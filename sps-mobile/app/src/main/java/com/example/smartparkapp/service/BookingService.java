package com.example.smartparkapp.service;

import com.example.smartparkapp.dto.BookRequestDto;
import com.example.smartparkapp.dto.ParkingDTO;
import com.example.smartparkapp.dto.ParkingLotDTO;
import com.example.smartparkapp.http.api.BookingAPI;
import com.example.smartparkapp.http.client.SPSClient;

import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BookingService {

    private final static BookingService INSTANCE = new BookingService();
    private final BookingAPI bookingAPI;

    private BookingService() {
        this.bookingAPI = SPSClient.getClient().create(BookingAPI.class);
    }

    public static BookingService getInstance() {
        return INSTANCE;
    }

    public Completable bookSpot(ParkingDTO parkingDTO) {
        return bookingAPI.bookASpot(parkingDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<BookRequestDto>> getAllBookRequests() {
        return bookingAPI.getAllBookRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable confirmArrival(UUID bookRequestId) {
        return bookingAPI.confirmArrival(bookRequestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
