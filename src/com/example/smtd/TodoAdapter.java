package com.example.smtd;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.example.smtd.multiselect.TContextBar;

import android.content.Context;
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
       //check.create onclick listener. 
       // In here, I have access to check.toggle() as well as item.properties
       
       
 
       
       
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
	
}
