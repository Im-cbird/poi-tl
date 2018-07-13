package com.deepoove.poi.tl.renderObjTest;

public class DataModel implements RenderObj{

	private String name;
	
	public DataModel() {
		super();
	}

	public DataModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
