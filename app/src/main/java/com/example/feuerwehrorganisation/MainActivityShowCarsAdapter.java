package com.example.feuerwehrorganisation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.feuerwehrorganisation.NewAuftrag.Auftrag;

import java.util.List;

public class MainActivityShowCarsAdapter extends ArrayAdapter<Auftrag>
{


    public MainActivityShowCarsAdapter(Context context, int resource, List<Auftrag> aufträge)
    {
        super(context, resource, aufträge);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Auftrag getItem(int position)
    {
        return super.getItem(position);
    }
}