package com.company.si;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * StaffIndex
 * 
 * MongoDB does not have a clean way to auto-increment IDs. So this collection's
 * sole purpose is to keep track of what the last assigned employee ID was.
 * 
 * @author Brian Hession
 *
 */
@Document(collection = "staffindex")
public class StaffIndex {

	public static final int EMPLOYEE_INDEX_ID = 1;

	@Id
	private int id;
	private int currentIndex;

	public StaffIndex(int id, int currentIndex) {
		this.id = id;
		this.currentIndex = currentIndex;
	}

	public int getId() {
		return id;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
	 * Increments the index and returns the available ID.
	 * 
	 * @return the next available ID
	 */
	public int getNextIndex() {
		return ++currentIndex;
	}
}
