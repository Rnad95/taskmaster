package com.example.taskmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "state")
    private State state;

    public Task(String title, String body, State state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }
    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = State.valueOf(state);
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

//    public String getState() {
//        return state.toString();
//    }

    public State getState() {
        return state;
    }
}
