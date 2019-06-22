package com.example.feuerwehrorganisation.NewAuftrag;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.feuerwehrorganisation.Fahrzeug;
import com.example.feuerwehrorganisation.Logic;
import com.example.feuerwehrorganisation.MainActivity;
import com.example.feuerwehrorganisation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewAuftragActivity extends AppCompatActivity
{
    int THECOUNTER;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    EditText name;
    Button speichern;
    EditText datum;
    EditText uhrzeitEdittext;


    List<Fahrzeug> fahrzeugeList;
    List<Fahrzeug> selectedFahrzeuge;
    List<Auftrag> listAufträge;

    ListView cars;
    ChecklistAdapter adapter;
    final Calendar myCalendar = Calendar.getInstance();
    Auftrag derAuftrag = null;
    Logic logic = new Logic();


    FirebaseUser currenUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auftrag_activity);
        init();

    }

    public void init()
    {
        initializeVar();
        listeners();
        showFahrzeuge();
    }

    public void initializeVar()
    {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        cars = findViewById(R.id.cars);


        fahrzeugeList = new ArrayList<>();
        listAufträge = new ArrayList<>();
        selectedFahrzeuge = new ArrayList<>();
        adapter = new ChecklistAdapter(this, R.layout.auftrag_activity_cars_list_adapter, fahrzeugeList);
        cars.setAdapter(adapter);

        datum = findViewById(R.id.datum);
        name = findViewById(R.id.text);
        uhrzeitEdittext = findViewById(R.id.uhrzeit);

        speichern = findViewById(R.id.speichern);


        currenUser = mAuth.getCurrentUser();
    }

    public void listeners()
    {
        speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                speichern();
                Intent i = new Intent(NewAuftragActivity.this, MainActivity.class);
                i.putExtra("Auftrag", derAuftrag);
                finish();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                // TODO Fahrzeug-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        datum.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    new DatePickerDialog(NewAuftragActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });


    }

    public void readFahrzeuge()
    {

        db.collection("Fahrzeuge").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {

                        Fahrzeug f = document.toObject(Fahrzeug.class);
                        fahrzeugeList.add(f);
                        Log.d("", document.getId() + " => " + document.getData());
                    }
//                    addCheckboxCars(fahrzeugeList);

                } else
                {
                    Log.w("", "Error getting documents.", task.getException());
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

//    @Override
//    public void onStop()
//    {
//        Intent i = new Intent(NewAuftragActivity.this, MainActivity.class);
//        i.putExtra("Auftrag", (Serializable) derAuftrag);
////do your stuff here
//        super.onStop();
//
//    }

    private void updateLabel()
    {
        String myFormat = "dd.MM.YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        datum.setText(sdf.format(myCalendar.getTime()));
    }

    public void addCheckboxCars(final List<Fahrzeug> fahrzeuge)
    {
        LinearLayout linearLayout = null;
//         linearLayout = findViewById(R.id.checkBoxAutos);

        // Create Checkbox Dynamically
        for (THECOUNTER = 0; THECOUNTER < fahrzeuge.size(); THECOUNTER++)
        {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(fahrzeuge.get(THECOUNTER).getBezeichnung());
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    String name = (String) buttonView.getText();
                    String msg = "Du hast das Fahrzeug " + name + (isChecked ? " ausgewählt" : " entfernt") + ".";
                    if (isChecked)
                    {
                        Fahrzeug newFahrzeug = null;
                        for (int i = 0; i < fahrzeuge.size(); i++)
                        {
                            if (buttonView.getText().equals(fahrzeuge.get(i).getBezeichnung()))
                            {
                                newFahrzeug = fahrzeuge.get(i);
                                break;
                            }
                        }

                        selectedFahrzeuge.add(newFahrzeug);
                    } else
                    {
                        Fahrzeug newFahrzeug = null;
                        for (int i = 0; i < fahrzeuge.size(); i++)
                        {
                            if (buttonView.getText().equals(fahrzeuge.get(i).getBezeichnung()))
                            {
                                newFahrzeug = fahrzeuge.get(i);
                                break;
                            }
                        }
                        selectedFahrzeuge.remove(newFahrzeug);
                    }
                    Toast.makeText(NewAuftragActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });

            // Add Checkbox to LinearLayout
            if (linearLayout != null)
            {
                linearLayout.addView(checkBox);
            }
        }
        THECOUNTER = 0;

    }

    public void showFahrzeuge()
    {
        readFahrzeuge();


    }

    public void speichern()
    {
        String text = name.getText().toString();
        String date = datum.getText().toString();
        String uhrzeit = uhrzeitEdittext.getText().toString();
        proveSelectedCars();

        derAuftrag = new Auftrag(text, selectedFahrzeuge, date, uhrzeit, currenUser.getDisplayName());
        writeAufragOnDB(derAuftrag);

    }

    public void writeAufragOnDB(Auftrag a)
    {

        db.collection("Aufträge").document(a.getName()).set(a).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(NewAuftragActivity.this, "gespeichert", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void proveSelectedCars()
    {
        for (int i = 0; i < fahrzeugeList.size(); i++)
        {
            if (fahrzeugeList.get(i).isSelected())
            {
                selectedFahrzeuge.add(fahrzeugeList.get(i));
            }
        }
    }


}
