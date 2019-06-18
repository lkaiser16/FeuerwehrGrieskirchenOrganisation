package com.example.feuerwehrorganisation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.feuerwehrorganisation.Eintragen.DetailActivity;
import com.example.feuerwehrorganisation.login.LoginActivity;
import com.example.feuerwehrorganisation.NewAuftrag.Auftrag;
import com.example.feuerwehrorganisation.NewAuftrag.NewAuftragActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    MenuItem refresh;
    List<Auftrag> auftragList;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    FloatingActionButton fab;
    Toolbar toolbar;
    ListView overView;

    MainActivityShowCarsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }


    public void initialize()
    {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        auftragList = new ArrayList<>();


        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);

        overView = findViewById(R.id.overView);
        adapter = new MainActivityShowCarsAdapter(this, R.layout.main_activity_overview_adapter, auftragList);
        overView.setAdapter(adapter);

    }

    public void init()
    {
        initialize();
        listeners();
    }

    public void listeners()
    {
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(MainActivity.this, NewAuftragActivity.class);
                startActivity(i);
            }
        });
        setSupportActionBar(toolbar);

        overView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {

                Intent i = new Intent(getApplicationContext(), DetailActivity.class);

                i.putExtra("Auftrag", auftragList.get(position));
                System.out.println(auftragList.get(position));
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh)
        {
            refresh();
            return true;
        }

        if (id == R.id.action_logout)
        {
            mAuth.signOut();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
//        for(int i = 1; i<4;i++)
//        {
//            writeFahrzeuge("Fahrzeug "+i);
//        }

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        Auftrag auftrag = (Auftrag) getIntent().getSerializableExtra("Auftrag");
        if (auftrag != null)
        {
            auftragList.add(auftrag);
            adapter.notifyDataSetChanged();
        }
    }

    public void writeFahrzeuge(String name)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("car", "Ada");
//        String name = "KDOF";
        Map<String, String> sitzplätze = new HashMap<>();
        sitzplätze.put("Maschinist", "");
        sitzplätze.put("Mann1", "");
        sitzplätze.put("Fahrzeugkommandant", "");
        sitzplätze.put("Mann2", "");
        sitzplätze.put("Mann3", "");
        sitzplätze.put("Mann4", "");
        sitzplätze.put("Mann5", "");


        Fahrzeug a = new Fahrzeug(name, sitzplätze);


        db.collection("Fahrzeuge").document(a.getBezeichnung()).set(a).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                System.out.println("gfunkt");
            }
        });

    }


    public void refresh()
    {
        auftragList.clear();
        db.collection("Aufträge").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Auftrag a = document.toObject(Auftrag.class);
                        auftragList.add(a);
                        Log.d("", document.getId() + " => " + document.getData());
                    }
                } else
                {
                    Log.w("", "Error getting documents.", task.getException());
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
