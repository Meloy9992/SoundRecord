package com.example.soundrecording.adapters;

import java.io.Serializable;

public class ListAudio implements Serializable {

    private String recName;
    private String filePath;
    private String recLength;
    private String addedTime;
    private int id = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRecLength() {
        return recLength;
    }

    public void setRecLength(String recLength) {
        this.recLength = recLength;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }
}
