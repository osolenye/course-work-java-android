package com.example.kursach3.models;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@Keep
@IgnoreExtraProperties
public class Table {
    String tableName;
    String cafeName;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public Table() {
    }

    public Table(String tableName, String cafeName, String key) {
        this.tableName = tableName;
        this.cafeName = cafeName;
        this.key = key;
    }
}
