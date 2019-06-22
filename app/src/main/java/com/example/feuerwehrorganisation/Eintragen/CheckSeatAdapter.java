package com.example.feuerwehrorganisation.Eintragen;

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

public class CheckSeatAdapter extends ArrayAdapter<String>
{
    private Context mContext;
    private int resourceLayout;

    FirebaseFirestore db;
    List<String> fahrzeugeList;
    CheckBox checkbox;

    int howManyChecked = 0;

    public CheckSeatAdapter(Context context, int resource, List<String> fahrzeugeList)
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

        String funktion = getItem(position);
        checkbox = convertView.findViewById(R.id.checkBoxCars);

        checkbox.setText(funktion);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (howManyChecked == 0)
                {

                    howManyChecked++;
                    String[] funktionsARR = buttonView.getText().toString().split(":");
                    SelectSeatActivity.selectedSeat = funktionsARR[0];

                } else
                {
                    if (isChecked == true)
                    {
                        buttonView.setChecked(false);
                        Toast.makeText(getContext(), "Du kannst nur einen Sitzplatz auswählen!", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        howManyChecked = 0;
                    }
                }
//                f.setSelected(isChecked);
//                String name = (String) buttonView.getText();
//                String msg = "Du hast das Fahrzeug " + name + (isChecked ? " ausgewählt" : " entfernt") + ".";
//                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

//                System.out.println(f);
            }
        });

        return convertView;
    }

    @Override
    public String getItem(int position)
    {
        return super.getItem(position);
    }


}
