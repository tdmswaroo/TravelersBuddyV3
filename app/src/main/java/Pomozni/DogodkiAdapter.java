package Pomozni;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nino on 13-Jan-16.
 */
public class DogodkiAdapter extends ListViewAdapter{

    public DogodkiAdapter(Context context, ArrayList<DogodekItem> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dogodek_vrstica, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.imeDogodka = (TextView)view.findViewById(R.id.textNazivDogodka);
            viewHolder.ikonaLokacija = (ImageView)view.findViewById(R.id.dogodekLokacija);
            viewHolder.lokacija = (TextView)view.findViewById(R.id.textLokacijaDogodka);
            viewHolder.ikonaDatum = (ImageView)view.findViewById(R.id.dogodekDatum);
            viewHolder.datum = (TextView)view.findViewById(R.id.textDatumDogodka);
            viewHolder.slikaDogodka = (ImageView) view.findViewById(R.id.slikaDogodka);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        DogodekItem item = (DogodekItem) getTypedItem(position);
        viewHolder.imeDogodka.setText(item.getNaziv());
        viewHolder.slikaDogodka.setImageResource(item.getSlikaDogodka());
        viewHolder.lokacija.setText(item.getLokacija());
        viewHolder.datum.setText(item.getDatum());

        return view;
    }

    static class ViewHolder {
        TextView lokacija;
        TextView datum;
        TextView imeDogodka;
        ImageView ikonaLokacija;
        ImageView ikonaDatum;
        ImageView slikaDogodka;

    }

}

