package com.example.smtd.multiselect;

import com.example.smtd.R;
import com.example.smtd.R.id;
import com.example.smtd.R.menu;

import android.view.*;

public class TContextBar {
	/* Holder for methods needed for the Contextual Action Bar */
	
	/* http://developer.android.com/guide/topics/ui/menus.html#context-menu */
	
	private ActionMode mActionMode;
	
	public ActionMode getmActionMode() {
		return mActionMode;
	}

	public void setmActionMode(ActionMode mActionMode) {
		this.mActionMode = mActionMode;
	}

	public static ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.cabmenu, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false; // Return false if nothing is done
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.action_email:
	                //shareCurrentItem();
	                mode.finish(); // Action picked, so close the CAB
	                return true;
	            case R.id.action_delete:
	            	//deleteCurrrentItems();
	            	mode.finish();
	            	return true;
	            default:
	                return false;
	        }
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	        //setmActionMode(null);
	    }
	};

	public ActionMode.Callback getmActionModeCallback() {
		return mActionModeCallback;
	}

	
	
	
}
