package com.example.busspassusingqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity<mAuth> extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button login;
    private TextView register;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText) findViewById(R.id.et_email);
        pass=(EditText) findViewById(R.id.et_password);
        login=(Button) findViewById(R.id.btn_login);
        register=(TextView) findViewById(R.id.swipeRight);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth=  FirebaseAuth.getInstance();
                String Email=email.getText().toString();
                String Password=pass.getText().toString();

                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"invalid email/password",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,"login successful",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), home.class));
                        }
                    }
                });
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }
}