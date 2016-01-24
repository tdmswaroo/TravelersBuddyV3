package Notes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.DBHandlerPotovanja;
import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.ArrayList;

import Pomozni.ListViewAdapter;

/**
 * Created by Asura on 19-Jan-16.
 */
public class KovcekAdapter  extends ListViewAdapter {

    public KovcekAdapter(Context context, ArrayList<Kovcek> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.kovcek_vrstica, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.slikaKovcka = (ImageView) view.findViewById(R.id.slikaKovcka);
            viewHolder.textNazivKovcka = (TextView)view.findViewById(R.id.textNazivKovcka);
            viewHolder.potovanjeSlika = (ImageView)view.findViewById(R.id.potovanjeSlika);
            viewHolder.textPotovanjeDo = (TextView)view.findViewById(R.id.textPotovanjeDo);
            viewHolder.potovanjeDatum = (ImageView)view.findViewById(R.id.potovanjeDatum);
            viewHolder.textDatumPotovanja = (TextView)view.findViewById(R.id.textDatumPotovanja);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        Kovcek kovcek = (Kovcek) getTypedItem(position);
        viewHolder.textNazivKovcka.setText(kovcek.getNaziv());

        DBHandlerPotovanja pot = new DBHandlerPotovanja();
        pot.setContext(super.getContext());

        Cursor c = pot.getTripById(kovcek.getIdPotovanja());
        Potovanje p = null;
        if (c .moveToFirst()) {
            while (c.isAfterLast() == false) {
                p = new Potovanje();
                p.setId(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
                p.setPotovanjeOD(c.getString(c.getColumnIndex("potovanjeOd")));
                p.setPotovanjeDO(c.getString(c.getColumnIndex("potovanjeDo")));
                p.setDatumOdhoda(c.getString(c.getColumnIndex("datumOdhoda")));
                p.setCasPotovanja(c.getString(c.getColumnIndex("casPotovanja")));
                p.setTipPrevoza(c.getString(c.getColumnIndex("tipPrevoza")));

                Log.d("Naziv kovčka: " + p, "GET CHECK");

                c.moveToNext();
            }
        }

        viewHolder.textPotovanjeDo.setText(p.getPotovanjeOD()+" - "+p.getPotovanjeDO());
        viewHolder.textDatumPotovanja.setText(p.getDatumOdhoda());

        return view;
    }

    static class ViewHolder {
        ImageView slikaKovcka;
        TextView textNazivKovcka;
        ImageView potovanjeSlika;
        TextView textPotovanjeDo;
        ImageView potovanjeDatum;
        TextView textDatumPotovanja;

    }

}
