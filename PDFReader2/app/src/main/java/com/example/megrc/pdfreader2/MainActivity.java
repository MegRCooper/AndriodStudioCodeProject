package com.example.megrc.pdfreader2;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
        import com.github.barteksc.pdfviewer.util.FitPolicy;

public class MainActivity extends AppCompatActivity {
    /**
     * This is a PFD reader that reads the .pdf from the Assets folder, it allows for block changing
     * of pages using a swipe motion. Allows for the rendering of annotations etc.
     *
     * @param savedInstanceState calls the super class onCreate to complete the creation of activity
     */
    //View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view = this.getWindow().getDecorView();
        //view.setBackgroundResource((R.color.Pyellow));

        /** Initialising the PDF View */
        PDFView pdfViewer = findViewById(R.id.pdfViewer);
        pdfViewer.fromAsset("Bionic Hand.pdf")
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                // spacing between pages in dp. To define spacing color, set view background
                .spacing(0)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }
}
