package com.example.smartparkapp.dashboard.ui.dialogue;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartparkapp.dto.ParkingDTO;
import com.example.smartparkapp.dto.ParkingLotDTO;
import com.example.smartparkapp.dto.QuickParkingInfoDTO;
import com.example.smartparkapp.service.BookingService;
import com.example.smartparkapp.service.ParkingService;

import java.util.UUID;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class BookingDialogueViewModel extends ViewModel {

    private final MutableLiveData<QuickParkingInfoDTO> quickParkingInfoData = new MutableLiveData<>();
    private final MutableLiveData<String> bookedSpotData = new MutableLiveData<>();
    private final ParkingService parkingService = ParkingService.getInstance();
    private final BookingService bookingService = BookingService.getInstance();

    public BookingDialogueViewModel() {}

    public void loadQuickParkingInfo(UUID parkingId) {
        parkingService.getQuickParkingInfo(parkingId).subscribe(new SingleObserver<Response<QuickParkingInfoDTO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Response<QuickParkingInfoDTO> response) {
                if (response.isSuccessful()) {
                    Log.i("loadQuickParkingInfo", "Got response baaaaby!");
                    quickParkingInfoData.postValue(response.body());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void bookASpot(UUID parkingId) {
        ParkingDTO parkingDTO = new ParkingDTO(parkingId, null);
        bookingService.bookSpot(parkingDTO).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                bookedSpotData.postValue("OK");
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }



    public MutableLiveData<QuickParkingInfoDTO> getQuickParkingInfoData() {
        return quickParkingInfoData;
    }

    public MutableLiveData<String> getBookedSpotData() {
        return bookedSpotData;
    }
}
