package com.example.smtd.multiselect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smtd.R;
import com.example.smtd.TItem;
import com.example.smtd.TodoAdapter;


import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;


public class TMultiChoiceListener implements MultiChoiceModeListener {

	
	//Private hashmap of which positions were selected
	private HashMap<Integer, Boolean> selectedpositions = new HashMap<Integer, Boolean>();
	
	
	private TodoAdapter adapter;
	
	
	
	public void setTodoAdapter(TodoAdapter a) {
		this.adapter = a;
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
				
				for (TItem todoitem : itemstodelete) {
					adapter.remove(todoitem);
				}
				adapter.notifyDataSetChanged();
				mode.finish();
			case R.id.action_email:
				//emailSelectedItems()
				mode.finish();
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
