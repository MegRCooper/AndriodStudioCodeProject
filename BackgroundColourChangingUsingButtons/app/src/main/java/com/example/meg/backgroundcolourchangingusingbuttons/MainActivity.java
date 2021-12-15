package com.example.meg.backgroundcolourchangingusingbuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    View view;
    /** A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
View is the base class for widgets, which are used to create interactive user interface (UI) components
(buttons, text fields, etc.). */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Pre loaded code.
            super.onCreate(savedInstanceState); // Pre loaded code.
            setContentView(R.layout.activity_main); // Pre loaded code.

            view = this.getWindow().getDecorView();
            view.setBackgroundResource((R.color.Pyellow)); /*This line of code sets the background
            colour of my app to a previously defined off white called Pyellow rather than white.*/
    }
    /** The methods below just sets up the colour options that the users can chose from. The colours
     *  are predefined in the colours.xml page
     */
        public void goBlue(View v){
            view.setBackgroundResource(R.color.Pblue); 
        }

        public void goRed(View v){
            view.setBackgroundResource(R.color.Pred);
        }

        public void goGreen(View v){
            view.setBackgroundResource(R.color.Pgreen);
        }

        public void goPink(View v){
            view.setBackgroundResource(R.color.Ppink);
        }

        public void goDefault(View v){
        view.setBackgroundResource(R.color.Pyellow);
    }
}