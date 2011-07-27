package de.fh.ka.as.car.gui;

import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

import android.R.bool;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GUIComponent {
	protected int x;
	protected int y;
	protected int height;
	protected int width;

	protected Paint paint = new Paint();

	protected List<ActionListener> listeners = new LinkedList<ActionListener>();
	
	protected boolean touchEnabled = true;

	public GUIComponent(int x, int y, int height, int width) {
		this();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	/**
	 * @param x
	 * @param y
	 * @param action {@link MotionEvent}
	 */
	public void touchEvent(int x, int y, int action) {
		if (contains(x, y) && touchEnabled ) {
			notifyListener(new ActionEvent(this, "Touched"));
		}
	}

	public GUIComponent() {
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(x, y, x + width, y + height, paint);
	};

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public boolean contains(int px, int py) {
		return px >= x && px <= (x + width) && py >= y && py <= (y + height);
	}
	
	public Rect getRect(){
		return new Rect(x, y, x+width, y+height);
	}
	
	protected void notifyListener(ActionEvent event){
		for (ActionListener listener : listeners) {
			listener.actionEventPerformed(event);
		}
	}
	
	public void setTouchEnabled(boolean touchEnabled) {
		this.touchEnabled = touchEnabled;
	}
	public boolean isTouchEnabled() {
		return touchEnabled;
	}
}
