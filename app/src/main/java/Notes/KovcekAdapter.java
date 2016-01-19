package Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.ArrayList;

import Pomozni.ListViewAdapter;

/**
 * Created by Asura on 19-Jan-16.
 */
public class KovcekAdapter  extends ListViewAdapter {

    public KovcekAdapter(Context context, ArrayList<KovcekPotovanje> list) {
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
        KovcekPotovanje kovcek = (KovcekPotovanje) getTypedItem(position);
        viewHolder.textNazivKovcka.setText(kovcek.getNaziv());
        viewHolder.textPotovanjeDo.setText(kovcek.getPotovanjeOD()+" - "+kovcek.getPotovanjeDO());
        viewHolder.textDatumPotovanja.setText(kovcek.getDatumOdhoda());

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
