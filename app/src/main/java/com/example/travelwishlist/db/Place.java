package com.example.travelwishlist.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;

@Entity
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date date;
    private String reason;

    public Place(){}

    @Ignore
    Place(String name, String reason){
        this.name = name;
        this.date = new Date();
        this.reason = reason;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getReason() {
        return reason;
    }

    public String getDate(){
        return DateFormat.getDateInstance().format(date);
    }


    @Override
    public String toString(){
        return "Place{" +
                "id=" + id +
                ", name=" + name +
                ", reason=" + reason +
                ", date=" + date +
                '}';
    }
}
