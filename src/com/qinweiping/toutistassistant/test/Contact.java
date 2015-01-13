package com.qinweiping.toutistassistant.test;

public class Contact {

	int id;
	String image;
	String name;
	
	public Contact() 
	{
		super();
	}
	
	public Contact(int id,String image,String name)
	{
		this.id = id;
		this.image = image;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
