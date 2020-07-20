package com.example.busspassusingqr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);

        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        DatabaseReference tempTicketDb = FirebaseDatabase.getInstance().getReference().child("TICKET").child(result.getText());
        tempTicketDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sourceValue = dataSnapshot.child("source").getValue().toString();
                String destinationValue = dataSnapshot.child("destination").getValue().toString();
                String amountValue = dataSnapshot.child("paymentamount").getValue().toString();

                ScanActivity1.source.setText(sourceValue);
                ScanActivity1.destination.setText(destinationValue);
                ScanActivity1.amount.setText(amountValue);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}