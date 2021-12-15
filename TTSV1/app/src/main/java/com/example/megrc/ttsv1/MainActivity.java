package com.example.megrc.ttsv1;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


/**
 * The program is a text to speech program that will take user inout and then
 * read it out loud if the button is pressed.
 * */
public class MainActivity extends AppCompatActivity {
    /**
     * The next four lines initialises the variables used throughout.
     */
    TextToSpeech tts;
    EditText write;
    Button readAloudBtn;
    Button stopBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Links the text editior to the variable to the .xml file
         * Links the button for reading out load to the .xml file
         * Link the stop button to to stop button in the .xml file
         */
        write = findViewById(R.id.editText);
        readAloudBtn = findViewById(R.id.readAloud);
        stopBtn = findViewById(R.id.stopBtn);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            /**
             * Sets the default language to English (UK)
             * @param status is an integer parameter used to check the there is no errors with the
             * text to speech then sets language
             */
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
        readAloudBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets an on click listener so that when pressed the text in the text editor is spoken
             * out loud.
             * @param v View: The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String toSpeak = write.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             *  Sets on click listener for the stop button
             *  If pressed than the program stops
             */
            public void onClick(View v) {
                {
                    // Don't forget to shutdown tts!
                    if (tts != null) {
                        tts.stop();
                    }
                    MainActivity.super.onDestroy();
                }
            }
        });
    }
}
