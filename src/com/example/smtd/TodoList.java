package com.example.smtd;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

public class TodoList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		
		/* Create a crappy array list of todoitems */
		ArrayList<TItem> todoarray = new ArrayList<TItem>();
		/* This is the wrong place to populate... */
		todoarray.add(new TItem("Test", false, false));
		todoarray.add(new TItem("Boris", false, false));
		todoarray.add(new TItem("Doris", false, false));
		/* Instantiate an adapter */
		TodoAdapter adapter = new TodoAdapter(this, todoarray);
		/* Get an instance of the ListView and attach the adapter */
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
