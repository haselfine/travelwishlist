package com.example.travelwishlist.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Place implements Comparable<Place>{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id; //unique identifier
    private String name; //name of place
    private Date date; //date created
    private String reason; //reason to visit

    public Place(){}

    @Ignore
    public Place(String name, String reason){


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

    public void setName(String name){
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int compareTo(Place place) {
        return this.name.toLowerCase().compareTo(place.getName().toLowerCase());
    }

    @Override
    public String toString(){ //string for insertion into database
        return "Place{" +
                "id=" + id +
                ", name=" + name +
                ", reason=" + reason +
                ", date=" + date +
                '}';
    }
}
