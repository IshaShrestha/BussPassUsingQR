package com.example.busspassusingqr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity<mAuth> extends AppCompatActivity {
    private EditText email;
    private EditText pass;
    private Button login;
    private TextView register;
    private FirebaseAuth mAuth;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mProgress;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.et_email);
        pass = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.btn_login);
        register = (TextView) findViewById(R.id.swipeRight);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                String Email = email.getText().toString();
                String Password = pass.getText().toString();

                if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Password)) {
                    mProgress.show();
                    loginUser(Email, Password);
                } else {
                    Toast.makeText(MainActivity.this, "Failed Login: Empty Inputs are not allowed", Toast.LENGTH_SHORT).show();
                }
            }



             private void loginUser(String Email, String Password) {
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();

                            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("USER")
                                    .child(RegisteredUserID);

                            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String userType = dataSnapshot.child("type").getValue().toString();
                                    if(userType.equals("Driver")){
                                        Intent intent = new Intent(MainActivity.this, ScanActivity1.class);
                                        startActivity(intent);
                                        finish();
                                    }else if(userType.equals("Passenger")){
                                        Intent intent = new Intent(MainActivity.this, Passenger.class);
                                        startActivity(intent);
                                        finish();
                                    }
//                                    else if(userType.equals("Admin")){
//                                        Intent intentMain = new Intent(MainActivity.this, Admin.class);
//                                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intentMain);
//                                        finish();
//                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                        //return;
                                    }
                                }



                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



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
                finish();
            }
        });

    }

}