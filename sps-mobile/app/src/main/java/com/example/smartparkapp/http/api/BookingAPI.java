package com.example.smartparkapp.http.api;

import com.example.smartparkapp.dto.BookRequestDto;
import com.example.smartparkapp.dto.ParkingDTO;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingAPI {

    @POST("booking/")
    Completable bookASpot(@Body ParkingDTO parkingDTO);

    @GET("booking/")
    Single<List<BookRequestDto>> getAllBookRequests();

    @POST("booking/confirm/{id}")
    Completable confirmArrival(@Path("id")UUID bookRequestId);

}
