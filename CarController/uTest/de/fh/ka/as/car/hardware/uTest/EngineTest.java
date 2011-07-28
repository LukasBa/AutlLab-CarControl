package de.fh.ka.as.car.hardware.uTest;

import ioio.lib.api.IOIO;
import ioio.lib.api.IOIOFactory;
import ioio.lib.api.exception.ConnectionLostException;
import junit.framework.TestCase;
import de.fh.ka.as.car.hardware.Engine;
import de.fh.ka.as.car.hardware.Engine.Direction;

public class EngineTest extends TestCase {

	protected IOIO ioio;
	protected Engine engine;

	protected void setUp() {
		ioio = IOIOFactory.create();
		try {
			engine = new Engine(ioio, 0, 1400, 1500, 1700);
		} catch (ConnectionLostException e) {
			fail("Engine konnte nicht erstellt werden.");
		}
	}

	public void testSetSpeed() {
		try {
			engine.setSpeed(0.5, Direction.Forward);
		} catch (ConnectionLostException e) {
			fail("Fehler bei Änderung der Geschwindigkeit.");
		}
	}

}
