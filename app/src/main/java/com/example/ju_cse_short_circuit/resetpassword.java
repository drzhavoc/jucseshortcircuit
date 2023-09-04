package com.example.ju_cse_short_circuit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseAuth;
    private Button reset;
    private EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        reset=findViewById(R.id.buttonResetPassword);
        email=(EditText) findViewById(R.id.emailid22);
        reset.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
    if( view.getId()==R.id.buttonResetPassword)
        resetPassword();
    }
    private void resetPassword() {
        String userEmail = email.getText().toString();
        firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Password reset email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}