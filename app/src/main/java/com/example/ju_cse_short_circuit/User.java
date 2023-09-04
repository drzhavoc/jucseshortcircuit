package com.example.ju_cse_short_circuit;

public class User {
     String firstName;
     String lastName;
     String regNo;
     String phoneNo;
     String email;

    public User() {
        // Default constructor required for Firestore
    }

    public User( String email,String firstName, String lastName,  String phoneNo,String regNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.regNo = regNo;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
