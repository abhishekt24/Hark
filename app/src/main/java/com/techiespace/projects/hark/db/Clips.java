package com.techiespace.projects.hark.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//clipID | Title | Level | Accent | Gender | StopPoints
//stopPoints : start1 stop1 start2 stop2 ...

@Entity
public class Clips {

    @ColumnInfo
    @PrimaryKey
    @NonNull
    private String clipID;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String level;
    @ColumnInfo
    private String difficulty;
    @ColumnInfo
    private String accent;
    @ColumnInfo
    private String gender;
    @ColumnInfo
    private String stopPoints;

    public Clips(String clipID, String title, String level, String difficulty, String accent, String gender, String stopPoints) {
        this.clipID = clipID;
        this.title = title;
        this.level = level;
        this.difficulty = difficulty;
        this.accent = accent;
        this.gender = gender;
        this.stopPoints = stopPoints;
    }

    @NonNull
    public String getClipID() {
        return clipID;
    }

    public void setClipID(@NonNull String clipID) {
        this.clipID = clipID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getAccent() {
        return accent;
    }

    public void setAccent(String accent) {
        this.accent = accent;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(String stopPoints) {
        this.stopPoints = stopPoints;
    }
}
