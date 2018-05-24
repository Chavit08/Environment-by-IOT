package com.example.chavit.projectenvnew;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public double latitude , longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle bundle = getIntent().getExtras();
        latitude = bundle.getDouble("Latitude");
        longitude = bundle.getDouble("Longitude");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mydevice = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(mydevice).title("Marker in MyDevice"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mydevice));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(20.0f);

    }

}
