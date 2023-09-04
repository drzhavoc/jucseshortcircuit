package com.example.ju_cse_short_circuit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class teacherprofile extends AppCompatActivity implements AdapterView.OnItemClickListener

{
    private ListView lv;
    public Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherprofile);
        String[] name = {"Dr.Jugal Krisno Das", "Dr. Emdadul Islam", "Dr. Zahidur Rahman", "Dr Liton Jude Rozario", "Md Mustafizur Rahman", "Dr.Ezharul Islam", "Md Musfiqur Rahman","Md Humaun Kabir", "Sovon Ahmed", "Rafsan Jani", "Bulbul Ahmed", "Onup Majumder", "Dr. Amena Akter", "Dr. Moriom Akter","tori","ritu","Ammajan","Apu"};

        lv = (ListView) findViewById(R.id.lv1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,name);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 0) {

            Fragment fragment;
            fragment = new techaer1("teacher", 0);
            FragmentManager fragmentmanager = getSupportFragmentManager();

            FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
            fragmenttransaction.replace(R.id.fid1,fragment);
            fragmenttransaction.commit();


        }
       else if (i == 1) {

            Fragment fragment;
            fragment = new techaer1("teacher", 1);
            FragmentManager fragmentmanager = getSupportFragmentManager();

            FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
            fragmenttransaction.replace(R.id.fid1, fragment);
            fragmenttransaction.commit();


        }

       else if (i == 2) {

            Fragment fragment;
            fragment = new techaer1("teacher", 2);
            FragmentManager fragmentmanager = getSupportFragmentManager();

            FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
            fragmenttransaction.replace(R.id.fid1,fragment);
            fragmenttransaction.commit();


        }
       else if (i == 3) {

            Fragment fragment;
            fragment = new techaer1("teacher",3);
            FragmentManager fragmentmanager = getSupportFragmentManager();

            FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
            fragmenttransaction.replace(R.id.fid1,fragment);
            fragmenttransaction.commit();


        }
        else {

            Fragment fragment;
            fragment = new techaer1("teacher",100);
            FragmentManager fragmentmanager = getSupportFragmentManager();

            FragmentTransaction fragmenttransaction = fragmentmanager.beginTransaction();
            fragmenttransaction.replace(R.id.fid1,fragment);
            fragmenttransaction.commit();
        }

    }


}
