package com.example.busspassusingqr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Original extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Object FirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = (FirebaseAuth.AuthStateListener) (FirebaseAuth);
                {
                    if (FirebaseAuth.getCurrentUser()== null) {
                        finish();
                        Intent mainact= new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainact);
                    }
                };
    }
}
