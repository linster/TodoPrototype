package com.example.smtd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;


public class SummaryViewDialogFragment extends DialogFragment {

	 @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
		 	//Get a LayoutInflater object
		 	LayoutInflater inflater = getActivity().getLayoutInflater();
		 
		 	
		 	View infoView = inflater.inflate(R.layout.summaryviewlayout, null);
		 	populateInformation(infoView);
		 	
		 	
		 	
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setTitle(R.string.Summary_title)
	        	   .setView(infoView)
	               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   //Do nothing
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }

	public void populateInformation(View infoView) {
		
		int uchecked = 0;
		int utotalunchecked = 0;
		int archived = 0;
		int ar_checked = 0;
		int ar_unchecked = 0;
		
		ListAdapter archiveAdapter = ((TodoList)getActivity()).archiveAdapter;
		ListAdapter unArchiveAdapter = ((TodoList)getActivity()).unarchiveAdapter;
		
		for (int position = 0; position < archiveAdapter.getCount(); position++){
			if ( ((TItem)unArchiveAdapter.getItem(position)).GetCheck()){
				uchecked++;
			} else {
				utotalunchecked++;
			}
		}
		
		archived = archiveAdapter.getCount();
		
		for (int position = 0; position < unArchiveAdapter.getCount(); position++){
			if ( ((TItem)archiveAdapter.getItem(position)).GetCheck()){
				ar_checked++;
			} else {
				ar_unchecked++;
			}
		}

		
		((TextView)infoView.findViewById(R.id.Summary_num_checked))
			.setText("Uunarchived TODO items checked: " + String.valueOf(uchecked));
	
		((TextView)infoView.findViewById(R.id.Summary_num_unchecked))
		.setText("Unarchived TODO items unchecked: " + String.valueOf(utotalunchecked));
		
		((TextView)infoView.findViewById(R.id.Summary_num_archived))
		.setText("Archived TODO items: " + String.valueOf(archived));
		
		((TextView)infoView.findViewById(R.id.Summary_num_ar_checked))
		.setText("Archived items checked: " + String.valueOf(ar_checked));
 	
		((TextView)infoView.findViewById(R.id.Summary_ar_unchecked))
		.setText("Archived items unchecked: " + String.valueOf(ar_unchecked));
 	
	}
}
