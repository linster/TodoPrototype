package com.example.smtd.multiselect;

import com.example.smtd.R;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;

public class TMultiChoiceListener implements MultiChoiceModeListener {

	@Override
	public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	       // Inflate the menu for the CAB
		Log.w("Inflate Action Bar", "Here");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.cabmenu, menu);
        return true;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
			boolean checked) {
		// TODO Auto-generated method stub
		
	}

}
