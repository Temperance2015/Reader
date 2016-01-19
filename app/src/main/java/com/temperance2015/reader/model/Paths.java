package com.temperance2015.reader.model;

import org.litepal.crud.DataSupport;

/**
 * Created by Isabel on 2016/1/18.
 */
public class Paths extends DataSupport {
    private int id;
    private String path;
    private String content;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }




}
