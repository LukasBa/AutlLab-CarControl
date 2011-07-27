package de.fh.ka.as.car.gui;

import de.fh.ka.as.car.gui.ButtonEvent.Type;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Button extends GUIComponent {

	private Bitmap bitmap;
	private boolean isPressed = false;

	public Button(int x, int y, int height, int width, Bitmap bitmap) {
		super(x, y, height, width);
		this.bitmap = bitmap;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, null, getRect(), paint);
	}

	/**
	 * @param x
	 * @param y
	 * @param action
	 *            {@link MotionEvent#ACTION_DOWN} | {@link MotionEvent#ACTION_UP}
	 */
	public void touchEvent(int x, int y, int action) {
		if (contains(x, y)) {
			if (action == MotionEvent.ACTION_UP) {
				isPressed = false;
				notifyListener(new ButtonEvent(this, Type.Up));
			} else if (action == MotionEvent.ACTION_DOWN) {
				isPressed = true;
				notifyListener(new ButtonEvent(this, Type.Down));
			}
		}
	}
	
	
	public boolean isPressed() {
		return isPressed;
	}
	
	

}
