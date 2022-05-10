package com.example.taskmaster;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao studentDao();
    private static AppDatabase appDatabase; //declaration for the instance
    public AppDatabase() {

    }

    public static synchronized AppDatabase getInstance(Context context) {

        if(appDatabase == null)
        {
            appDatabase = Room.databaseBuilder(context,
                    AppDatabase.class, "AppDatabase").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
