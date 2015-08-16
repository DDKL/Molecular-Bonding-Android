package com.example.molecularbonding;

import java.util.ArrayList;
import java.util.List;

class Element{
	public List<Element> bonds;
	private int atomicNumber;
	
	public Element(int atomicNumber){
		this.atomicNumber = atomicNumber;
		bonds = new ArrayList<Element>(); 
	}
	
	public void addBond(Element that){
		this.bonds.add(that);
		that.bonds.add(this);
	}

	public void deleteBond(Element that){
		this.bonds.remove(that);
		that.bonds.remove(this);
	}
	
	public void deleteAllBonds(){
		while(!this.bonds.isEmpty()){
			this.deleteBond(this.bonds.get(0));
		}
	}

	
	public int getAtomicNumber(){
		return this.atomicNumber;
	}
	
}
