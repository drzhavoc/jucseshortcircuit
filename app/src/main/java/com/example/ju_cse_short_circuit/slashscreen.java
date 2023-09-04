package com.example.ju_cse_short_circuit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class slashscreen extends AppCompatActivity {
    private ProgressBar progressbar,progressbar1;
    private TextView textview;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slashscreen);
        progressbar=(ProgressBar) findViewById(R.id.progressbarid);
        progressbar1=(ProgressBar) findViewById(R.id.progressbarid1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();

            }
        });
        thread.start();



    }

   public void doWork()
    {
        for(progress = 20 ;progress<=100;progress=progress+20) {
        try {
             Thread.sleep(700);
             progressbar.setProgress(progress);
             progressbar1.setProgress(progress);

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }


    }
    public void startApp()
    {


        Intent intent = new Intent(slashscreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}