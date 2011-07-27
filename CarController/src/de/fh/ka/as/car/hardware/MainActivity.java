package de.fh.ka.as.car.hardware;


import ioio.lib.R;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.AbstractIOIOActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.ToggleButton;

/**
 * This is the main activity of the HelloIOIO example application.
 * 
 * It displays a toggle button on the screen, which enables control of the
 * on-board LED. This example shows a very simple usage of the IOIO, by using
 * the {@link AbstractIOIOActivity} class. For a more advanced use case, see the
 * HelloIOIOPower example.
 */
public class MainActivity extends AbstractIOIOActivity {
	private ToggleButton button_;
	private SeekBar speedSlider;
	private SeekBar driveSlider;
	private EditText speedTextField;
	private EditText driveTextField;
	
	private int speedServoPort = 3;
	private int driveServoPort = 4;
	
	private static final int MAXIMUM_DRIVE_PULSE_WIDTH = 2000;
	
	private static final int MINIMUM_DRIVE_PULSE_WIDTH = 1000;

	/**
	 * Minimum pulse width in microsecends.
	 */
	private static final int MINIMUM_SPEED_PULSE_WIDTH = 1000;

	/**
	 * Maximum pulse width in microsecends.
	 */
	private static final int MAXIMUM_SPEED_PULSE_WIDTH = 2000;

	/**
	 * Called when the activity is first created. Here we normally initialize
	 * our GUI.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button_ = (ToggleButton) findViewById(R.id.button);
		speedSlider = (SeekBar) findViewById(R.id.seekBar1);
		driveSlider = (SeekBar) findViewById(R.id.seekBar2);
		speedTextField = (EditText) findViewById(R.id.SpeedTextField);
		driveTextField = (EditText) findViewById(R.id.DriveTextField);
		
		
		speedSlider.setMax((MAXIMUM_SPEED_PULSE_WIDTH - MINIMUM_SPEED_PULSE_WIDTH)-400);
		speedSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar paramSeekBar) {	}
			
			@Override
			public void onStartTrackingTouch(SeekBar paramSeekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar paramSeekBar, int paramInt,
					boolean paramBoolean) {
				int value = speedSlider.getProgress() + MINIMUM_SPEED_PULSE_WIDTH;
				//Log.i("IOIO", "slider: " + value);
				speedTextField.setText(""+value);
			}
		});
		

		driveSlider.setMax(MAXIMUM_DRIVE_PULSE_WIDTH - MINIMUM_DRIVE_PULSE_WIDTH);
		driveSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar paramSeekBar) {	}
			
			@Override
			public void onStartTrackingTouch(SeekBar paramSeekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar paramSeekBar, int paramInt,
					boolean paramBoolean) {
				int value = driveSlider.getProgress() + MINIMUM_DRIVE_PULSE_WIDTH;
				//Log.i("IOIO", "slider: " + value);
				driveTextField.setText(""+value);
			}
		});


	}

	/**
	 * This is the thread on which all the IOIO activity happens. It will be run
	 * every time the application is resumed and aborted when it is paused. The
	 * method setup() will be called right after a connection with the IOIO has
	 * been established (which might happen several times!). Then, loop() will
	 * be called repetitively until the IOIO gets disconnected.
	 */
	class IOIOThread extends AbstractIOIOActivity.IOIOThread {
		/** The on-board LED. */
		private DigitalOutput led_;
		private PwmOutput speedPwm;
		private PwmOutput drivePwm;

		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			led_ = ioio_.openDigitalOutput(0, true);
			speedPwm = ioio_.openPwmOutput(speedServoPort, 50);
			drivePwm = ioio_.openPwmOutput(driveServoPort, 50);
		}

		/**
		 * Called repetitively while the IOIO is connected.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
		 */
		@Override
		protected void loop() throws ConnectionLostException {
			led_.write(!button_.isChecked());
			
			int value = speedSlider.getProgress() + MINIMUM_SPEED_PULSE_WIDTH;
			Log.i("IOIO", "SpeedSlider: " + value);
			speedPwm.setPulseWidth(value);
			
			value = driveSlider.getProgress() + MINIMUM_DRIVE_PULSE_WIDTH;
			Log.i("IOIO", "DriveSlider: " + value);
			drivePwm.setPulseWidth(value);
			
			try {
				sleep(10);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * A method to create our IOIO thread.
	 * 
	 * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
	 */
	@Override
	protected AbstractIOIOActivity.IOIOThread createIOIOThread() {
		return new IOIOThread();
	}
}