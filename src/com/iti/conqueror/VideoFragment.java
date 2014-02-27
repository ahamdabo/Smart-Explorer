package com.iti.conqueror;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.conqueror.mobileAnarchy.Android.Widget.JoystickMovedListener;
import com.iti.conqueror.mobileAnarchy.Android.Widget.JoystickView;

public class VideoFragment extends Fragment {

	TextView tv1, tv2;

	static Handler handler1;
	private ImageView iv;
	JoystickView joystick;

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.video_fragment, container, false);

		tv1 = (TextView) v.findViewById(R.id.tv1);
		tv2 = (TextView) v.findViewById(R.id.tv2);

		joystick = (JoystickView) v.findViewById(R.id.joystickView);
		 iv = (ImageView) v.findViewById(R.id.imageView1);

		handler1 = new Handler(getActivity().getMainLooper(), new Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					iv.setImageBitmap((Bitmap) msg.obj);
				}
				return false;
			}
		});

		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		joystick.setOnJostickMovedListener(_listener);
		new Thread(new CamActivity(ConnectionActivity.serverIpAddress));
		super.onStart();

	}

	private JoystickMovedListener _listener = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			tv1.setText(Integer.toString(pan));
			tv2.setText(Integer.toString(tilt));
			if(1 < pan && pan <= 10)
			 {
				ConnectionActivity.sendMessage("Right");
			 }
			
			if(-10 < pan && pan <= 0)
			 {
				ConnectionActivity.sendMessage("Left");
			 }
			
			if(1 < tilt && tilt <= 10)
			 {
				ConnectionActivity.sendMessage("Down");
			 }
			
			if(-10 < tilt && tilt <= 0)
			 {
				ConnectionActivity.sendMessage("Up");
			 }
			
			
		}

		@Override
		public void OnReleased() {
			tv1.setText("released");
			tv2.setText("released");
			ConnectionActivity.sendMessage("Stop");
		}

		public void OnReturnedToCenter() {
			tv1.setText("stopped");
			tv2.setText("stopped");
		};
	};

}
