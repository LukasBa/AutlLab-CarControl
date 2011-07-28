package de.fh.ka.as.car.hardware;

import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import android.util.Log;
import de.fh.ka.as.car.gui.RangeConverter;

public class Engine {

	public enum Direction {
		Backward, Forward
	};

	private IOIO ioio;
	private int servoPort;
	private PwmOutput pwm;

	// TODO: Werte über PropertyDatei festlegen
	private int maxForwardEngineValue = 2000;
	private int maxBackwardEngineValue = 1000;
	private int stopEngineValue = 1500;

	private RangeConverter backwardRange;
	private RangeConverter forwardRange;

	public Engine(IOIO ioio, int servoPort) throws ConnectionLostException {
		this.ioio = ioio;
		this.servoPort = servoPort;

		backwardRange = new RangeConverter(0, 1, maxBackwardEngineValue, stopEngineValue);
		forwardRange = new RangeConverter(0, 1, stopEngineValue, maxForwardEngineValue);

		setup();
	}

	public Engine(IOIO ioio, int servoPort, int maxBackwardEngineValue, int stopEngineValue,
			int maxForwardEngineValue) throws ConnectionLostException {
		this(ioio, servoPort);
		setMaxBackwardEngineValue(maxBackwardEngineValue);
		setMaxForwardEngineValue(maxForwardEngineValue);
		setStopEngineValue(stopEngineValue);
	}

	protected void setup() throws ConnectionLostException {
		pwm = ioio.openPwmOutput(servoPort, 50);
	}

	public void setSpeed(double speed, Direction direction) throws ConnectionLostException {
		int newPulseWidth = stopEngineValue;

		switch (direction) {
			case Forward:
				if (speed < 0) {
					newPulseWidth = stopEngineValue;
				} else if (speed > 1) {
					newPulseWidth = maxForwardEngineValue;
				}
				newPulseWidth = (int) forwardRange.toOutput(speed);
				break;

			case Backward:
				if (speed < 0) {
					newPulseWidth = stopEngineValue;
				} else if (speed > 1) {
					newPulseWidth = maxBackwardEngineValue;
				}
				newPulseWidth = (int) backwardRange.toOutput(speed);
		}

		Log.i("Car", "Motorsteuerung: " + newPulseWidth);
		pwm.setPulseWidth(newPulseWidth);
	}

	public void setMaxForwardEngineValue(int maxForwardEngineValue) {
		this.maxForwardEngineValue = maxForwardEngineValue;
		forwardRange.setOutputMax(maxForwardEngineValue);
	}

	public void setStopEngineValue(int stopEngineValue) {
		this.stopEngineValue = stopEngineValue;
		forwardRange.setOutputMin(stopEngineValue);
		backwardRange.setOutputMax(stopEngineValue);
	}

	public void setMaxBackwardEngineValue(int maxBackwardEngineValue) {
		this.maxBackwardEngineValue = maxBackwardEngineValue;
		backwardRange.setOutputMin(maxBackwardEngineValue);
	}

	public int getMaxForwardEngineValue() {
		return maxForwardEngineValue;
	}

	public int getMaxBackwardEngineValue() {
		return maxBackwardEngineValue;
	}

	public int getStopEngineValue() {
		return stopEngineValue;
	}
}
