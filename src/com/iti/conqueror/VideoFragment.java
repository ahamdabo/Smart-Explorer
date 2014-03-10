package com.iti.conqueror;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.conqueror.mobileAnarchy.Android.Widget.JoystickMovedListener;
import com.iti.conqueror.mobileAnarchy.Android.Widget.JoystickView;

public class VideoFragment extends Fragment {

	TextView tv1, tv2;
	public TextView tv3;
	public static ImageView vImage;

	Handler handler1;
	JoystickView joystick;

	ServerThread serverUI = new ServerThread(this);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.video_fragment, container, false);

		tv1 = (TextView) v.findViewById(R.id.tv1);
		tv2 = (TextView) v.findViewById(R.id.tv2);
		tv3 = (TextView) v.findViewById(R.id.tv3);
		vImage = (ImageView) v.findViewById(R.id.imageView1);
		handler1 = new Handler();
		joystick = (JoystickView) v.findViewById(R.id.joystickView);
		Log.d("WahmaaaN: ", "befor tui");
		serverUI.tUI.start();
		serverUI.startVideo();
		Log.d("WahmaaaN: ", "After tui");
		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		joystick.setOnJostickMovedListener(_listener);
		super.onStart();

	}

	private JoystickMovedListener _listener = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			tv1.setText(Integer.toString(pan));
			tv2.setText(Integer.toString(tilt));
			if (7 < pan && pan <= 10) {
				ServerThread.sendMessage("Right");
			}

			if (-10 < pan && pan <= -7) {
				ServerThread.sendMessage("Left");
			}

			if (7 < tilt && tilt <= 10) {
				ServerThread.sendMessage("Down");
			}

			if (-10 < tilt && tilt <= -7) {
				ServerThread.sendMessage("Up");
			}

		}

		@Override
		public void OnReleased() {
			tv1.setText("released");
			tv2.setText("released");
		}

		public void OnReturnedToCenter() {
			tv1.setText("stopped");
			tv2.setText("stopped");
			ServerThread.sendMessage("Stop");

		};
	};

}
