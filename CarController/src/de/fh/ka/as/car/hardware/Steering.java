package de.fh.ka.as.car.hardware;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import android.util.Log;
import de.fh.ka.as.car.gui.RangeConverter;

public class Steering {
	private IOIO ioio;
	private int servoPort;
	private PwmOutput pwm;

	// TODO: Werte über PropertyDatei festlegen, stimmen Werte
	private int leftSteeringValue = 1000;
	private int rightSteeringValue = 2000;
	private int straightSteeringValue = 1500;

	// private RangeConverter rangeConverter
	private RangeConverter leftRange;
	private RangeConverter rightRange;

	public Steering(IOIO ioio, int servoPort) throws ConnectionLostException {
		this.ioio = ioio;
		this.servoPort = servoPort;

		// rangeConverter =
		// new RangeConverter(0, 1, leftSteeringValue, rightSteeringValue);

		leftRange = new RangeConverter(-1, 0, leftSteeringValue, straightSteeringValue);
		rightRange = new RangeConverter(0, 1, straightSteeringValue, rightSteeringValue);

		setup();
	}

	protected void setup() throws ConnectionLostException {
		pwm = ioio.openPwmOutput(servoPort, 50);
	}

	public void setSteering(double steering) throws ConnectionLostException {
		int newPulseWidth;
		if (steering < -1) {
			newPulseWidth = leftSteeringValue;
		} else if (steering < 0) {
			newPulseWidth = (int) leftRange.toOutput(steering);
		} else if (steering < 1) {
			newPulseWidth = (int) rightRange.toOutput(steering);
		} else { // if(steering > 1)
			newPulseWidth = rightSteeringValue;
		}
		Log.i("Car", "Lenkungsteuerung: " + newPulseWidth);
		pwm.setPulseWidth(newPulseWidth);
	}

	public void setLeftSteeringValue(int leftSteeringValue) {
		this.leftSteeringValue = leftSteeringValue;
		leftRange.setOutputMin(leftSteeringValue);
		// rangeConverter.setOutputMin(leftSteeringValue);
	}

	public void setStraightSteeringValue(int straightSteeringValue) {
		this.straightSteeringValue = straightSteeringValue;
		leftRange.setOutputMax(straightSteeringValue);
		rightRange.setOutputMin(straightSteeringValue);
	}

	public void setRightSteeringValue(int rightSteeringValue) {
		this.rightSteeringValue = rightSteeringValue;
		rightRange.setOutputMax(rightSteeringValue);
		// rangeConverter.setOutputMax(rightSteeringValue);
	}

	public int getRightSteeringValue() {
		return rightSteeringValue;
	}

	public int getLeftSteeringValue() {
		return leftSteeringValue;
	}

	public int getStraightSteeringValue() {
		return straightSteeringValue;
	}
}
