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
	

		/* Serialization functions */
		/* http://www.onjava.com/pub/a/onjava/excerpt/JavaRMI_10/?page=3 */
		
		
}
