package de.fh.ka.as.car.gui.uTest;

import junit.framework.TestCase;
import de.fh.ka.as.car.gui.RangeConverter;

public class RangeConverterTest extends TestCase {
	private RangeConverter converter;

	public RangeConverterTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		converter = new RangeConverter(100, 200, 400, 800);
	}

	public void testToOutput() {
		assertEquals(400, converter.toOutput(100));
		assertEquals(600, converter.toOutput(150));
		assertEquals(800, converter.toOutput(200));
	}

	public void testToInput() {
		assertEquals(100, converter.toInput(400));
		assertEquals(150, converter.toInput(600));
		assertEquals(200, converter.toInput(800));
	}

	public void testGetInputRange() {
		assertEquals(100, converter.getInputRange());
	}

	public void testGetOutputRange() {
		assertEquals(400, converter.getOutputRange());
	}

	public void testMinMaxSwap() {
		RangeConverter converterUnderTest = new RangeConverter(200, 100, 400, 800);

		assertEquals(400, converterUnderTest.toOutput(100));
		assertEquals(600, converterUnderTest.toOutput(150));
		assertEquals(800, converterUnderTest.toOutput(200));

		assertEquals(100, converterUnderTest.toInput(400));
		assertEquals(150, converterUnderTest.toInput(600));
		assertEquals(200, converterUnderTest.toInput(800));
	}

}
