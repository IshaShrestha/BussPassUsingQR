package com.example.busspassusingqr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Spinner spinner1, spinner2, spinner3;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        final String division[] = {"Lagankhel", "kumaripati", "Jawlakhel", "Pulchowk"};

        final String Lagankhel[] = {"Kumaripati", "Jawlakhel", "Pulchowk"};
        final String Kumaripati[] = {"Jawlakhel", "pulchowk"};
        final String Jawlakhel[] = {"Pulchowk"};
        final String i[]={"15"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, division);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelect = division[position];
                //              Toast.makeText(UserActivity.this, "Select item:"+itemSelect, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, Lagankhel);
                    spinner2.setAdapter(adapter1);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemselect2= Lagankhel[position];
                            if(position==0) {
                                ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter4);
                            }
                            if(position==1){

                                ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter5);

                            }
                            if(position==2){
                                ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter6);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
                if (position == 1) {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, Kumaripati);
                    spinner2.setAdapter(adapter2);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemselect3= Kumaripati[position];
                            if(position==0) {
                                ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter7);
                            }
                            if(position==1){

                                ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter8);

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });



                }
                if (position == 2) {
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, Jawlakhel);
                    spinner2.setAdapter(adapter3);

                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String itemselect2= Jawlakhel[position];
                            if(position==0) {
                                ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(UserActivity.this, android.R.layout.simple_spinner_dropdown_item, i);
                                spinner3.setAdapter(adapter9);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

}

