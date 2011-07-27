package de.fh.ka.as.car.gui;

public class SliderEvent extends ActionEvent{
	private double value;

	public SliderEvent(Object source, String name, double value) {
		super(source, name);
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

}
