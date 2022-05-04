package com.example.EC327FinalRepo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class Notes_Activity extends AppCompatActivity {
static ArrayList<String> notes=new ArrayList<>();
static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        ListView listview=(ListView)findViewById(R.id.listView);

        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(("com.example.myapplication_1"),Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (set==null) {
            notes.add("Please provide the date, indicate type of injury, and pain rating from 1-10");

        }else {

            notes = new ArrayList<>(set);
        }




        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        listview.setAdapter(arrayAdapter);

        notes.add("Please provide the date, indicate type of injury, and pain rating from 1-10");

        listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(),Note_Editor.class);
            intent.putExtra("noteId",i);
            startActivity(intent);
        });
        listview.setOnItemLongClickListener((adapterView, view, i, l) -> {
            final int itemToDelete=i;

            new AlertDialog.Builder(Notes_Activity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure you want to delete")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> {
                       notes.remove(itemToDelete);
                       arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences1 =getApplicationContext().getSharedPreferences(("com.example.myapplication_1"), Context.MODE_PRIVATE);

                        HashSet<String> set1 =new HashSet<>(Notes_Activity.notes);
                        sharedPreferences1.edit().putStringSet("notes", set1).apply();

                    })
                    .setNegativeButton("No",null)
                    .show();
            return true;

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_item,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.addNote){
            Intent intent=new Intent(getApplicationContext(),Note_Editor.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}



