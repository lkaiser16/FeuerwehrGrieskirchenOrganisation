package com.example.feuerwehrorganisation.Eintragen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feuerwehrorganisation.Fahrzeug;
import com.example.feuerwehrorganisation.NewAuftrag.Auftrag;
import com.example.feuerwehrorganisation.NewAuftrag.NewAuftragActivity;
import com.example.feuerwehrorganisation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectSeatActivity extends AppCompatActivity
{
    boolean isFree = false;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Fahrzeug fahrzeug1;
    Auftrag auftrag;
    Map<String, String> seats;
    List<String> seatsKeysetForListview;
    ListView cars;
    CheckSeatAdapter adapter;

    Button speichern;

    static String selectedSeat;


    Map<String, String> globalSeats;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_seat_activity);

        init();

    }

    private void init()
    {
        initVar();
        listeners();
    }

    private void listeners()
    {
        speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
//                i.putExtra("selectedSeat", selectedSeat);
                isFree = false;
                speichern();


            }
        });
    }

    private void speichern()
    {
        proveIfFree();


    }


    private void proveIfFree()
    {

        List<Auftrag> aufträge = new ArrayList<>();
        db.collection("Aufträge").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Log.d("", document.getId() + " => " + document.getData());
                        Auftrag a = document.toObject(Auftrag.class);
                        if (a.getName().equals(auftrag.getName()))
                        {
                            List<Fahrzeug> fahrzeuge = a.getFahrzeuge();
                            int indexOFFAHRZEUG = -1;
                            for (int i = 0; i < fahrzeuge.size(); i++)
                            {
                                if (fahrzeuge.get(i).getBezeichnung().equals(fahrzeug1.getBezeichnung()))
                                {
                                    indexOFFAHRZEUG = i;
                                    Map<String, String> sitzplätze = fahrzeuge.get(indexOFFAHRZEUG).getSitzplätze();

                                    String sitz = sitzplätze.get(selectedSeat);
                                    if (sitz.equals(""))
                                    {
                                        globalSeats = sitzplätze;

                                        globalSeats.put(selectedSeat, currentUser.getEmail());
                                        isFree = true;
                                        break;
                                    } else
                                    {

                                        isFree = false;
                                        break;
                                    }

                                }
                            }

                        }
                    }
                } else
                {
                    Log.w("", "Error getting documents.", task.getException());
                }
                showIfFreeAndSave();

            }


        });
    }

    public void showIfFreeAndSave()
    {
        if (isFree)
        {
            List<Fahrzeug> fnew = auftrag.getFahrzeuge();
            for (int i = 0; i < fnew.size(); i++)
            {
                if (fnew.get(i).getBezeichnung().equals(fahrzeug1.getBezeichnung()))
                {
                    fnew.remove(i);
                }
            }
            fahrzeug1.setSitzplätze(globalSeats);
            fnew.add(fahrzeug1);
            auftrag.setFahrzeuge(fnew);
            System.out.println("frei");
            db.collection("Aufträge").document(auftrag.getName()).delete().addOnCompleteListener(new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    db.collection("Aufträge").document(auftrag.getName()).set(auftrag).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            Toast.makeText(getApplicationContext(), "gespeichert!", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    });
                }
            });


        } else
        {
            Toast.makeText(getApplicationContext(), "da war leider jemand schneller als du!", Toast.LENGTH_SHORT).show();

            System.out.println("besetzt");
        }
    }

    private void initVar()
    {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        fahrzeug1 = getCar();
        auftrag = getAuftrag();
        cars = findViewById(R.id.carsToSelect);
        seats = fahrzeug1.getSitzplätze();
        seatsKeysetForListview = setToList(seats.keySet());
        adapter = new CheckSeatAdapter(this, R.layout.auftrag_activity_cars_list_adapter, seatsKeysetForListview);

        cars.setAdapter(adapter);


        speichern = findViewById(R.id.speichern);

    }

    private List<String> setToList(Set<String> keySet)
    {
        List<String> list = new ArrayList<>();
        for (String x : keySet)
        {
            list.add(x);
        }
        return list;
    }

    private Fahrzeug getCar()
    {
        Fahrzeug f = (Fahrzeug) getIntent().getSerializableExtra("Fahrzeug");
        return f;
    }

    private Auftrag getAuftrag()
    {
        Auftrag a = (Auftrag) getIntent().getSerializableExtra("Auftrag");
        return a;
    }
}
