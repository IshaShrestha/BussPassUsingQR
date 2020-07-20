package com.example.busspassusingqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScanActivity1 extends AppCompatActivity {
    public static TextView scan_logo, source, destination, amount;
    Button btn_scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan1);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        amount = findViewById(R.id.amount);

        btn_scanner = (Button) findViewById(R.id.btn_scanner);

        btn_scanner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanActivity1.this, ScanActivity2.class));
            }
        });
    }
}