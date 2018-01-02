package com.emp.findhotel.findhotel;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luka on 18. 12. 2017.
 */

public class Near extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_near, container, false);

        //koda, API za Google maps

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) v.findViewById(R.id.googlemaps);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        Geocoder geocoder = new Geocoder(getContext());

        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocationName("Slovenska cesta 34, 1000 Ljubljana", 1);
            LatLng hotel = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(hotel).title("Hotel Slon");
            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(hotel));
            mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
