package com.example.smtd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
       
       //check.create onclick listener. 
       // In here, I have access to check.toggle() as well as item.properties
       
       check.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//check.toggle();
				boolean currentcheckstate = item.GetCheck();
				check.setChecked(!currentcheckstate);
				item.SetCheck(!currentcheckstate);
				Context c = v.getContext();
				Toast.makeText(c, "Checked!" + item.getMessage(), Toast.LENGTH_SHORT).show();
			}
       });
       
       
       // Return the completed view to render on screen
       return convertView;
		
	}
	
}
