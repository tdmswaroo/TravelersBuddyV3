package Pomozni;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nino on 06-Jan-16.
 */
public abstract class ListViewAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected final Context context;

    public ListViewAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;
    }

    public T getTypedItem(int position) {
        if(position >= getList().size()) return null;
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= getList().size()) return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        if(position > getList().size()) return -1;
        return position;
    }

    public Context getContext() {
        return context;
    }

    public List<T> getList() {
        if(list == null) list = new ArrayList<T>();
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
