package com.iti.conqueror.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.iti.conqueror.R;

public class AnalogStick extends View {

	private double touchX, touchY;
	private int stickOriginX, stickOriginY;
	private int px, py;
	private double xCor, yCor;

	private static final int maxStickDistance = 100;
	private Bitmap base;
	private Bitmap stick;

	// private Context context;
	private TextView x;
	private TextView y;

	public AnalogStick(Context context, TextView x, TextView y) {
		super(context);
		 this.x = x;
		 this.y = y;
		init();
	}

	public AnalogStick(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AnalogStick(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// initialize the view
	private void init() {
		base = BitmapFactory.decodeResource(getResources(),
				R.drawable.control_base);
		stick = BitmapFactory.decodeResource(getResources(),
				R.drawable.control_knob);
		setFocusable(true);

		int bgWidth = base.getWidth();
		int bgHeight = base.getHeight();

		int stickWidth = stick.getWidth();
		int stickHeight = stick.getHeight();

		DisplayMetrics metrics = getContext().getResources()
				.getDisplayMetrics();
		int width = metrics.widthPixels - metrics.widthPixels / 3;
		int height = metrics.heightPixels;

		// math calculations to get the stick in the middle of the base image

		stickOriginX = width / 2 + width / 3;
		stickOriginY = height / 2 + height / 6;
		px = stickOriginX + stickWidth / 2 - bgWidth / 2;
		py = stickOriginY + stickHeight / 2 - bgHeight / 2;

		// make sure when app first starts the stick is in the right position
		touchX = stickOriginX;
		touchY = stickOriginY;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw the bitmaps

		 x.setText("X : " + (int) yCor);
		 y.setText("Y : " + (int) xCor);
		canvas.drawBitmap(base, px, py, null);
		canvas.drawBitmap(stick, (int) touchX, (int) touchY, null);

		canvas.save();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int actionType = event.getAction();

		if (actionType == MotionEvent.ACTION_MOVE) {
			double tempTouchX = touchX;
			double tempTouchY = touchY;

			touchX = stickOriginX - event.getX();
			touchY = stickOriginY - event.getY();

			if (!(Math.abs(touchX) > maxStickDistance || Math.abs(touchY) > maxStickDistance)) {
				int tempXSign = touchX >= 0 ? 1 : -1;
				int tempYSign = touchY >= 0 ? 1 : -1;

				touchX = stickOriginX + Math.min(Math.abs(touchX), 25)
						* (-tempXSign);
				touchY = stickOriginY + Math.min(Math.abs(touchY), 25)
						* (-tempYSign);

				invalidate();
			} else {
				touchX = tempTouchX;
				touchY = tempTouchY;
			}
			xCor = touchX - stickOriginX;
			yCor = touchY - stickOriginY;
		} else if (actionType == MotionEvent.ACTION_UP) {
			returnToCenter();
		}
		return true;
	}

	private void returnToCenter() {
		Handler handler = new Handler();
		int numberOfFrames = 5;
		final double intervalsX = (stickOriginX - touchX) / numberOfFrames;
		final double intervalsY = (stickOriginY - touchY) / numberOfFrames;

		for (int i = 0; i < numberOfFrames; i++) {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					touchX += intervalsX;
					touchY += intervalsY;

					xCor = touchX - stickOriginX;
					yCor = touchY - stickOriginY;
					invalidate();
				}
			}, i * 40);
		}

	}
}
