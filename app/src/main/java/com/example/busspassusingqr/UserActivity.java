package com.example.busspassusingqr;

import android.content.Intent;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class UserActivity extends AppCompatActivity {
    private TextView name, balance;
    private Spinner spinner1, spinner2;
    private int sourcePos = 0, destinationPos = 0, paymentAmount = 15, currentBalance = 0;
    private TextView payment;
    private DatabaseReference mDatabase;
    private Button buttonPay, buttonQR;
    private String source, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        name = findViewById(R.id.name);
        balance = findViewById(R.id.balance);

        mDatabase.child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameValue = dataSnapshot.child("name").getValue().toString();
                String balanceValue = dataSnapshot.child("balance").getValue().toString();
                currentBalance = Integer.valueOf(balanceValue);

                name.setText(nameValue);
                balance.setText(balanceValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, BalanceActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonPay = (Button) findViewById(R.id.btn_pay);
        buttonQR = findViewById(R.id.btn_qr);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(UserActivity.this, source + " " + destination + " " + paymentAmount, Toast.LENGTH_SHORT).show();

                if (currentBalance < paymentAmount) {
                    Toast.makeText(UserActivity.this, "Sorry, not sufficient balance", Toast.LENGTH_SHORT).show();
                } else {
                    String key = mDatabase.child("TICKET").push().getKey();
                    Map<String, Object> postValues = new HashMap<>();
                    postValues.put("source", source);
                    postValues.put("destination", destination);
                    postValues.put("paymentamount", paymentAmount);

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/TICKET/" + key, postValues);
                    childUpdates.put("/USER/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/ticketlink/", key);
                    childUpdates.put("/USER/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/balance/", currentBalance - paymentAmount);

                    mDatabase.updateChildren(childUpdates);
                }

            }
        });

        buttonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, QrcodeGenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        payment = findViewById(R.id.payment);

        final ArrayList<String> spinnerData = new ArrayList<>();
        spinnerData.add("Sundarijal");
        spinnerData.add("Gokarna");
        spinnerData.add("Jorpati");
        spinnerData.add("Boudha");
        spinnerData.add("Chabahil");
        spinnerData.add("Gaushala");
        spinnerData.add("Ratopul");
        spinnerData.add("Gyaneshwor");
        spinnerData.add("Dillibazar");
        spinnerData.add("Putalisadak");
        spinnerData.add("Ratnapark");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerData
        );

        spinner1.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinnerAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(UserActivity.this, spinnerData.get(position) + "Selected", Toast.LENGTH_SHORT).show();
                source = spinnerData.get(position);

                int paymentDistance = Math.abs(position - destinationPos);
                if (paymentDistance < 4 ) {
                    payment.setText("Pay 15");
                } else if (paymentDistance < 5) {
                    payment.setText("Pay 20");
                    paymentAmount = 20;
                } else {
                    payment.setText("Pay 25");
                    paymentAmount = 25;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(UserActivity.this, spinnerData.get(position) + "Selected", Toast.LENGTH_SHORT).show();
                destination = spinnerData.get(position);

                int paymentDistance = Math.abs(position - destinationPos);
                if (paymentDistance < 4 ) {
                    payment.setText("Pay 15");
                } else if (paymentDistance < 5) {
                    payment.setText("Pay 20");
                    paymentAmount = 20;
                } else {
                    payment.setText("Pay 25");
                    paymentAmount = 25;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}

