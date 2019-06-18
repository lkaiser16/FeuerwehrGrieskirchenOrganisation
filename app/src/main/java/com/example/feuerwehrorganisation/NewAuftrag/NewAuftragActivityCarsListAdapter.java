package com.example.feuerwehrorganisation.NewAuftrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.feuerwehrorganisation.Fahrzeug;
import com.example.feuerwehrorganisation.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NewAuftragActivityCarsListAdapter extends ArrayAdapter<Fahrzeug>
{
    private Context mContext;
    private int resourceLayout;

    FirebaseFirestore db;
    List<Fahrzeug> fahrzeugeList;
    CheckBox checkbox;

    public NewAuftragActivityCarsListAdapter(Context context, int resource, List<Fahrzeug> fahrzeugeList)
    {
        super(context, resource, fahrzeugeList);

        this.mContext = context;
        this.mContext = context;

        this.fahrzeugeList = fahrzeugeList;
        this.resourceLayout = resource;
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(mContext).inflate(resourceLayout, null);

        final Fahrzeug f = getItem(position);
        checkbox = convertView.findViewById(R.id.checkBoxCars);

        checkbox.setText(f.getBezeichnung());
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                f.setSelected(isChecked);
                String name = (String) buttonView.getText();
                String msg = "Du hast das Fahrzeug " + name + (isChecked ? " ausgewählt" : " entfernt") + ".";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

//                System.out.println(f);
            }
        });

        return convertView;
    }

    @Override
    public Fahrzeug getItem(int position)
    {
        return super.getItem(position);
    }


}
