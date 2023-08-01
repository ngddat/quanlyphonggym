package com.example.quanlyphonggym.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlyphonggym.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mMap = googleMap;

                LatLng sydneydn1 = new LatLng(16.075435908785813, 108.17047379523403);
                mMap.addMarker(new MarkerOptions().position(sydneydn1).title("Đạt GYM & Yoga chi nhánh 1"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneydn1));

                LatLng sydneykt2 = new LatLng(14.36408739248377, 107.99829881938612);
                mMap.addMarker(new MarkerOptions().position(sydneykt2).title("Đạt GYM & Yoga chi nhánh 2"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneykt2));

                LatLng sydneyhn3 = new LatLng(20.99158689956519, 105.86720760219323);
                mMap.addMarker(new MarkerOptions().position(sydneyhn3).title("Đạt GYM & Yoga chi nhánh 3"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyhn3));

                LatLng sydneyhcm4 = new LatLng(10.842341801039675, 106.64359412719327);
                mMap.addMarker(new MarkerOptions().position(sydneyhcm4).title("Đạt GYM & Yoga chi nhánh 4"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyhcm4));

                LatLng sydneydn = new LatLng(16.04621001890063, 108.2219929026236);
                mMap.addMarker(new MarkerOptions().position(sydneydn).title("Đạt GYM & Yoga cơ sở chính"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneydn));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydneydn, 15));
            }
        });

        //when map is loaded
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(@NonNull LatLng latLng) {
//                        //when clicked on map
//                        //Initilize marker options
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        //set position of marker
//                        markerOptions.position(latLng);
//                        //set title of marker
//                        markerOptions.title(latLng.latitude + " : " + latLng.latitude);
//                        //remove all marker
//                        googleMap.clear();
//                        //animating to zoom the marker
//                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                        //add marker on map
//                        googleMap.addMarker(markerOptions);

//            }
//        });

        return view;
    }
}
