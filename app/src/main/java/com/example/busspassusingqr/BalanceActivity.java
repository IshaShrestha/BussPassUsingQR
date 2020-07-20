package com.example.busspassusingqr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BalanceActivity extends AppCompatActivity {
    private TextView name, balance;
    private DatabaseReference mDatabase;
    private int currentBalance = 0;
    private Button buy100, buy500, buy1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

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

        buy100 = findViewById(R.id.btn_pay100);
        buy500 = findViewById(R.id.btn_pay500);
        buy1000 = findViewById(R.id.btn_pay1000);

        buy100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance").setValue(currentBalance + 100);
            }
        });

        buy500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance").setValue(currentBalance + 500);
            }
        });

        buy1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("balance").setValue(currentBalance + 1000);
            }
        });

    }

}


