package com.example.molecularbonding;


//This could use some abstraction, but at the time this was much easier
public class ElementData {
	public int AtomicNumber;
	public int type;
	public int TotalBonds;
	public String Name; 
		public ElementData(int AtomicNumber, int TotalBonds, int type, String Name){
			this.AtomicNumber = AtomicNumber;
			this.type = type;
			this.Name = Name;
			this.TotalBonds = TotalBonds;
		}
}
