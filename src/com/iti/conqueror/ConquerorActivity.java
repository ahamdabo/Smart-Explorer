package com.iti.conqueror;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.iti.conqueror.util.SettingsActivity;

public class ConquerorActivity extends FragmentActivity {

	public final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conqueror);
	}

	/**
	 * is to be called before creating view and after any modifications to the
	 * settings
	 */
	void showUserSettings() {
		// PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		// SharedPreferences sharedPrefs = PreferenceManager
		// .getDefaultSharedPreferences(this);
		//
		// StringBuilder builder = new StringBuilder();
		// builder.append("\n item: " + sharedPrefs.getString("key", "NULL"));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		

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

		default:
			return super.onOptionsItemSelected(item);

		}

	}
}