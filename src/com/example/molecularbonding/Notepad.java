package com.example.molecularbonding;

import java.io.Serializable;



@SuppressWarnings("serial")
public class Notepad implements Serializable {

	private String nm,content;
	 public Notepad() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Notepad(String txtname, String txtcontent) {
		// TODO Auto-generated constructor stub
		nm=txtname;
		content=txtcontent;
	}
    
//getter setter method
	public String toString() {
		return nm;
	}
	public String getContent() {
		return content;
	}
public String getName() {
		
		return ""+nm;
	}
	


}
