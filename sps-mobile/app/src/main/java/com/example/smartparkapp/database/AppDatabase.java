package com.example.smartparkapp.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartparkapp.dao.ParkingLocationDao;
import com.example.smartparkapp.model.ParkingLocation;

@Database(entities = {ParkingLocation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ParkingLocationDao parkingLocationDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(application.getApplicationContext(),
                    AppDatabase.class)
                    .build();
        }
        return INSTANCE;
    }
}
