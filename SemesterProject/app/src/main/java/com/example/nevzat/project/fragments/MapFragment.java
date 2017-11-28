package com.example.nevzat.project.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nevzat.semesterproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends FragmentActivity {

    public MapFragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_map, container, false);
        /*if (googleM==null) {
            googleM = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment)).getMap();
            if (googleM!=null){
                LatLng mCoordinate = new LatLng(43.02,28.72);
                googleM.moveCamera(CameraUpdateFactory.newLatLngZoom(mCoordinate, 13));
            }
        }*/
        return v;
    }





}
