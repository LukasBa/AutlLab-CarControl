package de.fh.ka.as.car.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Slider extends GUIComponent {
	//protected double min, max;

	/**
	 * Converts pixel to min, max
	 */
	protected RangeConverter range;
	protected double value;

	public enum Orientation {
		Horizontal, Vertical
	};

	protected Orientation direction = Orientation.Vertical;

	public Slider(int x, int y, int height, int width, double min, double max,
			double value) {
		super(x, y, height, width);
		switch (direction) {
		case Vertical:
			range = new RangeConverter(y, y + height, min, max);
			break;
		case Horizontal:
			range = new RangeConverter(x, x + width, min, max);
		default:
			break;
		}

		this.value = value;
	}

	public void setValue(double value) {
		this.value = value;
		if (value < range.getInputMin()) {
			value = range.getInputMin();
		} else if (value > range.getInputMax()) {
			value = range.getInputMax();
		}
	}

	public void setValue(int px, int py) {
		if (contains(px, py)) {
			switch (direction) {
			case Vertical:
				setValueVertical(py);
				break;
			}
		}
	}

	private void setValueVertical(int py) {
		value = range.getOutputMax()-range.toOutput(py);
		notifyListener(new SliderEvent(this, "Slided", value));
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		Rect rect = new Rect(x, (int) range.toInput(value), x + width, y + height);
		canvas.drawRect(rect, paint);
	}

	public double getRange() {
		return range.getOutputRange();
	}

	public double getMin() {
		return range.getOutputMin();
	}

	public void setMin(double min) {
		range.setOutputMin(min);
	}

	public double getMax() {
		return range.getOutputMax();
	}

	public void setMax(double max) {
		range.setOutputMax(max);
	}

	public double getValue() {
		return value;
	}

	@Override
	public void touchEvent(int x, int y, int action) {
		if (touchEnabled) {
			setValue(x, y);
		}
	}

}
