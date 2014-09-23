package com.example.smtd.multiselect;

import java.util.List;

import com.example.smtd.R;
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

	private TodoAdapter adapter;
	
	
	
	public void setTodoAdapter(TodoAdapter a) {
		this.adapter = a;
	}
	
	
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){	
			case R.id.action_delete:
				//deleteSelectedItems()
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
		// Cleanup our selected flag.

		int position;
		
		for (position = 0; position < adapter.getCount(); position++){
			adapter.getItem(position).setSelected(false);
			adapter.notifyDataSetChanged();
		}	
		
		
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
		adapter.getItem(position).setSelected(checked);
		adapter.notifyDataSetChanged();
		
		//Log.d("onItemCheckedStateChanged", "Position " + String.valueOf(position) + 
		//		" Id " + String.valueOf(id));
		

	}

}
