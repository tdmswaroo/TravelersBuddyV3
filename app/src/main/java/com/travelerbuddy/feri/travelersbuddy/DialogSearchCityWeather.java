package com.travelerbuddy.feri.travelersbuddy;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

import Notes.Kovcek;
import Notes.Potovanje;

/**
 * Created by Nino on 22-Jan-16.
 */
public class DialogSearchCityWeather extends DialogFragment {

    private Button prekiniSearch;
    private EditText cityName;
    private Button searchCityGumb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dialog_search_city_weather, container, false);

        cityName = (EditText) rootView.findViewById(R.id.cityName);
        searchCityGumb = (Button) rootView.findViewById(R.id.searchCityGumb);

        searchCityGumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cityWeather",cityName.getText().toString());
                editor.commit();

                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.content_frame, new VremeFragment());
                tx.commit();
                dismiss();
            }
        });

        prekiniSearch = (Button) rootView.findViewById(R.id.dismissSearch);
        prekiniSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

}
