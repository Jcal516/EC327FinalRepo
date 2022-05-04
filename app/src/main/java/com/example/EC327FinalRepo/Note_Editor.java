package com.example.EC327FinalRepo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.HashSet;

public class Note_Editor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText=(EditText)findViewById(R.id.editText);

        Intent intent=getIntent();
        int noteId = intent.getIntExtra("noteId", -1);
        if (noteId !=-1){
            editText.setText(Notes_Activity.notes.get(noteId));

        }
        else{
            Notes_Activity.notes.add("");
            noteId=Notes_Activity.notes.size()-1;
            Notes_Activity.arrayAdapter.notifyDataSetChanged();
        }

        int finalNoteId = noteId;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Notes_Activity.notes.set(finalNoteId,String.valueOf(charSequence));
                Notes_Activity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(("com.example.myapplication_1"), Context.MODE_PRIVATE);

                HashSet<String> set=new HashSet<>(Notes_Activity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
}