package com.example.busspassusingqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;

public class QrcodeGenActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonBack;
    private TextView source, destination, amount;
    private DatabaseReference mUserDB;

    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);

        buttonBack = findViewById(R.id.btn_back);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        amount = findViewById(R.id.amount);

        imageView = (ImageView)findViewById(R.id.imageView);

        mUserDB = FirebaseDatabase.getInstance().getReference().child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ticket = dataSnapshot.child("ticketlink").getValue().toString();

                try {
                    bitmap = TextToImageEncode(ticket);

                    DatabaseReference tempTicketDb = FirebaseDatabase.getInstance().getReference().child("TICKET").child(ticket);
                    tempTicketDb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String sourceValue = dataSnapshot.child("source").getValue().toString();
                            String destinationValue = dataSnapshot.child("destination").getValue().toString();
                            String amountValue = dataSnapshot.child("paymentamount").getValue().toString();

                            source.setText(sourceValue);
                            destination.setText(destinationValue);
                            amount.setText(amountValue);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    imageView.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QrcodeGenActivity.this, UserActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        btn_gen = (Button)findViewById(R.id.btn_gen);

//        btn_gen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //EditTextValue = editText.getText().toString();
//                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//                String RegisteredUserID = currentUser.getUid();
//
//                firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("USER")
//                        .child(RegisteredUserID);
//                firebaseDatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        try {
//                            String email = dataSnapshot.child("email").getValue().toString();
//                            bitmap = TextToImageEncode(email);
//
//                            imageView.setImageBitmap(bitmap);
//
//                        } catch (WriterException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//
//            }
//        });
    }


    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}