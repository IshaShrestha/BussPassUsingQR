package com.example.busspassusingqr;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;


public class QrScanner extends AppCompatActivity {
    private Button btn_scan;
    private final int BARCODE_RECO_REQ_CODE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==BARCODE_RECO_REQ_CODE) {
            if (resultCode==RESULT_OK) {
                Bitmap photo=(Bitmap)data.getExtras().get("data");
                barcodeRecognition(photo);
            }
        }
    }

    private void barcodeRecognition(Bitmap photo) {
        FirebaseVisionImage image= FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector();
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
            @Override
            public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                for (FirebaseVisionBarcode barcode: barcodes) {
                    Rect bounds = barcode.getBoundingBox();
                    Point[] corners = barcode.getCornerPoints();

                    String rawValue = barcode.getRawValue();

                    int valueType = barcode.getValueType();
                    Toast.makeText(QrScanner.this, rawValue, Toast.LENGTH_SHORT).show();
                }


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QrScanner.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void barcodeReco(View view) {
        Intent intent=new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,BARCODE_RECO_REQ_CODE);

    }



}



