package com.example.smartparkapp.dashboard.ui.bookrequestlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartparkapp.dto.BookRequestDto;
import com.example.smartparkapp.service.BookingService;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class BookRequestListViewModel extends ViewModel {

    private MutableLiveData<List<BookRequestDto>> bookRequests;
    private MutableLiveData<String> arrivalConfirmation;
    private BookingService bookingService = BookingService.getInstance();

    public BookRequestListViewModel() {
        bookRequests = new MutableLiveData<>();
        arrivalConfirmation = new MutableLiveData<>();
    }

    void confirmArrival(BookRequestDto bookRequestDto) {
        bookingService.confirmArrival(bookRequestDto.getId()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                arrivalConfirmation.postValue("OK");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    void getAllBookRequests() {
        bookingService.getAllBookRequests().subscribe(new SingleObserver<List<BookRequestDto>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<BookRequestDto> bookRequestDtoList) {
                bookRequests.postValue(bookRequestDtoList);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    MutableLiveData<List<BookRequestDto>> getBookRequests() {
        return bookRequests;
    }

    public MutableLiveData<String> getArrivalConfirmation() {
        return arrivalConfirmation;
    }
}