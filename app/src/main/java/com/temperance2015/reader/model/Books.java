package com.temperance2015.reader.model;

import org.litepal.crud.DataSupport;

/**
 * Created by Isabel on 2015/12/14.
 */
public class Books extends DataSupport {

    private int id;
    private String title;
    private String path;
    private String readDate;
    private double schedule;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getReadDate(){
        return readDate;
    }

    public void setReadDate(String readDate){
        this.readDate = readDate;
    }

    public double getSchedule(){
        return schedule;
    }

    public void setSchedule(double schedule){
        this.schedule = schedule;
    }

}
