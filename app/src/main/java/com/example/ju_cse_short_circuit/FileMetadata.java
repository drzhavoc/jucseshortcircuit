package com.example.ju_cse_short_circuit;

public class FileMetadata {
    private String fileName;
    private String uploaderName;
    private String uploadTime;
    private String fileType;

    // Constructor with all properties
    public FileMetadata(String fileName, String uploaderName, String uploadTime, String fileType) {
        this.fileName = fileName;
        this.uploaderName = uploaderName;
        this.uploadTime = uploadTime;
        this.fileType = fileType;
    }

    // Getters and setters for each property
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
