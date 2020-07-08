package com.example.smartparkapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.smartparkapp.model.ParkingLocation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ParkingLocationDao {

    @Query("SELECT * FROM parkinglocation")
    Single<List<ParkingLocation>> getAll();

    @Query("SELECT * FROM parkinglocation WHERE parking_name = :parkingName")
    Maybe<ParkingLocation> getByParkingName(String parkingName);

    @Insert
    Completable insertAll(List<ParkingLocation> parkingLocations);

    @Insert
    Completable insert(ParkingLocation parkingLocation);
}
