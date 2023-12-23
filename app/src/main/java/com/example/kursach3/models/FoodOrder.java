package com.example.kursach3.models;

public class FoodOrder {
    String cafeName, tableName, foodName, userName;

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FoodOrder(String cafeName, String tableName, String foodName, String userName) {
        this.cafeName = cafeName;
        this.tableName = tableName;
        this.foodName = foodName;
        this.userName = userName;
    }
}
