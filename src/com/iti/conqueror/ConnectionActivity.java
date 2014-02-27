package com.iti.conqueror;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectionActivity extends Activity {

	private EditText serverIp;
	private Button connectPhones;
	private static TextView Status;

	public static String serverIpAddress = "";
	private boolean connected = false;

	private static Handler handler;
	private Socket socket;
	public static PrintWriter out;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connection);

		serverIp = (EditText) findViewById(R.id.editText1);
		connectPhones = (Button) findViewById(R.id.buttonw);
		Status = (TextView) findViewById(R.id.textVieww);
		handler = new Handler();

		connectPhones.setOnClickListener(connectListener);
	}

	private OnClickListener connectListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!connected) {
				serverIpAddress = serverIp.getText().toString();
				if (!serverIpAddress.equals("")) {

					Thread cThread = new Thread(new ClientThread());
					cThread.start();

					Log.d("Wahman is here", "");

				}
			}
		}
	};

	public static void sendMessage(String Message) {
		try {

			out.println(Message);

			handler.post(new Runnable() {
				@Override
				public void run() {
					Status.setText("BotClient - Command is Sent");
				}
			});

		} catch (Exception e) {

			handler.post(new Runnable() {
				@Override
				public void run() {
					Status.setText("BotClient - Error1");
				}
			});

		}
	}

	public class ClientThread implements Runnable {

		public void run() {
			try {
				final InetAddress serverAddr = InetAddress
						.getByName(serverIpAddress);

				handler.post(new Runnable() {
					@Override
					public void run() {
						Status.setText("Conquerer....BotClient - Connected");
					}
				});

				socket = new Socket(serverAddr, 1234);
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
				sendMessage("Hello Server");
				// connected = true;

				if (!connected) {
					// Intent openMainWindow = new
					// Intent("android.intent.action.CONQUERORACTIVITY");
					// startActivity(openMainWindow);
					startActivity(new Intent(ConnectionActivity.this,
							ConquerorActivity.class));
				}

			} catch (Exception e) {

				handler.post(new Runnable() {
					@Override
					public void run() {
						Status.setText("BotClient - Error2");
					}
				});

				connected = false;
			}
		}
	}

}
