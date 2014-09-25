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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TodoAdapter extends ArrayAdapter<TItem> {
/* Guide:
 * https://github.com/thecodepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
	
	
	public TodoAdapter(Context context, ArrayList<TItem> objects) {
		super(context, R.layout.tditemlayout, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TItem item = getItem(position);
		
		// Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.tditemlayout, parent, false);
       }
       // Lookup view for data population
       final CheckBox check = (CheckBox) convertView.findViewById(R.id.checkbox_checkitem);
   
       check.setText(R.id.checkbox_checkitem);
       check.setText(item.getMessage());
       check.setChecked(item.GetCheck());
       
       if (item.isSelected()){
    	   convertView.setBackgroundColor(getContext().getResources().
    			   getColor(android.R.color.holo_blue_dark));
       } else {
    	   convertView.setBackgroundColor(getContext().getResources().
    			   getColor(android.R.color.background_light));
       }

       
       // Return the completed view to render on screen
       return convertView;
		
	}
	
	public String getMultipleBodies(Vector<Integer> positions) {
		/** Get the bodies of the TItems, concatenate bodies and 
		 *  return as a String. Intended for an email body.
		 * 
		 */
		
		String returnstring = new String();
		
		for (Integer key: positions) {
			
			if (this.getItem(key).GetCheck()){
				returnstring += "[X]";
			} else {
				returnstring += "[ ]";
			}
			returnstring += ( " " + this.getItem(key).getMessage() + "\n\n");
		}
		
		return returnstring;	
	}
	
	public String getAllBodies(Vector<Integer> positions) {
		String returnstring = new String();
		
		for (Integer key: positions) {
			// Need to call super. to get all items
			if (super.getItem(key).GetCheck()){
				returnstring += "[X]";
			} else {
				returnstring += "[ ]";
			}
			returnstring += ( " " + this.getItem(key).getMessage() + "\n\n");
		}
		return returnstring;
	}


	
}
