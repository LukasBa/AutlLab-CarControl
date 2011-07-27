package de.fh.ka.as.car;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import de.fh.ka.as.car.gui.ActionEvent;
import de.fh.ka.as.car.gui.ActionListener;
import de.fh.ka.as.car.gui.Button;
import de.fh.ka.as.car.gui.ButtonEvent;
import de.fh.ka.as.car.gui.GUIComponent;
import de.fh.ka.as.car.gui.Slider;
import de.fh.ka.as.car.gui.SliderEvent;

public class CanvasView extends View implements OnTouchListener {

	private List<GUIComponent> guiComponents = new LinkedList<GUIComponent>();

	Button button;
	Slider slider;
	public CanvasView(Context context) {
		super(context);

		 slider = new Slider(10, 10, 320, 80, 1500, 2000, 1500);
		// GUIComponent button = new GUIComponent(50, 120, 100, 40);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.pedal);
		 button = new Button(350, 250, 120, 90, bitmap);
		guiComponents.add(button);
		guiComponents.add(slider);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionEventPerformed(ActionEvent event) {
				ButtonEvent buttonEvent = (ButtonEvent) event;
				slider.setValue(1);
				slider.setTouchEnabled(!button.isPressed());
				
				Log.i("Button", "is pressed: "+button.isPressed());
			}
		});

		slider.addActionListener(new ActionListener() {

			@Override
			public void actionEventPerformed(ActionEvent event) {
				SliderEvent sliderEvent = (SliderEvent) event;

				Log.i("IOIO", "Slider Value: " + sliderEvent.getValue());

			}
		});

		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (GUIComponent component : guiComponents) {
			component.draw(canvas);
		}
	}

	int pointerCount;
	int MAX_POINTERS = 20;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		pointerCount = event.getPointerCount();

		int actionId = event.getPointerId(pointerIndex);

		// if (actionId < MAX_POINTERS) {
		// //lastActions[actionId] = action;
		// }

		for (int i = 0; i < pointerCount; i++) {
			int pointerId = event.getPointerId(i);
			if (pointerId < MAX_POINTERS) {
				// points[pointerId] =
				PointF point = new PointF(event.getX(i), event.getY(i));
				for (GUIComponent component : guiComponents) {
					component.touchEvent((int) point.x, (int) point.y, action);
				}
				if (action == MotionEvent.ACTION_MOVE) {
					// lastActions[pointerId] = action;
				}
			}
		}

		invalidate();
		return true;
	}

}
