package com.travelerbuddy.feri.travelersbuddy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class DogodkiFragment extends Fragment {

    private EditText vnosLokacije;
    private Spinner mesci;
    private Spinner kategorije;
    private String izbranMesec = "January";
    private String izbranaKategorija = "";
    private Button gumb;

    public DogodkiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dogodki, container, false);

        vnosLokacije = (EditText) view.findViewById(R.id.vnosLokacijeDogodkov);
        mesci = (Spinner) view.findViewById(R.id.mesci);
        kategorije = (Spinner) view.findViewById(R.id.kategorija);

        vnosLokacije.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    hideKeyboard();
                    vnosLokacije.clearFocus();
                    mesci.requestFocus();
                    mesci.performClick();
                }
                return true;
            }
        });

        kategorije.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                izbranaKategorija = dolociKat(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gumb = (Button) view.findViewById(R.id.najdiDogodkeGumb);

        gumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(),DogodkiActivity.class);
                i.putExtra("lokacija",vnosLokacije.getText().toString().replace(" ",""));
                i.putExtra("mesec",mesci.getSelectedItem().toString());
                i.putExtra("kat",izbranaKategorija);
                startActivity(i);

            }
        });

        return view;
    }

    public String dolociKat(int position) {
        switch (position) {
            case 0:
                return "music";
            case 1:
                return "conference";
            case 2:
                return "comedy";
            case 3:
                return "family_fun_kids";
            case 4:
                return "movies_film";
            case 5:
                return "attractions";
            case 6:
                return "sports";
            case 7:
                return "technology";
            case 8:
                return "other";
            default:
                return "";
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
