package com.example.notev1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notev1.databinding.ActivityAddNoteBinding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity
{
    private ActivityAddNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void writeToFile(String name, String content) throws IOException
    {
        String path = this.getFilesDir().getAbsolutePath();
        String notes = "/data/user/0/com.example.notev1/files/notes";
        Path folderPath = Paths.get(notes);
        if (!Files.exists(folderPath))
        {

            Files.createDirectory(folderPath);
//            Toast.makeText(this,"Directory created",Toast.LENGTH_SHORT).show();
        }
//        else
//        {
//            Toast.makeText(this,"Directory already exists", Toast.LENGTH_SHORT).show();
//        }
//        Log.d("PATH", "path: " + path); // path: /data/user/0/com.example.notev1/files
//        File file = new File(path + name +".txt");
        File file = new File(String.format("%s/notes/%s.txt",path,name));
        FileWriter fileWriter = new FileWriter(file);

        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.format("%s %s", name, content));
            bufferedWriter.close();
        }
        finally
        {
            fileWriter.close();
        }
    }

    public void saveNote(View view) throws IOException
    {
        String name = binding.etName.getText().toString();
        String content = binding.etContent.getText().toString();
        if (binding.etName.getText().length()!=0)
        {
            if (binding.etContent.getText().length()!=0)
            {
                try {
                    writeToFile(name, content);
                    Toast.makeText(this, this.getString(R.string.note_created), Toast.LENGTH_LONG).show();
                }
                catch (IOException ex)
                {
                    System.out.println(ex.toString());
                }
            }
            else
            {
                Toast.makeText(this,this.getString(R.string.missing_text), Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this,this.getString(R.string.invalid_name),Toast.LENGTH_LONG).show();
        }
        setResult(Activity.RESULT_OK);
        this.finish();
    }

    public void cancelNote(View view)
    {
        this.finish();
    }
}