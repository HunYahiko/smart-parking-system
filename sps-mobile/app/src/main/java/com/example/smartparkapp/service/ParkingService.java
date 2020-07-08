package com.example.smartparkapp.service;

import com.example.smartparkapp.dto.ParkingDTO;
import com.example.smartparkapp.dto.ParkingLocationDTO;
import com.example.smartparkapp.dto.QuickParkingInfoDTO;
import com.example.smartparkapp.http.api.ParkingAPI;
import com.example.smartparkapp.http.client.SPSClient;

import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ParkingService {
    private static final ParkingService INSTANCE = new ParkingService();
    private final ParkingAPI parkingAPI;

    private ParkingService() {
        this.parkingAPI = SPSClient.getClient().create(ParkingAPI.class);
    }

    public static ParkingService getInstance() {
        return INSTANCE;
    }

    public Single<Response<List<ParkingLocationDTO>>> fetchParkingsLocations() {
        return parkingAPI.getParkingsLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Response<QuickParkingInfoDTO>> getQuickParkingInfo(UUID parkingId) {
        return parkingAPI.getQuickParkingInfo(parkingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
