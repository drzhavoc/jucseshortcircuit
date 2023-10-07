package com.example.ju_cse_short_circuit;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class filehandler extends AppCompatActivity implements FileAdapter.OnDownloadClickListener {

    private static final int FILE_PICKER_REQUEST = 123;
    private ProgressBar uploadProgressBar;
    private Button uploadButton;
    private RecyclerView recyclerView;
    private FileAdapter adapter;
    private List<FileMetadata> fileMetadataList;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filehandler);

        uploadProgressBar = findViewById(R.id.uploadProgressBar);
        uploadButton = findViewById(R.id.uploadButton);
        recyclerView = findViewById(R.id.recyclerView);
        fileMetadataList = new ArrayList<>();
        adapter = new FileAdapter(this, fileMetadataList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(filehandler.this, task_activity.class));
            finish();
        }

        uploadButton.setOnClickListener(v -> selectFileAndUpload());
        displayAllFiles();
    }

    private void selectFileAndUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Files"), FILE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri fileUri = data.getClipData().getItemAt(i).getUri();
                    uploadFileToFirebaseStorage(fileUri);
                }
            } else if (data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                if (fileUri != null) {
                    uploadFileToFirebaseStorage(fileUri);
                }
            }
        }
    }

    private void uploadFileToFirebaseStorage(Uri fileUri) {
        String originalFileName = getOriginalFileName(fileUri);
        StorageReference fileRef = storageRef.child("files/" + originalFileName);

        UploadTask uploadTask = fileRef.putFile(fileUri);
        uploadProgressBar.setVisibility(View.VISIBLE);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(filehandler.this, "Upload Successful", Toast.LENGTH_SHORT).show();

            String fileType = getFileType(fileUri);
            storeFileMetadataInFirestore(currentUser.getEmail(), originalFileName, fileType);

            displayAllFiles();
            uploadProgressBar.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            Toast.makeText(filehandler.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            uploadProgressBar.setVisibility(View.GONE);
        }).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            uploadProgressBar.setProgress((int) progress);
        });
    }

    private void storeFileMetadataInFirestore(String userEmail, String fileName, String fileType) {
        CollectionReference filesRef = db.collection("files");
        Map<String, Object> fileData = new HashMap<>();
        fileData.put("email", userEmail);
        fileData.put("title", fileName);
        fileData.put("uploadTime", new Date());
        fileData.put("fileType", fileType);

        filesRef.add(fileData)
                .addOnSuccessListener(documentReference -> {
                    // Handle successful Firestore document creation
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(filehandler.this, "Failed to store file metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private String getFileType(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

        if (extension != null && !extension.isEmpty()) {
            return extension;
        } else {
            return "unknown";
        }
    }

    private void displayAllFiles() {
        CollectionReference filesRef = db.collection("files");

        filesRef.orderBy("uploadTime", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    fileMetadataList.clear();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String uploaderName = document.getString("email");
                        String fileName = document.getString("title");
                        String fileType = document.getString("fileType");
                        Date uploadTime11 = document.getDate("uploadTime");
                        String uploadTime = uploadTime11.toString();

                        FileMetadata fileMetadata = new FileMetadata(fileName, uploaderName, uploadTime, fileType);

                        fileMetadataList.add(fileMetadata);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(filehandler.this, "Failed to fetch files: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDownloadClick(int position) {
        String fileName = fileMetadataList.get(position).getFileName();
        downloadFile(fileName);
    }

    private void downloadFile(String fileName) {
        StorageReference fileRef = storageRef.child("files/" + fileName);
       String fileExtension = getFileExtension(fileName);
       String filenameonly = getFileNameWithoutExtension(fileName);

        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadFiles(filehandler.this, filenameonly, fileExtension, DIRECTORY_DOWNLOADS, url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(filehandler.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }


    private String getFileNameWithoutExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            // There is no file extension
            return fileName;
        } else {
            // Remove the file extension
            return fileName.substring(0, lastIndex);
        }
    }


    public void downloadFiles(Context context, String filename, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename + "." + fileExtension);
        downloadManager.enqueue(request);
    }



    private String getOriginalFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1 && cursor.moveToFirst()) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }



}
