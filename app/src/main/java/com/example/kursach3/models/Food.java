package com.example.kursach3.models;

import androidx.annotation.Keep;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
@Keep
public class Food {
    String cafeName, typeName, foodName;

    public Food() {
    }

    public Food(String cafeName, String typeName, String foodName) {
        this.cafeName = cafeName;
        this.typeName = typeName;
        this.foodName = foodName;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
