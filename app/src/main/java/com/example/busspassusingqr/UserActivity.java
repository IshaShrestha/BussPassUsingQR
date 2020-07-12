package com.example.busspassusingqr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private Spinner spinner1, spinner2, spinner3;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Information");
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);

        Query query = mDatabase.orderByChild("source");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String titlename = dataSnapshot1.child("source").getValue(String.class);
                    titleList.add(titlename);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        Query query1 = mDatabase.orderByChild("Destination");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> titleList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String titlename1 = dataSnapshot1.child("Destination").getValue(String.class);
                    titleList.add(titlename1);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_item, titleList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });




    }

}

