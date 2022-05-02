package com.example.EC327FinalRepo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedicActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic);

        Button basicButton = findViewById(R.id.Basic);
        Button emergencyButton = findViewById(R.id.emergency);
        Button resourcesButton = findViewById(R.id.resources);

        resourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MedicActivity.this, MedicResources.class);
                startActivity(i);
            }
        });
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MedicActivity.this, MedicEmergencyActivity.class);
                startActivity(i);
            }
        });

        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MedicActivity.this, MedicBasicActivity.class);
                startActivity(i);
            }
        });
    }
}