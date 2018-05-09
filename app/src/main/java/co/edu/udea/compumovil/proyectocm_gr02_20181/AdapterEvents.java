package co.edu.udea.compumovil.proyectocm_gr02_20181;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by stefanny.toro on 8/05/18.
 */

public class AdapterEvents extends BaseAdapter {
    Activity activity;
    List<Event> listEvent;
    LayoutInflater inflater;

    public AdapterEvents(Activity activity, List<Event> listEvent, LayoutInflater inflater) {
        this.activity = activity;
        this.listEvent = listEvent;
        this.inflater = inflater;
        //jdhdhd
    }

    @Override
    public int getCount() {
        return listEvent.size();
    }

    @Override
    public Object getItem(int i) {
        return listEvent.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)activity.getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       // falta organizar View itemView = inflater.inflate(R.layout.fragment_cardview_eventos,null);
/*
        TextView txtEvent = (TextView)itemView.findViewById(R.id.txtCV_Event);
        TextView txtEventFrom = (TextView)itemView.findViewById(R.id.txtCV_EventFrom);
        TextView txtEventTo = (TextView)itemView.findViewById(R.id.txtCV_EventTo);


        txtEvent.setText(listEvent.get(i).getUid());
        txtEventFrom.setText(listEvent.get(i).getOrigen());
        txtEventTo.setText(listEvent.get(i).getDestino());*/

        return view;

    }
}
