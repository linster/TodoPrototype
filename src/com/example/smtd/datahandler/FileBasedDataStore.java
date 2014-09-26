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
package com.example.smtd.datahandler;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.example.smtd.TItem;
import com.example.smtd.TodoAdapter;

public class FileBasedDataStore extends ItemDataStore {

	Context ctx;
	
	
	public FileBasedDataStore(Context ctx) {
		//Need to pass in a context
		this.ctx = ctx;
	}
	
	
	
	@Override
	public ArrayList<TItem> UnPackData(String filename) {
		/* Unpack the serialized ArrayList into the class variable. */
		/* Return True if sucessful */
		// http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again

		// deserialize the object
		
		
		
		File ListDataDescriptor = new File(ctx.getFilesDir(), filename);
		InputStream in = null;
		
		try {
			in = new BufferedInputStream(new FileInputStream(ListDataDescriptor));
		} catch(FileNotFoundException f) {
			Log.e("TodoList.java UnPack", "ListData file not found");
			//Try to create the file.
			File listdata = new File(ctx.getFilesDir(), "ListData");
		} 
		
		
		 try {
		     ObjectInputStream si = new ObjectInputStream(in);
		     ArrayList<TItem> obj = (ArrayList<TItem>) si.readObject();
		     
		     si.close();
		     return obj;
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
		return null;
	}

	/* Implementing ISerializable. No code taken from site.*/
	/* http://www.onjava.com/pub/a/onjava/excerpt/JavaRMI_10/?page=3 */
	
	
	
	@Override
	public boolean PackData(ArrayList<TItem> source, String filename) {
		/** Pack the in-memory ArrayList<TItem>. This serializes
		 *  it to a shared preferences file.
		 */
		
		/* Referenced, but no code used from:
		 * http://beginnersbook.com/2013/12/how-to-serialize-arraylist-in-java/ */
		
		
		/* Need to serialize an object to a string 
		 */
		
		/*
		 * Creative Commons Attribution
		 * http://creativecommons.org/licenses/by-sa/2.5/
		 * 
		 * Changes made to variable names.
		 */
		
		// http://stackoverflow.com/questions/8887197/reliably-convert-any-object-to-string-and-then-back-again
		// Creative Commons Attribution Share Alike license for the code
		Log.d("FBDS pack data", source.toString());
		byte[] SerializedList;
		
		try { 
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(source);
			so.flush();
			SerializedList = bo.toByteArray();
			so.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		
		
		FileOutputStream fos;
		
		try {
			fos = ctx.openFileOutput(filename,  Context.MODE_PRIVATE);
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
