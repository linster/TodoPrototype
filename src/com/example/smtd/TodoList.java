package com.example.smtd;

import java.io.*;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.util.Log;



public class TodoList extends Activity {

	private ArrayList<TItem> todoarray = new ArrayList<TItem>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);
		
		/* Create a crappy array list of todoitems */
		
		//Unserialize existing data from a store, if the store exists.
		
		if (UnPackData()) {
			//Unpacking sucessful
		} else {
			//Unpacking unsucessful. Probably record to log?
			Log.e("TodoList Activity", "Failed to unpack data");
			
			//Put in some test data.
			todoarray.add(new TItem("Test", false, false));
			todoarray.add(new TItem("Boris", false, false));
			todoarray.add(new TItem("Doris", false, false));
			todoarray.add(new TItem("Jeff", false, false));
			todoarray.add(new TItem("Steve", false, false));	
		}
		
		
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
	
	@Override
	public void onPause() {
		//Need to persist the data into Shared Preferences.
		super.onPause();
		if (!PackData()) {
			//Writing data failed.
			Log.w("TodoList Activity onPause", "PackData failed");
		}
	}
	
	
	private boolean UnPackData() {
		/* Unpack the serialized ArrayList into the class variable. */
		/* Return True if sucessful */
		// http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again

		// deserialize the object
		
		
		
		File ListDataDescriptor = new File(getFilesDir(), "ListData");
		InputStream in = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(ListDataDescriptor));
		} catch(FileNotFoundException f) {
			Log.e("TodoList.java UnPack", "ListData file not found");
			//Try to create the file.
			File listdata = new File(getFilesDir(), "ListData");
		} 
		
		
		 try {
		     ObjectInputStream si = new ObjectInputStream(in);
		     ArrayList<TItem> obj = (ArrayList<TItem>) si.readObject();
		     this.todoarray = obj;
		     si.close();
		     return true;
		 } catch (Exception e) {
		     System.out.println(e + "\n\n" + e.getMessage());
		     Log.e("Unpack", "Exception");

		 }finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return false;
	}
	
	private boolean PackData() {
		/** Pack the in-memory ArrayList<TItem>. This serializes
		 *  it to a shared preferences file.
		 */
		
		/* http://beginnersbook.com/2013/12/how-to-serialize-arraylist-in-java/ */
		
		
		/* Need to serialize an object to a string 
		 */
		// http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again
		// Creative Commons Attribution Share Alike license for the code
		
		byte[] SerializedList;
		
		try { 
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(this.todoarray);
			so.flush();
			SerializedList = bo.toByteArray();
			so.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		
		String filename = "ListData";
		FileOutputStream fos;
		
		try {
			fos = openFileOutput(filename,  Context.MODE_PRIVATE);
			fos.write(SerializedList);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TodoList Activity PackData", "Exception in filewriting");
			return false;
		}
		
	}

}
