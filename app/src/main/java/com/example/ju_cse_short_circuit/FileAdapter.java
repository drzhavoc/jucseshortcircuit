package com.example.ju_cse_short_circuit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
/*
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private final Context context;
    private final List<String> fileNames;
    private final OnDownloadClickListener downloadClickListener;

    public FileAdapter(Context context, List<String> fileNames, OnDownloadClickListener downloadClickListener) {
        this.context = context;
        this.fileNames = fileNames;
        this.downloadClickListener = downloadClickListener;
    }


    public interface OnDownloadClickListener {
        void onDownloadClick(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fileName = fileNames.get(position);
        holder.fileNameTextView.setText(fileName);

        // Handle download button click
        holder.downloadButton.setOnClickListener(v -> {
            if (downloadClickListener != null) {
                downloadClickListener.onDownloadClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;
        ImageButton downloadButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
            downloadButton = itemView.findViewById(R.id.downloadButton);
        }
    }
}*/



public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private final Context context;
    private final List<FileMetadata> fileMetadataList; // FileMetadata should represent file details
    private final OnDownloadClickListener downloadClickListener;

    public FileAdapter(Context context, List<FileMetadata> fileMetadataList, OnDownloadClickListener downloadClickListener) {
        this.context = context;
        this.fileMetadataList = fileMetadataList;
        this.downloadClickListener = downloadClickListener;
    }

    public interface OnDownloadClickListener {
        void onDownloadClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileMetadata fileMetadata = fileMetadataList.get(position);

        holder.fileNameTextView.setText(fileMetadata.getFileName());
        holder.uploaderNameTextView.setText("Uploaded by: " + fileMetadata.getUploaderName());
        holder.uploadTimeTextView.setText("Upload time: " + fileMetadata.getUploadTime());
        holder.fileTypeTextView.setText("File type: " + fileMetadata.getFileType());

        // Handle download button click
        holder.downloadButton.setOnClickListener(v -> {
            if (downloadClickListener != null) {
                downloadClickListener.onDownloadClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileMetadataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView, uploaderNameTextView, uploadTimeTextView, fileTypeTextView;
        ImageButton downloadButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
            uploaderNameTextView = itemView.findViewById(R.id.uploaderNameTextView);
            uploadTimeTextView = itemView.findViewById(R.id.uploadTimeTextView);
            fileTypeTextView = itemView.findViewById(R.id.fileTypeTextView);
            downloadButton = itemView.findViewById(R.id.downloadButton);
        }
    }
}

