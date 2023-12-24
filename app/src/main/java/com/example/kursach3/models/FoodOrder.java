package com.example.kursach3.models;

public class FoodOrder {
    String cafeName;
    String tableName;
    String foodName;
    String userName;
    String cookName;
    String key;
    String status;

//    public FoodOrder(String cafeName, String tableName, String foodName, String userName) {
//        this.cafeName = cafeName;
//        this.tableName = tableName;
//        this.foodName = foodName;
//        this.userName = userName;
//        this.cookName = "none";
//        this.status = "notCooking";
//        this.key= "null";
//    }

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

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public FoodOrder(String cafeName, String tableName, String foodName, String userName, String cookName, String status, String key) {
//        this.cafeName = cafeName;
//        this.tableName = tableName;
//        this.foodName = foodName;
//        this.userName = userName;
//        this.cookName = cookName;
//        this.status = status;
//        this.key= key;
//    }
    public FoodOrder(String cafeName, String tableName, String foodName, String userName, String key){
        this.cafeName = cafeName;
        this.tableName = tableName;
        this.foodName = foodName;
        this.userName = userName;
        this.key= key;
        this.status = "noCooking";
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FoodOrder(){}
}
