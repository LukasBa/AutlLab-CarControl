package de.fh.ka.as.car;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class CarController extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        CanvasView canvasView = new CanvasView(this);
        setContentView(canvasView);
    }
}