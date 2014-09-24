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
		
		int checked = 0;
		int totalunchecked = 0;
		int archived = 0;
		int ar_checked = 0;
		int ar_unchecked = 0;
		
		ListAdapter tdla = ((TodoList)getActivity()).getListAdapter(); 
		
		int position = 0;
		for (position = 0; position < tdla.getCount(); position++){
			
			if ( ((TItem)tdla.getItem(position)).GetCheck() ) {
				checked++;
			} else {
				totalunchecked++;
			}
			
			if ( ((TItem)tdla.getItem(position)).GetArchive()  ){
				archived++;
			}
			
			if (      ((TItem)tdla.getItem(position)).GetArchive()
				   && ((TItem)tdla.getItem(position)).GetCheck()
				){
				ar_checked++;
			}

			if (       ((TItem)tdla.getItem(position)).GetArchive()
				   && !((TItem)tdla.getItem(position)).GetCheck()
				){
				ar_unchecked++;
			}
		}
		
		((TextView)infoView.findViewById(R.id.Summary_num_checked))
			.setText("Total Number of TODO items checked: " + String.valueOf(checked));
	
		((TextView)infoView.findViewById(R.id.Summary_num_unchecked))
		.setText("Total Number of TODO items unchecked: " + String.valueOf(totalunchecked));
		
		((TextView)infoView.findViewById(R.id.Summary_num_archived))
		.setText("Total Number of archived TODO items: " + String.valueOf(archived));
		
		((TextView)infoView.findViewById(R.id.Summary_num_ar_checked))
		.setText("Total Number of archived items checked: " + String.valueOf(ar_checked));
 	
		((TextView)infoView.findViewById(R.id.Summary_ar_unchecked))
		.setText("Total Number of archived items unchecked: " + String.valueOf(ar_unchecked));
 	
	}
}
