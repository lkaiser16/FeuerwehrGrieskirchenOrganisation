package com.example.feuerwehrorganisation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener
{
    MenuItem refresh;
    List<Auftrag> auftragList;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseFirestore db;
    FloatingActionButton fab;
    Toolbar toolbar;
    RecyclerView overView;

    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
    public void initialize()
    {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        auftragList = new ArrayList<>();


        overView = findViewById(R.id.overView);

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        overView = findViewById(R.id.overView);

        overView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, auftragList);
        adapter.setClickListener(this);
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
            //refresh
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

            auftragList.add(auftrag);
            adapter.notifyDataSetChanged();

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


    public void fillList()
    {

    }
}
