package com.example.smartparkapp.dashboard.ui.dialogue.steps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dashboard.ui.dialogue.BookingDialogueViewModel;
import com.example.smartparkapp.dto.ParkingLotDTO;
import com.google.android.material.button.MaterialButton;


import java.util.Objects;
import java.util.UUID;

public class BookingStepTwoFragment extends Fragment {

    private BookingDialogueViewModel bookingDialogueViewModel;
    private TextView parkingNameText;
    private MaterialButton confirmBookingRequestButton;
    private MaterialButton rejectBookingRequestButton;

    public static BookingStepTwoFragment newInstance(Bundle bundle) {
        BookingStepTwoFragment bookingStepTwoFragment = new BookingStepTwoFragment();
        bookingStepTwoFragment.setArguments(bundle);
        return bookingStepTwoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bookingDialogueViewModel =
                ViewModelProviders.of(this).get(BookingDialogueViewModel.class);

        View view = inflater.inflate(R.layout.fragment_booking_second, container, false);
        initViews(view);

        String parkingName = Objects.requireNonNull(getArguments()).getString("parking_name");
        parkingNameText.setText(parkingName);

        bookingDialogueViewModel.getBookedSpotData().observe(this, this::handleSuccessfulBooking);

        confirmBookingRequestButton.setOnClickListener(v -> {
            UUID parkingId = UUID.fromString(Objects.requireNonNull(getArguments()).getString("parking_id"));
            bookingDialogueViewModel.bookASpot(parkingId);
        });

        return view;
    }

    private void initViews(View view) {
        parkingNameText = view.findViewById(R.id.parking_name);
        confirmBookingRequestButton = view.findViewById(R.id.confirm_booking_request);
        rejectBookingRequestButton = view.findViewById(R.id.reject_booking_request);
    }

    private void handleSuccessfulBooking(String string) {
        Bundle bundle = Objects.requireNonNull(getArguments());

        String bookingResponseMessage = "Successfully booked a parking spot!";
        bundle.putString("bookingResponseMessage", bookingResponseMessage);

        BookingStepThreeFragment bookingStepThreeFragment = BookingStepThreeFragment.newInstance(bundle);
        FragmentManager fragmentManager = Objects.requireNonNull(getFragmentManager());
        fragmentManager.beginTransaction()
                .replace(R.id.booking_step_layout, bookingStepThreeFragment)
                .commit();
    }
}
