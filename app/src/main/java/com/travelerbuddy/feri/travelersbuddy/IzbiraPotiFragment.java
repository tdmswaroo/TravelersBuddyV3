package com.travelerbuddy.feri.travelersbuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import Parsing.Response;

public class IzbiraPotiFragment extends Fragment  {
    ImageButton search;
    EditText from;
    EditText to;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_izbira_poti, container, false);
        System.out.println("POGANJAM ASYNCTASK");
        from = (EditText) view.findViewById(R.id.editTextFrom);
        to = (EditText) view.findViewById(R.id.editTextTo);
        search = (ImageButton)  view.findViewById(R.id.search_orange_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTaskParseJson(new AsyncTaskParseJson.JsonResponse() {
                    @Override
                    public void jsonProcessFinished(Response output) {
                        System.out.println("SEM V CALLBACKU PRED PREUSMERITVIJO" + output.toString());
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, PrevoziFragment.newInstance(output)).addToBackStack("tag").commit();

                    }
                }, from.getText().toString(), to.getText().toString()).execute();


            }
        });

        return view;


    }



}
