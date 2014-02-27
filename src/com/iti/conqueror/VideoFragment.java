package com.iti.conqueror;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MobileAnarchy.Android.Widgets.Joystick.JoystickMovedListener;
import com.MobileAnarchy.Android.Widgets.Joystick.JoystickView;

public class VideoFragment extends Fragment {

	TextView tv1, tv2;
	JoystickView joystick;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.video_fragment, container, false);

		tv1 = (TextView) v.findViewById(R.id.tv1);
		tv2 = (TextView) v.findViewById(R.id.tv2);
		joystick = (JoystickView) v.findViewById(R.id.joystickView);

		// LinearLayout ll = new LinearLayout(getActivity());
		// ll.addView(new com.iti.conqueror.util.AnalogStick(getActivity(),
		// tv1, tv2));
		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		joystick.setOnJostickMovedListener(_listener);
	}

	private JoystickMovedListener _listener = new JoystickMovedListener() {

		@Override
		public void OnMoved(int pan, int tilt) {
			tv1.setText(Integer.toString(pan));
			tv2.setText(Integer.toString(tilt));
		}

		@Override
		public void OnReleased() {
			tv1.setText("released");
			tv2.setText("released");
		}

		public void OnReturnedToCenter() {
			tv1.setText("stopped");
			tv2.setText("stopped");
		};
	};

}
