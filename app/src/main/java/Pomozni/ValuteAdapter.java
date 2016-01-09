package Pomozni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.List;

/**
 * Created by Nino on 08-Jan-16.
 */
public class ValuteAdapter extends ListViewAdapter {

    public ValuteAdapter(Context context, List<SpinnerItem> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_vrstica, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.itemName = (TextView)view.findViewById(R.id.imeValute);
            viewHolder.ikona = (ImageView)view.findViewById(R.id.ikonaValute1);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        SpinnerItem item = (SpinnerItem) getTypedItem(position);
        viewHolder.itemName.setText(item.getIme());
        viewHolder.ikona.setImageResource(item.getIkona());

        return view;
    }

    static class ViewHolder {
        TextView itemName;
        ImageView ikona;
    }

}

