package com.example.notev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.notev1.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_NOTE_REQUEST_CODE = 1;
    private static final int DELETE_NOTE_REQUEST_CODE = 2;
    public ArrayList<String> listNotes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotes);
        binding.listView.setAdapter(adapter);

        loadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();
        if (id == R.id.add_note)
        {
            startActivityForResult(new Intent(this, AddNoteActivity.class), ADD_NOTE_REQUEST_CODE);
            return true;
        }
        if (id == R.id.delete_note)
        {
            Intent intent = new Intent(MainActivity.this, DeleteNoteActivity.class);
            intent.putStringArrayListExtra("notesList", listNotes); //TODO perduodam sarasa
            startActivityForResult(intent, DELETE_NOTE_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNotes()
    {
        listNotes.clear();
        String path = getFilesDir() + "/notes";
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory())
        {
            File[] files = dir.listFiles((directory, name) -> name.toLowerCase().endsWith(".txt"));
            if (files != null)
            {
                for (File file : files)
                {
                    String fileName = file.getName().replaceFirst("[.][^.]+$", "");
                    listNotes.add(fileName);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int request, int result, Intent data)
    {
        super.onActivityResult(request, result, data);
        if (request == ADD_NOTE_REQUEST_CODE && result == AddNoteActivity.RESULT_OK)
        {
            loadNotes();
        }
        else if (request == DELETE_NOTE_REQUEST_CODE && result == DeleteNoteActivity.RESULT_OK)
        {
            loadNotes();
        }
    }
}