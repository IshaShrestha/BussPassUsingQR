package com.example.busspassusingqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText name;
    private EditText repass;
    private EditText email;
    private EditText pass;
    private Spinner spinner;
    private Button register;
    private TextView login;
    private RadioGroup radioGroup;
    private RadioButton mOption;
    private static final String TAG = "YOUR-TAG-NAME";
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private String type = "";
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //spinner = (Spinner) findViewById(R.id.spinner1);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        repass = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.btn_register);
        login =(TextView) findViewById(R.id.swipeLeft);
        radioGroup = (RadioGroup)findViewById(R.id.radioButton);
        // RadioButton uType =(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        final user users = new user();
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            private FirebaseAuth.AuthStateListener firebaseAuthListener;

            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                mOption=(RadioButton)findViewById(selectedId);
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
                if(!password.equals(repassword)){
                    repass.setError("Password Not matching");
                    return;
                }




                firebaseAuth.createUserWithEmailAndPassword(Email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mProgress.show();
                                    sendVerificationEmail();
                                    users.setEmail(Email);
                                    users.setName(fullname);
                                    users.setType(mOption.getText().toString().trim());
                                    FirebaseDatabase.getInstance().getReference("USER")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            //Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();

                                        }


                                    });
                                }


                            }

                            private void sendVerificationEmail() {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user != null) {
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                        finish();
                                                    }
                                                }
                                            });
                                }
                            }

                        });
                //firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                //  private FirebaseUser firebaseUser;

                //  @Override
//                                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                                                    firebaseUser = firebaseAuth.getCurrentUser();
//                                                    if (firebaseUser != null ) {
//                                                        Log.e(TAG, firebaseUser.isEmailVerified() ? "User is signed in and email is verified" : "Email is not verified");
//                                                    } else {
//                                                        Log.e(TAG, "onAuthStateChanged:signed_out");
//                                                    }
//                                                }
                // };


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
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

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
}
