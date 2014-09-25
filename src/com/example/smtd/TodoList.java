package com.example.smtd;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import com.example.smtd.datahandler.FileBasedDataStore;
import com.example.smtd.multiselect.TContextBar;

import com.example.smtd.multiselect.TMultiChoiceListener;


import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.util.Log;



public class TodoList extends ListActivity {

	private ArrayList<TItem> unarchived = new ArrayList<TItem>();
	private ArrayList<TItem> archived = new ArrayList<TItem>();
	
	private boolean showingArchived;
	
	//String that holds which file we're using for data
	private String dataFileUsed = new String();
	final static public String unarchivedFile = "unarchived";
	final static public String archivedFile = "archived";
	
	
	//Adapters for both lists
	TodoAdapter archiveAdapter;
	TodoAdapter unarchiveAdapter;
	
	private TMultiChoiceListener tMultiChoiceListener;
	
	/* When true, this overrides the onItemClick listener so that clicking
	 * on a checkbox changes selection state for the CAB, not actually checking 
	 * off TODO items.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.id.list);
		
		showingArchived = false;
		
		this.archiveAdapter = new TodoAdapter(this, archived);
		this.unarchiveAdapter = new TodoAdapter(this, unarchived);
		
		this.tMultiChoiceListener = new TMultiChoiceListener();
		this.dataFileUsed = this.unarchivedFile;
		
		//Unserialize existing data from a store, if the store exists.
		if (UnPackData()) {
			//Unpacking sucessful
			Log.d("TodoList Activity", "Unpacking Successful");
		} else {
			//Unpacking unsucessful. If the file doesn't exist, then UnPackData() should
			//create it. Thus, UnPackData() should work next time. Which is why this case
			//doesn't freak out.
			Log.e("TodoList Activity", "Failed to unpack data");
		}
		
		setListAdapter(this.unarchiveAdapter);
		
		//Multiple modal may take over the LongClickListener
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		//However, this line lets people start the CAB many times.
		//listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Log.d("onItemClick listenter", "called");
				TItem item = (TItem) getListAdapter().getItem(position);					
				CheckBox check = (CheckBox) view.findViewById(R.id.checkbox_checkitem);
				boolean currentcheckstate = item.GetCheck();
				check.setChecked(!currentcheckstate);
				item.SetCheck(!currentcheckstate);
				Context c = view.getContext();
				//Toast.makeText(view.getContext(), "Checked!" + item.getMessage(), Toast.LENGTH_SHORT).show();
				PackData();
			}
		});
		getListView().setMultiChoiceModeListener(tMultiChoiceListener);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				view.setSelected(true);
				Log.w("onItemLongClick Listener Got here", String.valueOf(showingArchived));
				TodoList.this.startActionMode(tMultiChoiceListener);
			
				return true;
			}
			
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();

	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		//Clear the list and get data from filesystem
		//((TodoAdapter)getListAdapter()).clear();
		//UnPackData();
		
		/* Need to pass the adapter to our TMultiChoice Listener so that it can
		 * get id's
		 */
		if (showingArchived){
			this.tMultiChoiceListener.setTodoAdapter(archiveAdapter, unarchiveAdapter);
		} else {
			this.tMultiChoiceListener.setTodoAdapter(unarchiveAdapter, archiveAdapter);
		}
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list, menu);
	    return super.onCreateOptionsMenu(menu);

		//return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		PackData();
		int id = item.getItemId();
		switch (id) {
			case R.id.action_add_item:
				openAddItemDialog();
				break;
			case R.id.action_summarize:
				openSummaryDialog();
				break;
			case R.id.action_email_all:
				emailAllItems();
				break;
			case R.id.action_toggle_archive_view:
				toggleArchiveVIew();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	

	private void toggleArchiveVIew() {
		Log.d("toggleArchiveView() in activity", "Got here");
		
		//Toggle state
		boolean currentstate = this.showingArchived;
		this.showingArchived = !currentstate;
		Log.d("toggleArchive View()", "Current state"+ String.valueOf(showingArchived));
		//Show a toast so the user knows which mode he's in.
		String toastext = new String();
	
		// Change the action bar icon to brown. Brown is old, and archives are old. Good visual
		// reminder.
		if (this.showingArchived){
			toastext = "Now showing archived items";
			tMultiChoiceListener.setTodoAdapter(archiveAdapter, unarchiveAdapter);
			this.dataFileUsed = this.archivedFile;
			this.getActionBar().setIcon(R.drawable.ic_launcher_2);
			setListAdapter(archiveAdapter);
		} else {
			toastext = "Now showing unarchived items";
			tMultiChoiceListener.setTodoAdapter(unarchiveAdapter, archiveAdapter);
			this.dataFileUsed = this.unarchivedFile;
			this.getActionBar().setIcon(R.drawable.ic_launcher);
			setListAdapter(unarchiveAdapter);
		}
		UnPackData(); //Get latest data from file for the current list
		Toast.makeText(this, toastext, Toast.LENGTH_SHORT).show();
		((TodoAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	private void openSummaryDialog() {
		SummaryViewDialogFragment svdf = new SummaryViewDialogFragment();
		svdf.show(getFragmentManager(), "SummaryDF");
	}
	
	private void openAddItemDialog() {
		/* Instantiate the AddItemDialogFragment
		 * as per  http://developer.android.com/guide/topics/ui/dialogs.html
		 */
		AddItemDialogFragment aid = new AddItemDialogFragment();
		aid.show(getFragmentManager(), "AddItemF");
	}
	
	@Override
	public void onPause() {
		//Need to persist the data into Shared Preferences.
		super.onPause();
		if (!PackData()) {
			//Writing data failed.
			Log.w("TodoList Activity onPause", "PackData failed");
		}
	}
	
	
	private boolean UnPackData() {
		FileBasedDataStore fbds = new FileBasedDataStore(TodoList.this);
		
		ArrayList<TItem> arcarray = fbds.UnPackData(archivedFile);
		ArrayList<TItem> unarcparray = fbds.UnPackData(unarchivedFile);
		if (arcarray == null || unarcparray == null){
			Log.e("Unpack Wrapper", "Array returned was null. Using old data");
			return false;
		} else {
			
				archiveAdapter.clear();
				archiveAdapter.addAll(arcarray);
	
				unarchiveAdapter.clear();
				unarchiveAdapter.addAll(unarcparray);
			
			
			return true;
		}
		
	}
	
	private boolean PackData() {
		//Instantiate a FileBasedDataStore to handle this for us.
		FileBasedDataStore fbds = new FileBasedDataStore(TodoList.this);
		
		boolean a = false;
		boolean b = false;
		
		
		//Pack all the data
		//ArrayList may not match what data is in the adapter. Need to make a method that takes an adapter,
		//generates array list, and then packs it.
		Log.e("Got to PackData() in Activity", "");
			a = fbds.PackData(archived, archivedFile);
			b = fbds.PackData(unarchived, unarchivedFile);
			return a&&b;
		//TODO 
			//This doesn't actually Pack the data for some reason.
	}

	public void addItem(AddItemDialogFragment aidf) {
		/* Callback from the AddItemDialog Fragment */
		// TODO Auto-generated method stub
		
		Dialog d = aidf.getDialog();
		EditText b = (EditText) d.findViewById(R.id.AddItem_editText);
		if (this.showingArchived){
			Log.d("AddItem archived", "adding item");
			archiveAdapter.add(new TItem(b.getText().toString(), false, true));
		} else {
			unarchiveAdapter.add(new TItem(b.getText().toString(), false, false));

		}
		((TodoAdapter)getListAdapter()).notifyDataSetChanged();
		PackData();
		
	}
	
	
	private void emailAllItems() {
		Vector<Integer> allarchiveditems = new Vector<Integer>();
		
		//Lots of ugly code since BaseAdapter doesn't do hasNext()
		for (Integer i = 0; i < archiveAdapter.getCount(); i++){
			allarchiveditems.add(i);
		}
		
		Vector<Integer> allunarchiveditems = new Vector<Integer>();
		
		//Lots of ugly code since BaseAdapter doesn't do hasNext()
		for (Integer i = 0; i < unarchiveAdapter.getCount(); i++){
			allunarchiveditems.add(i);
		}
		
		String emailmessage = "Archived Items: \n ========== \n" +
				((TodoAdapter)archiveAdapter).getAllBodies(allarchiveditems) + 
				         "\n Unarchived Items: \n ========== \n" +
				((TodoAdapter)unarchiveAdapter).getAllBodies(allunarchiveditems);
		
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_TEXT, emailmessage);
		try {
			startActivity(Intent.createChooser(i, "Send TODO items"));
		} catch (android.content.ActivityNotFoundException e){
			Log.w("Action_email in TodoActivity", "caught ActivityNotFoundException");
		}
	}

}
