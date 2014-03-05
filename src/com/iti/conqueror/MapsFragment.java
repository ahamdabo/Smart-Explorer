package com.iti.conqueror;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 
 * @author Ahmed
 * 
 *         This class is responsible for displaying the location on the map
 */
public class MapsFragment extends Fragment {

	private GoogleMap googleMap;
	double latitude = 30;
	double longitude = 31;
	private static final LatLng SYDNEY = new LatLng(-33.88, 151.21);
	private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
	MarkerOptions marker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.maps_fragment, container, false);

		return v;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		googleMap.setTrafficEnabled(true);
		try {
			// Loading map
			marker = new MarkerOptions().position(
					new LatLng(latitude, longitude)).title("Hello Egypt.. ");

			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED));

			googleMap.addMarker(marker);

			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setTiltGesturesEnabled(true);
			googleMap.getUiSettings().setAllGesturesEnabled(true);
			googleMap.setBuildingsEnabled(true);

			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

			// Zoom in, animating the camera.
			googleMap.animateCamera(CameraUpdateFactory.zoomIn());

			// Zoom out to zoom level 10, animating with a duration of 2
			// seconds.
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

			// Construct a CameraPosition focusing on Mountain View and animate
			// the camera to that position.
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(MOUNTAIN_VIEW) // Sets the center of the map to
											// Mountain View
					.zoom(17) // Sets the zoom
					.bearing(90) // Sets the orientation of the camera to east
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
