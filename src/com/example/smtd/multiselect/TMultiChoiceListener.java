package com.example.smtd.multiselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.example.smtd.R;
import com.example.smtd.TItem;
import com.example.smtd.TodoAdapter;
import com.example.smtd.datahandler.FileBasedDataStore;


import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;


public class TMultiChoiceListener implements MultiChoiceModeListener {

	
	//Private hashmap of which positions were selected
	private HashMap<Integer, Boolean> selectedpositions = new HashMap<Integer, Boolean>();
	
	
	private TodoAdapter adapter; //One of archiveadaper / unarchive adapter
	private TodoAdapter otheradapter; //One of unarchive adapter // archive adapter
	
	
	public void setTodoAdapter(TodoAdapter a, TodoAdapter o) {
		if (a == null) {
			throw new NullPointerException("Adapter passed to tMultiChoice listener was null");
		}
		if (o == null) {
			throw new NullPointerException("Other adapter passed to tMultiChoice listener was null");
		}
		
		this.adapter = a;
		this.otheradapter = o;
		
	}
	
	
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){	
			case R.id.action_delete:
				
				// Build an array list of TItems to delete
				ArrayList<TItem> itemstodelete = new ArrayList<TItem>();
				
				/* Delete the selected items */
				for (Map.Entry<Integer, Boolean> entry : selectedpositions.entrySet()){
					//Entry is in selectedpositions and value is true (to be deleted)
					//note that items can be in this map, but set to false if they
					//have been selected and deselected before the CAB is exited.
					if (entry.getValue()) {
						itemstodelete.add(adapter.getItem(entry.getKey()));
					}
				}
				//Need to do it this way since the position of all the items will
				//change as the for loop runs
				for (TItem todoitem : itemstodelete) {
					adapter.remove(todoitem);
				}
				adapter.notifyDataSetChanged();
				mode.finish();
				return true;
		case R.id.action_email:
				
				
				Vector<Integer> positions = new Vector<Integer>();
				for (Map.Entry<Integer, Boolean> entry : selectedpositions.entrySet()){
					if (entry.getValue()) {
						positions.add(entry.getKey());
					}
				}
				
				
				String emailmessage = adapter.getMultipleBodies(positions);
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_TEXT, emailmessage);
				
				try {
					adapter.getContext().startActivity(Intent.createChooser(i, "Send items"));
				} catch (android.content.ActivityNotFoundException e){
					Log.w("Action_email in TMC", "caught ActivityNotFoundException");
				}
				mode.finish();
				return true;
		case R.id.action_toggle_archive:
				for (Map.Entry<Integer, Boolean> entry : selectedpositions.entrySet()){
					if (entry.getValue()){ 
						//Item in selected positions map and actually selected (v = true)
						//Swap to other adapter.
						try{
						TItem archiveitem = adapter.getItem(entry.getKey());
						archiveitem.setSelected(false);
						boolean currentArchiveVal = archiveitem.GetArchive();
						archiveitem.SetArchive(!currentArchiveVal);
						
						adapter.remove(archiveitem);
						otheradapter.add(archiveitem);
						} catch (IndexOutOfBoundsException e) {
							Log.e("tmMultiChoiceListener", "Index out of bounds exception");
							Log.e("tmMultiChoiceListener", "");
						}
						otheradapter.notifyDataSetChanged();
						
						
					}
				}
				adapter.notifyDataSetChanged();
				mode.finish();
				return true;
		default:
				return false;
		}
		
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	       // Inflate the menu for the CAB
		//Log.w("Inflate Action Bar", "Here");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.cabmenu, menu);
        return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// Cleanup our selected flags.

		int position;
		for (position = 0; position < adapter.getCount(); position++){
			adapter.getItem(position).setSelected(false);
			adapter.notifyDataSetChanged();
		}	
		selectedpositions.clear();
		
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
			boolean checked) {
		
		//in here, update tmSelectedItems instance with data on what is selected
		selectedpositions.put(position, checked);
		adapter.getItem(position).setSelected(checked);
		adapter.notifyDataSetChanged();
		
		//Log.d("onItemCheckedStateChanged", "Position " + String.valueOf(position) + 
		//		" Id " + String.valueOf(id));
		

	}

}
