package com.travelerbuddy.feri.travelersbuddy.travelersbuddy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.travelerbuddy.feri.travelersbuddy.R;


public class DogodkiFragment extends Fragment {

    private EditText vnosLokacije;
    private Spinner mesci;

    public DogodkiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dogodki, container, false);



        return view;
    }

}
