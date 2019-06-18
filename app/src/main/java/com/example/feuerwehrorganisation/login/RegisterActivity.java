package com.example.feuerwehrorganisation.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feuerwehrorganisation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    EditText mEmail;
    EditText mPassword;
    EditText name;

    Button registerButton;
    String email;
    String password;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    EditText dienstgrad;
    String dienstgradString;
    String nameString;

    int counterClicked = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.registerEmail);
        mPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButtonACT);
        dienstgrad = findViewById(R.id.registerDienstgrad);
        name = findViewById(R.id.registerName);
        db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (counterClicked == 0)
                {
                    counterClicked++;
                    email = mEmail.getText().toString();
                    password = mPassword.getText().toString();
                    dienstgradString = dienstgrad.getText().toString();
                    nameString = name.getText().toString();
                    if (email.equals("") || password.equals("") || dienstgradString.equals("") || nameString.equals(""))
                    {
                        counterClicked = 0;
                        Toast.makeText(RegisterActivity.this, "Überprüfen Sie Ihre Eingabe!", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {

                                    currentUser = mAuth.getCurrentUser();
//                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                startActivity(intent);
                                    finish();
                                    writeDB();
                                    mEmail.setText("");
                                    mPassword.setText("");
                                    dienstgrad.setText("");
                                    name.setText("");

                                } else
                                {
                                    // If sign in fails, display a message to the user.
                                    //                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    counterClicked = 0;
                                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    Log.w("", "createUserWithEmail:failure", task.getException());


                                }

                                // ...
                            }
                        });
                    }
                }
            }
        });

    }

    public void writeDB()
    {
        String newUser = email;
        String newDienstgrad = dienstgradString;
        String newPasswort = password;
        String newName = nameString;

        Map<String, Object> newValues = new HashMap<>();
        newValues.put("email", newUser);
        newValues.put("dienstgrad", newDienstgrad);
        newValues.put("passwort", newPasswort);
        newValues.put("name", newName);
        db.collection("FeuerwehrMänner").document(newUser).set(newValues).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

            }
        });
    }
}
