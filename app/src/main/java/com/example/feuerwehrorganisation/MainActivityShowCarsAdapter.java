package com.example.feuerwehrorganisation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.feuerwehrorganisation.NewAuftrag.Auftrag;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivityShowCarsAdapter extends ArrayAdapter<Auftrag>
{

    private Context mContext;
    private int resourceLayout;

    public MainActivityShowCarsAdapter(Context context, int resource, List<Auftrag> aufträge)
    {
        super(context, resource, aufträge);

        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Auftrag a = getItem(position);
        convertView = LayoutInflater.from(mContext).inflate(resourceLayout, null);

        TextView text = convertView.findViewById(R.id.title);

        text.setText(a.getName());

        TextView datum = convertView.findViewById(R.id.duration);
        datum.setText(a.getD());


        TextView erstelltVon = convertView.findViewById(R.id.artist);
        erstelltVon.setText("erstellt von: " + a.getErstelltVon());
        return convertView;

    }

    @Override
    public Auftrag getItem(int position)
    {
        return super.getItem(position);
    }
}