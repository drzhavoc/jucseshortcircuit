package com.example.ju_cse_short_circuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class officials extends AppCompatActivity implements View.OnClickListener{
    private CardView CV11,CV12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        CV11=(CardView) findViewById(R.id.cv11);
        CV12=(CardView) findViewById(R.id.cv12);
        CV11.setOnClickListener(this);
        CV12.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.cv11)
        {
            Intent intent = new Intent(officials.this, teacherprofile.class);
            startActivity(intent);

        }
       else if(view.getId()==R.id.cv11)
        {
            Intent intent = new Intent(officials.this, stuffprofile.class);
            startActivity(intent);

        }

    }
}