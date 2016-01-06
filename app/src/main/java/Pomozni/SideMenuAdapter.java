package Pomozni;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.List;

/**
 * Created by Nino on 06-Jan-16.
 */
public class SideMenuAdapter extends ListViewAdapter {

    public SideMenuAdapter(Context context, List<SideMenuItem> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sidemenu_vrstica, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.itemName = (TextView)view.findViewById(R.id.itemName);
            viewHolder.ikona = (ImageView)view.findViewById(R.id.ikona);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        SideMenuItem item = (SideMenuItem) getTypedItem(position);
        viewHolder.itemName.setText(item.getIme());
        viewHolder.ikona.setImageResource(item.getIkona());

        return view;
    }

    static class ViewHolder {
        TextView itemName;
        ImageView ikona;
    }

}
