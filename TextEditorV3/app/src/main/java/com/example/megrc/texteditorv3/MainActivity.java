package com.example.megrc.texteditorv3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Initialises the Variable in use.
    EditText titleEditText, descriptionEditText;
    Button saveBtn;
    Button openBtn;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = this.getWindow().getDecorView();
        view.setBackgroundResource((R.color.Pyellow));

        /*Because I want to access files and save files from external storage such as SD storage I
        require permissions from the device which is what the following allows for.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);

        }

        // Find the Vars by id as set in the activity_main.xml
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveBtn = findViewById(R.id.saveBtn);
        openBtn = findViewById(R.id.openBtn);

        /* Sets on on click listener which is looking to see if the btn is pressed. Automatically,
        saves file as the title at the top in the title band. The 'if (!filename.equals("") &&
        !content.equals("")) {' is only used if both values are null.
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = titleEditText.getText().toString();
                String content = descriptionEditText.getText().toString();
                if (!filename.equals("") && !content.equals("")) {
                    try {
                        saveTextAsFile(filename, content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private String readText (String input){
        File file = new File(input);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();

        }
        return text.toString();
    }

    private void saveTextAsFile(String filename, String content) throws IOException {
        String fileName = filename + ".txt";
        // Creates a file;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        // Write to File
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}