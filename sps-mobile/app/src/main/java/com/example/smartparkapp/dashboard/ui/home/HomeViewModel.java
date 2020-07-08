package com.example.smartparkapp.dashboard.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.smartparkapp.database.AppDatabase;
import com.example.smartparkapp.dto.ParkingLocationDTO;
import com.example.smartparkapp.model.ParkingLocation;
import com.example.smartparkapp.model.mapper.Mapper;
import com.example.smartparkapp.model.mapper.ParkingLocationMapper;
import com.example.smartparkapp.service.ParkingService;

import java.util.List;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<List<ParkingLocation>> parkingsLocationsData;
    private final ParkingService parkingService = ParkingService.getInstance();
    private final Mapper<ParkingLocation, ParkingLocationDTO> mapper = new ParkingLocationMapper();
    private final AppDatabase appDatabase;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getInstance(application);
        parkingsLocationsData = new MutableLiveData<>();
    }

    void loadParkingsLocations() {
        appDatabase.parkingLocationDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ParkingLocation>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<ParkingLocation> parkingLocations) {
                        handleCachedValues(parkingLocations);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void handleCachedValues(List<ParkingLocation> parkingLocations) {
        if (parkingLocations.size() != 0) {
            parkingsLocationsData.postValue(parkingLocations);
        }
        else {
            parkingService.fetchParkingsLocations().subscribe(new SingleObserver<Response<List<ParkingLocationDTO>>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Response<List<ParkingLocationDTO>> listResponse) {
                    handleResponse(listResponse);
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }
    }

    private void handleResponse(Response<List<ParkingLocationDTO>> listResponse) {
        if (listResponse.isSuccessful()) {
            List<ParkingLocation> parkingLocations = mapper.mapList(Objects.requireNonNull(listResponse.body()));
            appDatabase.parkingLocationDao().insertAll(parkingLocations)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
            parkingsLocationsData.postValue(parkingLocations);
        } else {
            Log.e("loadParkingLocations: failed because status", Integer.toString(listResponse.code()));
        }
    }

    MutableLiveData<List<ParkingLocation>> getParkingsLocationsData() {
        return parkingsLocationsData;
    }
}