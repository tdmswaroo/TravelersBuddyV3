package com.travelerbuddy.feri.travelersbuddy;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Parsing.Route;
import Parsing.Segment;

/**
 * Created by Klemen on 24.1.2016.
 */
public class RouteRecyclerViewAdapter  extends RecyclerView.Adapter<RouteRecyclerViewAdapter.CustomRouteViewHolder> {

    private Context mContext;
    private Route route;
    private FragmentManager frgMng;




    private ArrayList<Segment> segments;


    public RouteRecyclerViewAdapter(Context context,  Route route, FragmentManager frgMng) {
        this.frgMng = frgMng;
        this.mContext = context;
        this.route = route;
        this.segments = route.getSegments();
    }

    @Override
    public CustomRouteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.segment_list_row, null);

        CustomRouteViewHolder viewHolder = new CustomRouteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomRouteViewHolder holder, int position) {
        Segment segment = segments.get(position);
        holder.sName.setText(segment.getsName());
        holder.tName.setText(segment.gettName());
        holder.url.setText(segment.getUrl());
        holder.kind.setText(segment.getKind());

        float d = (segment.getDuration());

        int hours = (int)d / 60; //since both are ints, you get an int
        int minutes = (int)d % 60;
        holder.duration.setText(hours+"h "+minutes+"min");

        float p = (segment.getIndicativePrice().getPrice());
        if(p == (long) p)
            holder.price.setText(String.format("%d",(long)p)+" "+segment.getIndicativePrice().getCurrency());
        else
            holder.price.setText(String.format("%s",p)+" "+segment.getIndicativePrice().getCurrency());


        if(segment.getKind().equals("bus")){
            holder.slika.setImageResource(R.drawable.bus);
        }else if(segment.getKind().equals("train")){
            holder.slika.setImageResource(R.drawable.train);
        }else if(segment.getKind().equals("car")){
            holder.slika.setImageResource(R.drawable.car);
        }else if(segment.getKind().equals("plane")){
            holder.slika.setImageResource(R.drawable.plane);
        }else if(segment.getKind().equals("taxi")){
            holder.slika.setImageResource(R.drawable.car);
        }



    }



    @Override
    public int getItemCount() {
        return (null != segments ? segments.size() : 0);
    }





    public class CustomRouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        protected TextView url;
        protected TextView kind;
        protected TextView sName;
        protected TextView tName;
        protected TextView duration;
        protected TextView price;
        protected ImageView slika;


        public CustomRouteViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.slika = (ImageView) view.findViewById(R.id.image);
            this.url = (TextView) view.findViewById(R.id.url);
            this.kind = (TextView) view.findViewById(R.id.kind);
            this.sName = (TextView) view.findViewById(R.id.sName);
            this.tName = (TextView) view.findViewById(R.id.tName);
            this.duration = (TextView) view.findViewById(R.id.duration);
            this.price = (TextView) view.findViewById(R.id.price);


        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, sName.getText(), Toast.LENGTH_SHORT).show();
           // frgMng.beginTransaction().replace(R.id.content_frame, SestavaPotiFragment.newInstance(route)).commit();

        }
    }

}
