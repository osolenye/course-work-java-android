package com.example.kursach3.models;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@Keep
@IgnoreExtraProperties
public class Type {
    String typeName;
    String cafeName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public Type() {
    }

    public Type(String typeName, String cafeName, String key) {
        this.typeName = typeName;
        this.cafeName = cafeName;
        this.key = key;
    }
}
