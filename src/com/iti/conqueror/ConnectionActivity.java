package com.iti.conqueror;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends Activity {

	private EditText serverIp;
	private Button connectPhones;

	public String serverIpAddress = "";
	private boolean connected = false;

	private ServerThread s = new ServerThread(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection);

		serverIp = (EditText) findViewById(R.id.editText1);
		connectPhones = (Button) findViewById(R.id.buttonw);

		connectPhones.setOnClickListener(connectListener);
	}

	private OnClickListener connectListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!connected) {
				serverIpAddress = serverIp.getText().toString();
				if (!serverIpAddress.equals("")) {

					s.t_server.start();

					startActivity(new Intent(ConnectionActivity.this,
							ConquerorActivity.class));
					Log.d("WahmaaaN: ", "startvid activ");

				}
			}
		}
	};
}
