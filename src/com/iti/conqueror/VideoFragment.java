package com.iti.conqueror;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VideoFragment extends Fragment {

	TextView tv1, tv2;

	RelativeLayout rl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.video_fragment, container, false);

		tv1 = (TextView) v.findViewById(R.id.tv1);
		tv2 = (TextView) v.findViewById(R.id.tv2);

		rl = (RelativeLayout) v.findViewById(R.id.main);

		// LinearLayout ll = new LinearLayout(getActivity());
		// ll.addView(new com.iti.conqueror.util.AnalogStick(getActivity(),
		// tv1, tv2));

		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		 rl.addView(new com.iti.conqueror.util.AnalogStick(getActivity(), tv1,
		 tv2));

	}
}
