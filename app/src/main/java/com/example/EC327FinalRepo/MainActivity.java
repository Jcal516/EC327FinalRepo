package com.example.EC327FinalRepo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    //in case everything here breaks
    //static ArrayAdapter arrayAdapter;
    //
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton camera = findViewById(R.id.buttonCam);
        camera.setOnClickListener(view -> {
            try{
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

        ImageButton medicButton = findViewById(R.id.buttonAid);
        ImageButton callButton = findViewById(R.id.buttonCal);
        ImageButton settingsButton = findViewById(R.id.buttonSet);

        medicButton.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, MedicActivity.class);
            startActivity(i);
        });

        callButton.setOnClickListener(view -> makePhoneCall());
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    private void makePhoneCall() {


        String number;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        number = sharedPreferences.getString("iam3", "555");
        System.out.println("3");
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("4");
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else {
            System.out.println("5");
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            System.out.println("6");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
            else {
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
