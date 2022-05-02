package com.example.EC327FinalRepo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MedicBasicActivity extends AppCompatActivity {

    String s1[], s2[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medic_basic);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.BasicMedicList);
        s2 = getResources().getStringArray(R.array.BasicDescriptionList);

        MyAdapter myAdapter = new MyAdapter(this, s1,s2);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
    }
}