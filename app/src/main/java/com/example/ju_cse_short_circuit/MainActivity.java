package com.example.ju_cse_short_circuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private CardView CV1,CV2,CV3,CV4,CV5,CV6;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CV1=(CardView) findViewById(R.id.cv1);
        CV2=(CardView) findViewById(R.id.cv2);
        CV3=(CardView) findViewById(R.id.cv3);
        CV4=(CardView) findViewById(R.id.cv4);
        CV5=(CardView) findViewById(R.id.cv5);
        CV6=(CardView) findViewById(R.id.cv6);

        CV1.setOnClickListener(this);
        CV2.setOnClickListener(this);
        CV3.setOnClickListener(this);
        CV4.setOnClickListener(this);
        CV5.setOnClickListener(this);
        CV6.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
    if(view.getId()==R.id.cv1)
    {
     Intent intent= new Intent(MainActivity.this, officials.class);
     startActivity(intent);

    }


    else if(view.getId()==R.id.cv2)
    {
            Intent intent= new Intent(MainActivity.this, gallery.class);
            startActivity(intent);


    }

    else if(view.getId()==R.id.cv4)
    {
        Intent intent= new Intent(MainActivity.this, task_activity.class);
        startActivity(intent);


    }

    }
}