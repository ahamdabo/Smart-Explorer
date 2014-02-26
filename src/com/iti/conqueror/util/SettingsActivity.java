package com.iti.conqueror.util;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.iti.conqueror.R;

public class SettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.settings);
		addPreferencesFromResource(R.xml.settings);

	}

}
