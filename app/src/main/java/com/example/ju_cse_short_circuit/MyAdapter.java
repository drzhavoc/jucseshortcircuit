package com.example.ju_cse_short_circuit;
/*
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User>userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item1,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
    User user = userArrayList.get(position);
    holder.firstName.setText(user.firstName);
    holder.lastName.setText(user.lastName);
    holder.regNo.setText(user.regNo);
    holder.phoneNo.setText(user.phoneNo);
    holder.email.setText(user.email);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstName,lastName,regNo,phoneNo,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName=itemView.findViewById(R.id.firstnameid1);
            lastName=itemView.findViewById(R.id.lastnameid1);
            regNo=itemView.findViewById(R.id.regnoid1);
            phoneNo=itemView.findViewById(R.id.phonenoid1);
            email=itemView.findViewById(R.id.emailid1);
        }
    }
}
*/

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.firstName.setText(user.firstName);
        holder.lastName.setText(user.lastName);
        holder.regNo.setText(user.regNo);
        holder.phoneNo.setText(user.phoneNo);
        holder.email.setText(user.email);

        // Add click listeners for phone numbers and emails
        holder.phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = user.phoneNo;
                makePhoneCall(phoneNumber);
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.email;
                sendEmail(email);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, regNo, phoneNo, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.firstnameid1);
            lastName = itemView.findViewById(R.id.lastnameid1);
            regNo = itemView.findViewById(R.id.regnoid1);
            phoneNo = itemView.findViewById(R.id.phonenoid1);
            email = itemView.findViewById(R.id.emailid1);
        }
    }

    private void makePhoneCall(String phoneNumber) {


        // Intent intent = new Intent(Intent.ACTION_DIAL);
        //intent.setData(Uri.parse("tel:" + phoneNumber));

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));


        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No dialer app found!", Toast.LENGTH_SHORT).show();
        }
    }



    private void sendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No email app found!", Toast.LENGTH_SHORT).show();
        }
    }
}
