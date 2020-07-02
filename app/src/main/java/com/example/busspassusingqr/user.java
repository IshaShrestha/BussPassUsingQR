package com.example.busspassusingqr;

import android.widget.EditText;

public class user {

    private String name;
    private String email;
    private String type;
    public user(EditText name, EditText email){

    }

    public user(String name, String email, String type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}