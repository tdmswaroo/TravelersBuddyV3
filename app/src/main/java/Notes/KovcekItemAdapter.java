package Notes;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.travelerbuddy.feri.travelersbuddy.DBHandlerNotes;
import com.travelerbuddy.feri.travelersbuddy.R;

import java.util.ArrayList;

/**
 * Created by Asura on 16-Jan-16.
 */
public class KovcekItemAdapter extends ArrayAdapter<KovcekItem> {

    public ArrayList<KovcekItem> itemslist;

    public KovcekItemAdapter(Context context, int textViewResourceId,
                         ArrayList<KovcekItem> itemslist) {
        super(context, textViewResourceId, itemslist);
        this.itemslist = new ArrayList<KovcekItem>();
        this.itemslist.addAll(itemslist);
    }

    private class ViewHolder {
        TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.item_vrstica, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    KovcekItem country = (KovcekItem) cb.getTag();
                    final DBHandlerNotes myDBNotes = new DBHandlerNotes();
                    myDBNotes.setContext(v.getContext());

                    boolean r = myDBNotes.checkNote(country.getId());

                    if(r == true) {
                        Snackbar.make(v, "Checked: " + country.getVsebina(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else{
                        Snackbar.make(v, "Something went wrong.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    country.setSelected(cb.isChecked());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        KovcekItem country = itemslist.get(position);
        holder.code.setText(country.getVsebina());
        holder.name.setChecked(country.isSelected());
        holder.name.setTag(country);

        return convertView;

    }


}