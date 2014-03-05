package com.iti.conqueror;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.iti.conqueror.util.SettingsActivity;

/**
 * is to be called before creating view and after any modifications to the
 * settings
 * 
 */

public class ConquerorActivity extends FragmentActivity {

	public final int RESULT_SETTINGS = 1;
	boolean audio, video;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conqueror);

	}

	void setUserSettings() {
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		StringBuilder builder = new StringBuilder();

		if (sharedPrefs.getBoolean("enablevideo", true)) {

			video = true;
			builder.append("Video-> true\n");

		} else {
			video = false;
			builder.append("Video-> false\n");
		}

		if (sharedPrefs.getBoolean("enableaudio", true)) {
			audio = true;
			builder.append("audio-> true\n");
		} else {
			audio = false;
			builder.append("audio-> false\n");
		}

		Log.i("Audio-Video", "Values.. " + builder.toString());

	}

	void connectServer(Activity x, Point xy) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			setUserSettings();
			break;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConquerorActivity.this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conqueror, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case R.id.action_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;

		case R.id.action_connection:
			startActivity(new Intent(ConquerorActivity.this,
					ConnectionActivity.class));

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}
}