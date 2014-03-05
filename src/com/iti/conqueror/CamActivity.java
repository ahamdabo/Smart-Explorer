package com.iti.conqueror;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class CamActivity implements Runnable {

	public static final int ClientPort = 1235;
	private static String ip;
	Bitmap bm;
	ServerSocket imageServerSocket;
	public Socket imageSocket;

	public CamActivity(String s) {
		ip = s;
	}

	public void run() {
		startVideo();
	}

	void startVideo() {
		try {
			imageSocket = new Socket(InetAddress.getByName(ip), 1235);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						Log.v("BotCam", "Listening for video\n");
						BufferedInputStream input = new BufferedInputStream(
								imageSocket.getInputStream());
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
												final Bitmap bitmap = Bitmap
														.createBitmap(bm, 0, 0,
																w, h, mtx, true);

												Log.v("BotCam",
														"Got a new Bitmap");
												VideoFragment.handler1
														.obtainMessage(0,
																bitmap)
														.sendToTarget();

											} else {
												Log.v("BotCam",
														"failed to decode\n");
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
						e.printStackTrace();
					}
				}
			}, "imageSender");
			thread.setDaemon(true);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
