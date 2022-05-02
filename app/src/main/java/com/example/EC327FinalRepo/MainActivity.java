package com.example.myapplication_1;

import android.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.DialogInterface;
import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.preference.PreferenceManager;
import java.util.ArrayList;
import java.util.HashSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    static ArrayList<String> notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton camera = findViewById(R.id.buttonCam);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        
        ListView listview=(ListView)findViewById(R.id.listView);
        ImageButton callButton = findViewById(R.id.buttonCal);
        ImageButton settingsButton = findViewById(R.id.buttonSet);
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Note_Editor.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemToDelete=i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure you want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               notes.remove(itemToDelete);
                               arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(("com.example.myapplication_1"), Context.MODE_PRIVATE);

                                HashSet<String> set=new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;

            }
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



