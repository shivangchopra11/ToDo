package com.example.shivang.todo1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shivang on 22/12/17.
 */
@Entity
public class ToDo {
    @PrimaryKey
    String title;
    String description;
    String category;
    String date;
    String time;
    Boolean setAlarm;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getSetAlarm() {
        return setAlarm;
    }

    public void setSetAlarm(Boolean setAlarm) {
        this.setAlarm = setAlarm;
    }

    public ToDo(String title, String description, String category, String date, String time, Boolean setAlarm) {
        this.title = title;
        this.description = description;
        this.category = category;

        this.date = date;
        this.time = time;
        this.setAlarm = setAlarm;
    }
}
