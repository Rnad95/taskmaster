package com.example.taskmaster;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

@Entity
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name;
    private List<Task> tasks;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getIdTeam() {
        String str = ""+id;
        return str;
    }
}
