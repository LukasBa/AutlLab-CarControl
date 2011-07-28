package de.fh.ka.as.car.gui;

public class RangeConverter {
	private double inputMin;
	private double inputMax;

	private double outputMin;
	private double outputMax;

	public RangeConverter(double inputMin, double inputMax, double outputMin, double outputMax) {
		this.inputMin = inputMin;
		this.inputMax = inputMax;
		this.outputMin = outputMin;
		this.outputMax = outputMax;
	}

	public double toOutput(double input) {
		return (((input - getRealInputMin()) / getInputRange()) * getOutputRange()) + getRealOutputMin();
	}

	public double toInput(double output) {
		return (((output - getRealOutputMin()) / getOutputRange()) * getInputRange()) + getRealInputMin();
	}

	public double getInputRange() {
		if (inputMin < inputMax) {
			return inputMax - inputMin;
		} else {
			return inputMin - inputMax;
		}
	}

	public double getOutputRange() {
		if (outputMin < outputMax) {
			return outputMax - outputMin;
		} else {
			return outputMin - outputMax;
		}
	}

	private double getRealInputMin() {
		return Math.min(inputMin, inputMax);
	}

	private double getRealOutputMin() {
		return Math.min(outputMin, outputMax);
	}

	public double getInputMin() {
		return inputMin;
	}

	public void setInputMin(double inputMin) {
		this.inputMin = inputMin;
	}

	public double getInputMax() {
		return inputMax;
	}

	public void setInputMax(double inputMax) {
		this.inputMax = inputMax;
	}

	public double getOutputMin() {
		return outputMin;
	}

	public void setOutputMin(double outputMin) {
		this.outputMin = outputMin;
	}

	public double getOutputMax() {
		return outputMax;
	}

	public void setOutputMax(double outputMax) {
		this.outputMax = outputMax;
	}

}
