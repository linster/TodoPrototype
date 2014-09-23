package com.example.smtd.multiselect;

import java.util.*;

import com.example.smtd.TodoAdapter;
import com.example.smtd.TodoList;

import android.content.Context;
import android.widget.ArrayAdapter;

public class TMSelectedItems {
	
	
	//need to talk to the adapter for the ListView in the Activity to call 
	//its notifyDataSetChanged() method
	private TodoAdapter adapter;
	
	
	//This class needs a reference to the TodoAdapter in the TodoList activity
	//so that it can call adapter.notifyDataSetChanged() on it.
	TMSelectedItems(TodoAdapter a){
		this.adapter = a;	
	}
	
	//Position, Selected?
	private HashMap<Integer, Boolean> sa = new HashMap<Integer, Boolean>();
	
	public void setSelection(int position, boolean val){
		sa.put(position, val);
		adapter.notifyDataSetChanged();
	}
	
	public void deselect(int position) {
		sa.remove(position);
		adapter.notifyDataSetChanged();
	}
	
	public void emptyMap() {
		/* Empty the hashmap */
		sa.clear();
	}
	
	public boolean isSelected(int position){
		Boolean result = sa.get(position);
		if (result == null)
			return false;
		else
				return result;
	}

	public Set<Integer> getCheckedPositions() {
		/** Return which positions are actually set. (Set of positions w data)*/
		return sa.keySet();
	}
	

}
