package com.travelerbuddy.feri.travelersbuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import Parsing.Response;


public class PrevoziFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Response response;




    public static PrevoziFragment newInstance(Response response) {
        PrevoziFragment fragment = new PrevoziFragment();
        Bundle args = new Bundle();
        args.putParcelable("response", response);


        fragment.setArguments(args);
        return fragment;

    }

    public PrevoziFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            response = getArguments().getParcelable("response");



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment









        View view = inflater.inflate(R.layout.fragment_prevozi, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (response != null) {
            adapter = new RecyclerViewAdapter(getContext(), response, getFragmentManager());
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }





}
