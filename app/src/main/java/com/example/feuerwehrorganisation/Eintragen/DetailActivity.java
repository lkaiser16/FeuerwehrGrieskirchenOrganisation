package com.example.feuerwehrorganisation.Eintragen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.feuerwehrorganisation.Fahrzeug;
import com.example.feuerwehrorganisation.NewAuftrag.Auftrag;
import com.example.feuerwehrorganisation.NewAuftrag.ChecklistAdapter;
import com.example.feuerwehrorganisation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity
{
    FloatingActionButton fabspeichern;
    TextView zeit;
    TextView name;
    ListView autos;

    List<Fahrzeug> fahrzeugList;
    String auftragName;
    String datumUUhrzeit;

    ShowCarsAdapter adapter;
    Auftrag a;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        init();
        setAuftrag();

    }

    private void setAuftrag()
    {
        a = getAuftrag();
        fahrzeugList.clear();
        List<Fahrzeug> agetFahrzeuge = a.getFahrzeuge();
        fahrzeugList = agetFahrzeuge;
        auftragName = a.getName();
        datumUUhrzeit = "am " + a.getD() + ", " + a.getUhrzeit();

        zeit.setText(datumUUhrzeit);
        name.setText(auftragName);
        adapter = new ShowCarsAdapter(getApplicationContext(), R.layout.detail_activity_list_adapter, fahrzeugList);
        autos.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }

    private Auftrag getAuftrag()
    {
        return (Auftrag) getIntent().getSerializableExtra("Auftrag");
    }


    private void init()
    {
        initVar();
        listeners();

    }

    private void initVar()
    {
        autos = findViewById(R.id.autosZumEintragen);
        name = findViewById(R.id.auftragName);
        zeit = findViewById(R.id.datumUuhrzeit);
        fabspeichern = findViewById(R.id.fabSpeichern);

        fahrzeugList = new ArrayList<>();
        Map<String, String> t = new HashMap<>();
        fahrzeugList.add(new Fahrzeug("testAUTO!", t));


    }

    private void listeners()
    {
        fabspeichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                speichern();
                finish();
            }
        });
        autos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = new Intent(getApplicationContext(), SelectSeatActivity.class);
                Fahrzeug f = fahrzeugList.get(position);
                i.putExtra("Fahrzeug", f);
                i.putExtra("Auftrag", a);
                startActivity(i);
            }
        });
    }

    private void speichern()
    {
        checkDatabase();
        writeOnDatabase();
    }

    private void checkDatabase()
    {
    }

    private void writeOnDatabase()
    {
    }


}
