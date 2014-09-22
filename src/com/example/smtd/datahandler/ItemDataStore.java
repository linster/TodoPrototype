package com.example.smtd.datahandler;

import java.util.ArrayList;

import com.example.smtd.TItem;

public abstract class ItemDataStore {

	public abstract ArrayList<TItem> UnPackData();
	public abstract boolean PackData(ArrayList<TItem> source);
	
	
}
