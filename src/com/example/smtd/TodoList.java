package com.example.smtd;

import java.io.*;
import java.util.ArrayList;

import com.example.smtd.datahandler.FileBasedDataStore;
import com.example.smtd.multiselect.TContextBar;

import com.example.smtd.multiselect.TMultiChoiceListener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;



public class TodoList extends ListActivity {

	private ArrayList<TItem> todoarray = new ArrayList<TItem>();
	private TMultiChoiceListener tMultiChoiceListener = new TMultiChoiceListener();
	
	/* When true, this overrides the onItemClick listener so that clicking
	 * on a checkbox changes selection state for the CAB, not actually checking 
	 * off TODO items.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.id.list);
		
		/* Initialize the isCABactive flag to false */
		
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
		
		/* Instantiate a TContextBar utility class that holds an ActionModeCallback */
		final TContextBar tContextBar = new TContextBar();
		
		/* Instantiate an adapter */
		TodoAdapter adapter = new TodoAdapter(this, todoarray);
		/* Get an instance of the ListView and attach the adapter */
		//Also need to add a longclick listener... probably shouldn't do this in 
		//the onCreate of activity for 301-ness.
		ListView listView = getListView();
		setListAdapter(adapter);
		
		/* Need to pass the adapter to our TMultiChoice Listener so that it can
		 * get id's
		 */
		
		tMultiChoiceListener.setTodoAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TItem item = (TItem) getListAdapter().getItem(position);					
				CheckBox check = (CheckBox) view.findViewById(R.id.checkbox_checkitem);
				boolean currentcheckstate = item.GetCheck();
				check.setChecked(!currentcheckstate);
				item.SetCheck(!currentcheckstate);
				Context c = view.getContext();
				Toast.makeText(view.getContext(), "Checked!" + item.getMessage(), Toast.LENGTH_SHORT).show();
				PackData();
			}
		});
		
		
		//Multiple modal may take over the LongClickListener
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		//However, this line lets people start the CAB many times.
		//listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		getListView().setMultiChoiceModeListener(tMultiChoiceListener);
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				view.setSelected(true);
				Log.w("onItemLongClick Listener", "Got here");
				TodoList.this.startActionMode(tMultiChoiceListener);
			
				return true;
			}
			
		});
		//getListView().getItemAtPosition(position).setBackgroundColor(color)
		//Debugging to see if the layout works
		//TodoList.this.startActionMode(tMultiChoiceListener);
		
		
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
		int id = item.getItemId();
		switch (id) {
			case R.id.action_add_item:
				openAddItemDialog();
				break;
			case R.id.action_summarize:
				openSummaryDialog();
				break;
		}
		
		return super.onOptionsItemSelected(item);
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
		ArrayList<TItem> temparray = fbds.UnPackData();
		if (temparray == null){
			Log.e("Unpack Wrapper", "Array returned was null. Using old data");
			return false;
		} else {
			todoarray = temparray;
			return true;
		}
		
	}
	
	private boolean PackData() {
		//Instantiate a FileBasedDataStore to handle this for us.
		FileBasedDataStore fbds = new FileBasedDataStore(TodoList.this);
		return fbds.PackData(todoarray);
		
	}

	public void addItem(AddItemDialogFragment aidf) {
		/* Callback from the AddItemDialog Fragment */
		// TODO Auto-generated method stub
		
		Dialog d = aidf.getDialog();
		EditText b = (EditText) d.findViewById(R.id.AddItem_editText);
		this.todoarray.add(new TItem(b.getText().toString(), false, false));
		PackData();
		
	}

}
