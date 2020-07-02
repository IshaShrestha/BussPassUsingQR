package com.example.busspassusingqr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private EditText name;
    private EditText repass;
    private EditText email;
    private EditText pass;
    private Spinner spinner;
    private Button register;
    private TextView login;
    private static final String TAG = "YOUR-TAG-NAME";



    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = (Spinner) findViewById(R.id.spinner1);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.btn_register);
        login =(TextView) findViewById(R.id.swipeLeft);


        final List<String> type = new ArrayList<String>();
        type.add(0, "Select Usertype");
        type.add(1, "passenger");
        type.add(2, "driver");

        //creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            final String type = spinner.getSelectedItem().toString();
                                            final String fullname = name.getText().toString().trim();
                                            final String Email = email.getText().toString().trim();
                                            String password = pass.getText().toString().trim();
                                            String repassword = repass.getText().toString().trim();


                                            if (TextUtils.isEmpty(fullname)) {
                                                name.setError("name is required");
                                                return;
                                            }
                                            if (TextUtils.isEmpty(Email)) {
                                                email.setError("email is required");
                                                return;
                                            }
                                            if (password.length() < 6) {
                                                pass.setError("password must be greater than 6 character");
                                            }
                                            if (TextUtils.isEmpty(password)) {
                                                Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                                            }
                                            if (TextUtils.isEmpty(repassword)) {
                                                Toast.makeText(Register.this, "please Enter Repassword", Toast.LENGTH_SHORT).show();
                                            }


                                            firebaseAuth.createUserWithEmailAndPassword(Email, password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                user info = new user(
                                                                        name,
                                                                        email
                                                                );
                                                                FirebaseDatabase.getInstance().getReference("users")
                                                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                                    }


                                                                });
                                                            }


                                                        }
                                                    });


                                        }
                                    });
            login.setOnClickListener(new View.OnClickListener() {
                     @Override
                    public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
            }
        });
//        final FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (!user.isEmailVerified()) {
//            user.sendEmailVerification()
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getApplicationContext(),
//                                        "Verification email sent to " + user.getEmail(),
//                                        Toast.LENGTH_SHORT).show();
//                                Log.d("Verification", "Verification email sent to " + user.getEmail());
//                            } else {
//                                Log.e(TAG, "sendEmailVerification", task.getException());
//                                Toast.makeText(getApplicationContext(),
//                                        "Failed to send verification email.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//
//    }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}





