/*Copyright 2014 Stefan Martynkiw

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.example.smtd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;


/* CC BY 2.5 Attribution. Dialogs. Code modified for variable names.
 * http://developer.android.com/guide/topics/ui/dialogs.html */

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
