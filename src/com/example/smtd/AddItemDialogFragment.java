package com.example.smtd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;


/* http://developer.android.com/guide/topics/ui/dialogs.html */

public class AddItemDialogFragment extends DialogFragment {
	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
		 
		 
		 
		 
		 
		 	//Get a LayoutInflater object
		 	LayoutInflater inflater = getActivity().getLayoutInflater();
		 
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setTitle(R.string.AddItem_title)
	        	   .setView(inflater.inflate(R.layout.additemlayout, null))
	               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   ((TodoList) getActivity()).addItem(
	                    		   AddItemDialogFragment.this
	                    		   );
	                    
	                   }
	               })
	               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // User cancelled the dialog
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }


}
