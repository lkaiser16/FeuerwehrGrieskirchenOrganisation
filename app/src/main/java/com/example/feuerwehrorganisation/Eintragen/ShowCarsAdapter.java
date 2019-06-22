package com.example.feuerwehrorganisation.Eintragen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.feuerwehrorganisation.Fahrzeug;
import com.example.feuerwehrorganisation.R;

import java.util.List;

public class ShowCarsAdapter extends ArrayAdapter<Fahrzeug>
{
    private Context mContext;
    private int resourceLayout;

    public ShowCarsAdapter(Context context, int resource, List<Fahrzeug> objects)
    {
        super(context, resource, objects);

        this.resourceLayout = resource;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(mContext).inflate(resourceLayout, null);

        Fahrzeug f = getItem(position);
        TextView v = convertView.findViewById(R.id.textViewFahrzeugeList);

        v.setText(f.getBezeichnung());

        return convertView;
    }

    @Override
    public Fahrzeug getItem(int position)
    {
        return super.getItem(position);
    }
}
