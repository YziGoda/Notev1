package com.example.notev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notev1.databinding.ActivityDeleteNoteActiviryBinding;

import java.util.ArrayList;
import java.io.File;

public class DeleteNoteActivity extends AppCompatActivity
{
    private ActivityDeleteNoteActiviryBinding binding;
    private ArrayList<String> notesList;
    private ArrayAdapter<String> spinnerAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteNoteActiviryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        spinner = binding.spinner;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        if (intent != null)
        {
            notesList = intent.getStringArrayListExtra("notesList");

            if (notesList != null && !notesList.isEmpty())
            {
                spinnerAdapter.addAll(notesList);
                spinnerAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this, this.getString(R.string.note_missing), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void deleteNote (View view)
    {
        if(!notesList.isEmpty())
        {
            int selected = spinner.getSelectedItemPosition();
            String selectedNote = notesList.get(selected);
            String path = getFilesDir() + "/notes/" + selectedNote + ".txt";
            File deletedFile = new File(path);
            deletedFile.delete();
            Toast.makeText(this, "Note was deleted", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
            this.finish();
        }
        else
        {
            Toast.makeText(this, "Note notes", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }
    public void cancelNote (View view)
    {
        this.finish();
    }
}