package com.example.kursach3.models;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class Cafe {
    String name, password, email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cafe() {
    }

    public Cafe(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
