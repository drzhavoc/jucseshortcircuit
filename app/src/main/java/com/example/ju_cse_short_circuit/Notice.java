package com.example.ju_cse_short_circuit;

public class Notice {
    private String category;
    private String details;
    private String uploaderEmail;
    private String uploadTime;

    // Default constructor (required for Firestore)
    public Notice() {
        // Default constructor required for Firestore
    }

    public Notice(String category, String details, String uploaderEmail, String uploadTime) {
        this.category = category;
        this.details = details;
        this.uploaderEmail = uploaderEmail;
        this.uploadTime = uploadTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUploaderEmail() {
        return uploaderEmail;
    }

    public void setUploaderEmail(String uploaderEmail) {
        this.uploaderEmail = uploaderEmail;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
