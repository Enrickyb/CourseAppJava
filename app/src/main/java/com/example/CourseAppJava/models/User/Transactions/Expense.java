package com.example.CourseAppJava.models.User.Transactions;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class Expense implements Serializable {
    private String id;
    private String title;
    private String description;
    private float value;
    private String date;

    public Expense(String id, String title, String description, float value, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getValue() {

        return value;

    }

    public String getFormatedValue(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedValue = currencyFormat.format(value);
        return formattedValue;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDate() {
        String[] dateArray = date.split("-");
        String day = dateArray[2];
        String month = dateArray[1];
        String year = dateArray[0];
        String formattedDate = day + "/" + month + "/" + year;

        return formattedDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Value: " + value + "\n" +
                "Date: " + date;
    }




}
