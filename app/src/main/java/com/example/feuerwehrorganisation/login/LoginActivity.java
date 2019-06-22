package com.example.feuerwehrorganisation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity
{
    FirebaseUser currenUser;
    Button registrieren;
    private FirebaseAuth mAuth;
    EditText mEmail;
    EditText mPassword;
    Button loginButton;
    Button noUserYet;

    String email;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        registrieren = findViewById(R.id.registerButton);

        registrieren.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.loginEmail);
        mPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
//        noUserYet = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                if (email.equals("") || password.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Überprüfen Sie die Eingabe!", Toast.LENGTH_SHORT).show();
                } else
                {
//
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "signInWithEmail:success");
                                currenUser = mAuth.getCurrentUser();
                                mEmail.setText("");
                                mPassword.setText("");
                                Toast.makeText(LoginActivity.this, "Wilkommen, " + currenUser.getDisplayName() + "!", Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                        startActivity(i);
                                finish();


                                // updateUI(user);
                            } else
                            {
                                // If sign in fails, display a message to the user.
                                //  Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Fehler bei der Anmeldung! Überprüfen Sie Ihre Eingabe!", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
                }
            }
        });
//
    }
}
