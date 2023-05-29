package com.xwarner.mdash.actions;

public class Attribute {

	public String displayName;
	public Object value;
	public boolean editable;

	public Attribute(String displayName) {
		this.displayName = displayName;
		this.editable = true;
	}

	public Attribute(String displayName, Object value) {
		this.displayName = displayName;
		this.value = value;
		this.editable = true;
	}

}
