package com.example.smartparkapp.http.api;

import com.example.smartparkapp.dto.ParkingDTO;
import com.example.smartparkapp.dto.ParkingLocationDTO;
import com.example.smartparkapp.dto.QuickParkingInfoDTO;

import java.util.List;
import java.util.UUID;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParkingAPI {

    @GET("parkings/locations")
    Single<Response<List<ParkingLocationDTO>>> getParkingsLocations();

    @GET("parkings/{id}/info/quick")
    Single<Response<QuickParkingInfoDTO>> getQuickParkingInfo(@Path("id") UUID parkingId);
}
