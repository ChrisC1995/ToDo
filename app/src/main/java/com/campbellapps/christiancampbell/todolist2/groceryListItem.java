package com.campbellapps.christiancampbell.todolist2;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class groceryListItem implements Comparable<groceryListItem> {

    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("date")
    private String date;

    @SerializedName("category")
    private String category;

    @SerializedName("currentDate")
    private Date currentDate;

    @SerializedName("day")
    private String day;

    @SerializedName("month")
    private String month;

    @SerializedName("time")
    private String time;




    public groceryListItem(String title, String text, String date, String category, Date currentDate, String day, String month, String time){
        this.title = title;
        this.text = text;
        this.date = date;
        this.category = category;
        this.currentDate = currentDate;
        this.day = day;
        this.month = month;
        this.time = time;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int compareTo(groceryListItem another) { // compares to other notes
//        return another.getDate().compareTo(getDate()); // sorts note by date. Compares one note date to to other notes date.
     return getCategory().compareTo(another.getCategory()); // returns oldest to newest

    }


}
