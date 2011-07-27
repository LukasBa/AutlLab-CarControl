package de.fh.ka.as.car.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Slider extends GUIComponent {
	protected double min, max;

	public enum Orientation {
		Horizontal, Vertical
	};

	protected Orientation direction = Orientation.Vertical;

	protected double value;

	public void setValue(double value) {
		this.value = value;
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		lastPY = max;
		switch (direction) {
		case Vertical:
			lastPY = ((value) / (max - min)) * getHeight() + y;
			System.out.println(lastPY);
		}

	}

	public void setValue(int px, int py) {
		if (contains(px, py)) {
			switch (direction) {
			case Vertical:
				setValueVertical(px, py);

				break;

			}
		}
	}

	private void setValueVertical(int px, int py) {
		double height = getHeight();
		double partLenght = height + y - py;
		value = (partLenght / height) * getRange() + min;
		notifyListener(new SliderEvent(this, "Slided", value));
		lastPY = py;
	}

	private double lastPY;

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		Rect rect = new Rect(x, (int) lastPY, x + width, y + height);
		canvas.drawRect(rect, paint);
	}

	public double getRange() {
		return max - min;
	}

	public Slider(int x, int y, int height, int width, double min, double max,
			double value) {
		super(x, y, height, width);
		this.min = min;
		this.max = max;
		this.value = value;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
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
