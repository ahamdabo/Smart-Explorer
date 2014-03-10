package com.iti.conqueror;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;

/**
 * 
 * @author Ahmed
 * @see * Client/Server class for receiving the video streaming and control the
 *      bot..
 * 
 */

public class ServerThread {

	public BufferedInputStream input;

	public static String ReceivedMessage = "";
	public static String line = null;

	public Socket socket;

	// Camera Server and port
	public static Socket imageSocket;
	public static final int CSERVERPORT = 1235;
	Bitmap bm;
	public static PrintWriter out;
	private ConnectionActivity Main_Connection;
	public VideoFragment Main_Video;

	public ServerThread(ConnectionActivity c) {
		Main_Connection = c;
	}

	public ServerThread(VideoFragment c) {
		Main_Video = c;
	}

	Thread tUI = new Thread(new Runnable() {

		public void run() {

			while (true) {
				try {
					Thread.sleep(500);

				} catch (InterruptedException e) {
				}

				Main_Video.handler1.post(new Runnable() {

					public void run() {
						Main_Video.tv3.setText("Received msg: "
								+ ReceivedMessage);
					}
				});
			}
		}
	});

	Thread t_server = new Thread(new Runnable() {
		public void run() {

			try {

				final InetAddress serverAddr = InetAddress
						.getByName(Main_Connection.serverIpAddress);

				socket = new Socket(serverAddr, 1234);

				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				try {
					imageSocket = new Socket(serverAddr, CSERVERPORT);
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("Error", "couldn't start video.. ");
				}

				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);

				sendMessage("Hello Server");

				// startVideo();

				while (socket.isConnected()) {
					while ((line = in.readLine()) != null) {

						ReceivedMessage = line;
						Log.d(":::WahmaaaN::TA7T:: ", ReceivedMessage);

					}
				}

			} catch (Exception e) {
			}
		}
	});

	public static void sendMessage(String Message) {
		try {

			out.println(Message);

		} catch (Exception e) {

		}
	}

	public void startVideo() {

		try {
			final ImageView frame = VideoFragment.vImage;
			Thread imageThread = new Thread(new Runnable() {
				public void run() {
					try {

						Main_Video.handler1.post(new Runnable() {
							public void run() {
								// Log.d("::HeeeeMaaa BAsha:: ",
								// "handeler ettani");

								// Main_Video.tv3.setText("YARAB");
							}
						});

						// console("Listening for video\n");
						BufferedInputStream input = new BufferedInputStream(
								ServerThread.imageSocket.getInputStream());
						byte[] buffer = new byte[512000];
						while (imageSocket.isConnected()) {
							int i = 0;
							boolean capture = false;
							byte prev = 0;
							byte cur = 0;

							while (!capture) {
								cur = (byte) input.read();
								// SOI
								if (cur == (byte) 0xD8 && prev == (byte) 0xFF) {
									// console("found header!\n");
									buffer[i++] = prev;
									buffer[i++] = cur;

									while (!capture) {

										buffer[i++] = (byte) input.read();
										// EOI
										if (buffer[i - 1] == (byte) 0xD9
												&& buffer[i - 2] == (byte) 0xFF) {
											// console("done capture\n");
											bm = BitmapFactory.decodeByteArray(
													buffer, 0, i);
											if (bm != null) {
												// console("setting bitmap");
												int w = bm.getWidth();
												int h = bm.getHeight();
												// Setting post rotate to 90
												Matrix mtx = new Matrix();
												mtx.postRotate(90);
												// Rotating Bitmap
												final Bitmap rotatedbm = Bitmap
														.createBitmap(bm, 0, 0,
																w, h, mtx, true);

												Main_Video.handler1
														.post(new Runnable() {
															public void run() {
																frame.setImageBitmap(rotatedbm);
															}
														});
											} else {
												// console("failed to decode\n");
											}
											capture = true;
										}

									}

								} else {
									prev = cur;
								}
							}
						}
					} catch (Exception e) {
						// console(e);
					}
				}
			}, "imageSender");
			imageThread.setDaemon(true);
			imageThread.start();
		} catch (Exception e) {
			// console(e);
		}

	}

}
