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
 * Created by Asura on 23-Jan-16.
 */
public class PotovanjeAdapter  extends ListViewAdapter {

    public PotovanjeAdapter(Context context, ArrayList<Potovanje> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.potovanje_vrstica, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.slikaMape = (ImageView) view.findViewById(R.id.slikaMape);
            viewHolder.potovanjeOdDo = (TextView)view.findViewById(R.id.potovanjeOdDo);
            viewHolder.odhodDatumSlika = (ImageView)view.findViewById(R.id.odhodDatumSlika);
            viewHolder.textDatumOdhod = (TextView)view.findViewById(R.id.textDatumOdhod);
            viewHolder.prevozSlika = (ImageView)view.findViewById(R.id.prevozSlika);
            viewHolder.tipPrevoza = (TextView)view.findViewById(R.id.tipPrevoza);
            viewHolder.casPrevozaSlika = (ImageView)view.findViewById(R.id.timerIcon);
            viewHolder.casPrevoza = (TextView)view.findViewById(R.id.casPrevoza);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if(list.size()!=0){
            Potovanje potovanje = (Potovanje) getTypedItem(position);
            viewHolder.potovanjeOdDo.setText(potovanje.getPotovanjeOD() + " - " + potovanje.getPotovanjeDO());
            viewHolder.textDatumOdhod.setText(potovanje.getDatumOdhoda());
            viewHolder.tipPrevoza.setText(potovanje.getTipPrevoza());
            viewHolder.casPrevoza.setText(potovanje.getCasPotovanja());
        }

        return view;
    }

    static class ViewHolder {
        ImageView slikaMape;
        TextView potovanjeOdDo;
        ImageView odhodDatumSlika;
        TextView textDatumOdhod;
        ImageView prevozSlika;
        TextView tipPrevoza;
        ImageView casPrevozaSlika;
        TextView casPrevoza;
    }

}
