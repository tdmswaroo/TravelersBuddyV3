package com.travelerbuddy.feri.travelersbuddy;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Parsing.Place;
import Parsing.Response;
import Parsing.Route;

/**
 * Created by Klemen on 18.1.2016.
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private Response response;
    private Context mContext;
    private ArrayList<Route> routes;
    private ArrayList<Place> places;
    private FragmentManager frgMng;


    public RecyclerViewAdapter(Context context, Response response, FragmentManager frgMng) {
        this.frgMng = frgMng;
        this.response = response;
        this.mContext = context;
        this.routes = response.getRoutes();
        this.places = response.getPlaces();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_list_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.CustomViewHolder holder, int position) {
        Route route =  routes.get(position);
        holder.sName.setText(route.getName());
        holder.route = route;
        float d = (route.getDuration());

        int hours = (int)d / 60; //since both are ints, you get an int
        int minutes = (int)d % 60;
        holder.duration.setText(hours+"h "+minutes+"min");

        float p = (route.getIndicativePrice().getPrice());
        if(p == (long) p)
            holder.price.setText(String.format("%d",(long)p)+" "+route.getIndicativePrice().getCurrency());
        else
            holder.price.setText(String.format("%s",p)+" "+route.getIndicativePrice().getCurrency());



    }



    @Override
    public int getItemCount() {
        return (null != routes ? routes.size() : 0);
    }





    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        protected TextView sName;
        protected TextView price;
        protected TextView duration;
        protected Route route;


        public CustomViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.sName = (TextView) view.findViewById(R.id.sName);
            this.price = (TextView) view.findViewById(R.id.price);
            this.duration = (TextView) view.findViewById(R.id.duration);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, sName.getText(), Toast.LENGTH_SHORT).show();
            frgMng.beginTransaction().replace(R.id.content_frame, SestavaPotiFragment.newInstance(route)).addToBackStack("tag").commit();

        }
    }

}
