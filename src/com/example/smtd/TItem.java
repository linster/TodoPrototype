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
import java.util.Date;
import java.io.Serializable;
public class TItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	
	
	
	/* Stores whether the current item is checked */
	private boolean isChecked;
	
	public void SetCheck(boolean toggle) {
		this.isChecked = toggle;
	}
	
	public boolean GetCheck() {
		return this.isChecked;
	}
	
	/* Archived status? */
		private boolean isArchived;
		public void SetArchive(boolean toggle) {
			this.isArchived = toggle;
		}
		public boolean GetArchive() {
			return this.isArchived;
		}
	
	
	/* Date stamp for creation */
		//transient to make serialization work?
		Date created;
	/* Message of the TODO */
		private String message = new String();
		public String getMessage() {
			return message;
		}
	
		public void setMessage(String message) {
			this.message = message;
		}

	/* Define a ToString method for the ListItem?? */
	
	/* Constructors */
		TItem() {
			/** Create an empty todo list item with no text, unchecked, and unarchived */
			this("", false, false);
		}
		
		TItem(String message, boolean isChecked, boolean isArchived) {
			/** Full constructor for a todolist item. */
			/* Initialize the date to the initialization date of this object, if not already set. */
			this.created = new Date();	
			
			this.SetCheck(isChecked);
			this.SetArchive(isArchived);
			this.setMessage(message);
			
		}

		/* Is the item selected for multiview*/
		private boolean isSelected;
		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
	

		/* Serialization functions */
		/* http://www.onjava.com/pub/a/onjava/excerpt/JavaRMI_10/?page=3 */
		
		
}
