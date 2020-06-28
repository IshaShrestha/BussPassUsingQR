package com.example.busspassusingqr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText name;
    private EditText repass;
    private EditText email;
    private EditText pass;
   // private Spinner mySpinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.password);
        EditText repass = (EditText) findViewById(R.id.repassword);


        spinner.setOnItemSelectedListener(this);//spinner click listener
        List<String> type = new ArrayList<String>();
        type.add("passenger");
        type.add("driver");

        //creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
