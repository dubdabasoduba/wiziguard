package com.amisa.wiziguard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class location extends AppCompatActivity {
	double x;
	double y;
	Geocoder geocoder;
	List<Address> addresses;
	private TextView latitude;
	private TextView longitude;
	private TextView provText;
	private String provider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		latitude = (TextView) findViewById(R.id.lat);
		longitude = (TextView) findViewById(R.id.lon);
		provText = (TextView) findViewById(R.id.prov);
		/*choice = (TextView) findViewById(R.id.choice);
		fineAcc = (CheckBox) findViewById(R.id.fineAccuracy);
        choose = (Button) findViewById(R.id.chooseRadio);*/
		TextView address = (TextView) findViewById(R.id.address);

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);   //default

      /*  choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fineAcc.isChecked()){
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    choice.setText("fine accuracy selected");
                }else {
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    choice.setText("coarse accuracy selected");
                }
            }
        });*/
		criteria.setCostAllowed(false);

		provider = locationManager.getBestProvider(criteria, true);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
			return;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		MyLocationListener mylistener = new MyLocationListener();
		if (location != null) {
			mylistener.onLocationChanged(location);
		}
		locationManager.requestLocationUpdates(provider, 0, 0, mylistener);

	}

	private class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			latitude.setText("Latitude: " + location.getLatitude());
			longitude.setText("Longitude: " + location.getLongitude());
			provText.setText(provider + " provider has been selected.");
			Toast.makeText(location.this, "Location changed!", Toast.LENGTH_SHORT).show();

			x = location.getLatitude();
			y = location.getLongitude();

			try {
				geocoder = new Geocoder(location.this, Locale.ENGLISH);
				addresses = geocoder.getFromLocation(x, y, 1);
				StringBuilder str = new StringBuilder();
				if (Geocoder.isPresent()) {
//                        Toast.makeText(getApplicationContext(),"geocoder present", Toast.LENGTH_SHORT).show();
					if (addresses.size() > 0) {
						Address returnAddress = addresses.get(0);

						String localityString = returnAddress.getLocality();
						String city = returnAddress.getCountryName();
						String region_code = returnAddress.getCountryCode();
//                        String zipcode = returnAddress.getPostalCode();

						str.append(localityString).append(",");
						str.append(city).append(",").append(region_code).append(".");
//                        str.append(zipcode + "\n");

//                        address.setText(str);
						Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
					}

				}
//          else {
////                        Toast.makeText(getApplicationContext(),"geocoder not present", Toast.LENGTH_SHORT).show();
//                    }
			} catch (IOException e) {
// TODO Auto-generated catch block

				Log.e("tag", e.getMessage());
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(location.this, provider + "'s status changed to " + status + "!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(location.this, "Provider " + provider + " enabled!", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderDisabled(String provider) {

			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
			Toast.makeText(location.this, "Provider " + provider + " disabled!", Toast.LENGTH_SHORT).show();
		}
	}

}

