package de.fh.ka.as.car.gui;

public class ActionEvent {
	private Object source;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public Object getSource() {
		return source;
	}
	
	public ActionEvent(Object source, String name) {
		this.source = source;
		this.name = name;
	}


}
