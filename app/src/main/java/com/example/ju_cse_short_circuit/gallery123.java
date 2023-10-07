//tools:context=".gallery"



package com.example.ju_cse_short_circuit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class gallery123 extends AppCompatActivity {

    private static final int IMAGE_PICKER_REQUEST = 123;
    private LinearLayout imageLayout;
    private Button  selectImagesButton;
    private StorageReference storageRef;

    private RecyclerView recyclerView;
    private List<String> imageUrls;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery123);

        //imageLayout = findViewById(R.id.imageLT);


        selectImagesButton = findViewById(R.id.imageBT);

       // bt2 = findViewById(R.id.id2);


        // Initialize Firebase Storage reference

        storageRef = FirebaseStorage.getInstance().getReference();


        recyclerView = findViewById(R.id.recyclerView);
        imageUrls = new ArrayList<>();
        adapter = new ImageAdapter(this, imageUrls);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Storage reference

        selectImagesButton.setOnClickListener(v -> selectImages());

        // Display all uploaded images
        displayAllImages();

        selectImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImages();
            }
        });


    }

    private void selectImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), IMAGE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKER_REQUEST && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uploadImageToFirebaseStorage(imageUri);
                }
            }
        }
    }


    private void uploadImageToFirebaseStorage(Uri imageUri) {
        // Generate a unique filename using a timestamp and a random UUID
        String filename = System.currentTimeMillis() + "_" + UUID.randomUUID().toString();
        StorageReference imageRef = storageRef.child("images/" + filename);

        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful upload
                // Get the download URL of the uploaded image
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Toast.makeText(gallery123.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                        //String imageUrl = uri.toString();
                        //displayImageFromUrl(imageUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failed upload
                Toast.makeText(gallery123.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void displayAllImages() {
        // List all files in the "images" folder in Firebase Storage
        StorageReference imagesRef = storageRef.child("images");
        imagesRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    String imageUrl = uri.toString();
                                    imageUrls.add(imageUrl);
                                    adapter.notifyDataSetChanged();
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failures while retrieving download URLs
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failures while listing files
                    Toast.makeText(gallery123.this, "Failed to list images: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


}
