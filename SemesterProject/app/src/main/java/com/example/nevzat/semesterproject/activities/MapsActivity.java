package com.example.nevzat.semesterproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nevzat.semesterproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);

        if (googleMap==null){
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
            if (googleMap!=null){
                googleMap.clear();
                Bundle bundle = getIntent().getExtras();
                LatLng location = new LatLng(bundle.getDouble("Latitude"),bundle.getDouble("Longitude"));
                googleMap.addMarker(new MarkerOptions().position(location).title("Marker in Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        }

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("DEBUG", "Map clicked [" + latLng.latitude + " / " + latLng.longitude + "]");
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Location"));
                Toast.makeText(getApplicationContext(),"New Location.",Toast.LENGTH_LONG).show();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                ReturnMapData(latLng);
            }

        });


    }

    public void ReturnMapData(LatLng arg) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("latitude", String.valueOf(arg.latitude));
        resultIntent.putExtra("longitude", String.valueOf(arg.longitude));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        LatLng location = new LatLng(bundle.getDouble("Latitude"),bundle.getDouble("Longitude"));
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker in Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        Log.d("DEBUG", "Location Changed [" + latLng.latitude + " / " + latLng.longitude + "]");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
