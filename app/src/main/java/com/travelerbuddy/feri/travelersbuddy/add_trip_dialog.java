package com.travelerbuddy.feri.travelersbuddy;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Notes.Potovanje;


public class add_trip_dialog extends DialogFragment {

    private Spinner sp;
    private Spinner spT;
    private Potovanje potovanje = null;
    final DBHandlerPotovanja myDBpotovanja = new DBHandlerPotovanja();

    View superView;

    private DatePicker datePicker;
    private Calendar calendar;
    private EditText dateView;
    private int year, month, day;
    private Button dateBut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_trip_dialog, container, false);
        myDBpotovanja.setContext(rootView.getContext());
        superView = rootView;

        final EditText tripFrom = (EditText) rootView.findViewById(R.id.typeTripFrom);
        final EditText tripTo = (EditText) rootView.findViewById(R.id.typeTripTo);
        final EditText timeOfTravelText = (EditText) rootView.findViewById(R.id.timeOfTravelText);
        sp = (Spinner) rootView.findViewById(R.id.casPrevozaArray);

        spT = (Spinner) rootView.findViewById(R.id.tipPrevozaArray);


        dateView = (EditText) rootView.findViewById(R.id.editTextDate);
        dateBut = (Button) rootView.findViewById(R.id.buttonSetDate);
        dateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(rootView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar c = Calendar.getInstance();
                        c.set(selectedyear, selectedmonth, selectedday);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        String formattedDate = sdf.format(c.getTime());
                        System.out.print(formattedDate);
                        dateView.setText(formattedDate);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Insert canceled.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dismiss();
            }
        });

        Button insert = (Button) rootView.findViewById(R.id.insert_trip);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Potovanje potov = new Potovanje();
                potov.setPotovanjeOD(tripFrom.getText().toString());
                potov.setPotovanjeDO(tripTo.getText().toString());
                potov.setDatumOdhoda(dateView.getText().toString());
                potov.setCasPotovanja(timeOfTravelText.getText().toString() + " " + sp.getSelectedItem());
                potov.setTipPrevoza(spT.getSelectedItem().toString());


                boolean worked = false;

                try {
                    worked = myDBpotovanja.insertPotovanje(potov.getPotovanjeOD(),potov.getPotovanjeDO(),potov.getDatumOdhoda(),potov.getCasPotovanja(),potov.getTipPrevoza());
                } catch (Exception e) {
                    Log.d("NEKAJ JE NAROBE", "CHECK");
                }

                if (worked == true) {
                    Snackbar.make(v, "Trip added.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //KovcekFragment.this.refresh(new KovcekFragment());
                }
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.content_frame, new MojaPotovanjaFragment());
                tx.commit();
                dismiss();

            }
        });

        return rootView;
    }

}

