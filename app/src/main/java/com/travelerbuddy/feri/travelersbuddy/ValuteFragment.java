package com.travelerbuddy.feri.travelersbuddy;


import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Pomozni.SideMenuItem;
import Pomozni.SpinnerItem;
import Pomozni.ValuteAdapter;
import Pomozni.ValuteAdapter2;


/**
 * A simple {@link Fragment} subclass.
 */
public class ValuteFragment extends Fragment {

    private ValuteAdapter adapter;
    private EditText vnosValute1;
    private EditText vnosValute2;
    private ValuteAdapter2 adapter2;
    private String valuta1 = "EUR";
    private ImageView swapIkona;
    private String valuta2 = "USD";
    private int pozicijaValute1 = 0;
    private Spinner spinner1 = null;
    private Spinner spinner2 = null;
    private int pozicijaValute2 = 2;
    private IzracunValute izracunValute = new IzracunValute();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_valute, container, false);

        vnosValute2 = (EditText) view.findViewById(R.id.textValuta2);
        vnosValute2.setFocusable(false);



        List<SpinnerItem> items = new ArrayList<>();
        items.add(new SpinnerItem("EUR", R.mipmap.eur));
        items.add(new SpinnerItem("GBP", R.mipmap.gbp));
        items.add(new SpinnerItem("USD", R.mipmap.usa));
        items.add(new SpinnerItem("AUD", R.mipmap.aus));
        items.add(new SpinnerItem("CNY", R.mipmap.ch));
        items.add(new SpinnerItem("CHF", R.mipmap.sw));
        items.add(new SpinnerItem("JPY", R.mipmap.jp));
        items.add(new SpinnerItem("NOK", R.mipmap.no));
        items.add(new SpinnerItem("SEK", R.mipmap.swe));
        items.add(new SpinnerItem("INR", R.mipmap.ind));
        items.add(new SpinnerItem("NZD", R.mipmap.nz));
        items.add(new SpinnerItem("DKK", R.mipmap.dn));
        items.add(new SpinnerItem("RUB", R.mipmap.rus));
        items.add(new SpinnerItem("ZAR", R.mipmap.sta));
        items.add(new SpinnerItem("EGP", R.mipmap.eg));
        items.add(new SpinnerItem("BRL", R.mipmap.bra));





        adapter = new ValuteAdapter(getActivity().getApplicationContext(),items);
        adapter2 = new ValuteAdapter2(getActivity().getApplicationContext(),items);

        spinner1 = (Spinner) view.findViewById(R.id.valute1);
        spinner2 = (Spinner) view.findViewById(R.id.valute2);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(2);

        vnosValute1 = (EditText) view.findViewById(R.id.textValuta1);
        vnosValute2 = (EditText) view.findViewById(R.id.textValuta2);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valuta1 = spremeniValuto(position);
                pozicijaValute1 = position;
                new IzracunValute().execute(new String[]{"http://rate-exchange.herokuapp.com/fetchRate?from="+valuta1+"&to="+valuta2,vnosValute1.getText().toString()});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valuta2 = spremeniValuto(position);
                pozicijaValute2 = position;
                new IzracunValute().execute(new String[]{"http://rate-exchange.herokuapp.com/fetchRate?from=" + valuta1 + "&to=" + valuta2, vnosValute1.getText().toString()});

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vnosValute1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new IzracunValute().execute(new String[]{"http://rate-exchange.herokuapp.com/fetchRate?from=" + valuta1 + "&to=" + valuta2, vnosValute1.getText().toString(), "prva"});
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        swapIkona = (ImageView) view.findViewById(R.id.swapIkona);

        swapIkona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuta1 = spremeniValuto(pozicijaValute2);
                valuta2 = spremeniValuto(pozicijaValute1);
                spinner1.setSelection(pozicijaValute2);
                spinner2.setSelection(pozicijaValute1);

                vnosValute1.setText(vnosValute1.getText().toString());

            }
        });

        return view;
    }

    private class IzracunValute extends AsyncTask<String, Void, String>{

        private Double izracun = null;
        private Double izracun2 = null;

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();

            try {

                URL url = new URL(params[0]);
                URLConnection conn;
                conn = url.openConnection();

                HttpURLConnection httpConn = (HttpURLConnection) conn;
                int responseCode = httpConn.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){

                    InputStream in = httpConn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader((in)));

                    String out;
                    while((out = br.readLine()) != null){
                        sb.append(out);
                    }

                    JSONObject json = new JSONObject(sb.toString());

                    String rate = json.getString("Rate");

                    Double pretvorba = Double.parseDouble(rate);
                    Double kolicina = Double.parseDouble(params[1]);

                    izracun = kolicina*pretvorba;


                }



            }catch (Exception e){
                Log.d("NAPAKA",e.getMessage());
            }


            return ""+izracun;
        }

        @Override
        protected void onPostExecute(String s) {
            vnosValute2 = (EditText) getView().findViewById(R.id.textValuta2);
            if(izracun != null) {
                DecimalFormat df = new DecimalFormat("#.## ");
                df.setRoundingMode(RoundingMode.CEILING);
                vnosValute2.setText("" + df.format(izracun));
            }else {
                vnosValute2.setText("");
            }
        }
    }

    public String spremeniValuto(int position){
        switch(position) {

            case 0:
                return "EUR";
            case 1:
                return "GBP";
            case 2:
                return "USD";
            case 3:
                return "AUD";
            case 4:
                return "CNY";
            case 5:
                return "CHF";
            case 6:
                return "JPY";
            case 7:
                return "NOK";
            case 8:
                return "SEK";
            case 9:
                return "INR";
            case 10:
                return "NZD";
            case 11:
                return "DKK";
            case 12:
                return "RUB";
            case 13:
                return "ZAR";
            case 14:
                return "EGP";
            case 15:
                return "BRL";
            default:
                return "EUR";

        }
    }
}
