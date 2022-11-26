package com.alireza.notify;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataModal extends RealmObject {
    // on below line we are creating our variables
    // and with are using primary key for our id.
    @PrimaryKey
    private long id;
    private String title;
    private String description;
    private String time;

    // on below line we are
    // creating an empty constructor.
    public DataModal() {
    }

    // below line we are
    // creating getter and setters.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}

