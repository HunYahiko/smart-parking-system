package com.example.smartparkapp.dashboard.ui.dialogue.steps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dashboard.ui.dialogue.BookingDialogueViewModel;
import com.example.smartparkapp.dto.AddressDTO;
import com.example.smartparkapp.dto.QuickParkingInfoDTO;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;
import java.util.UUID;

public class BookingStepOneFragment extends Fragment {

    private BookingDialogueViewModel bookingDialogueViewModel;

    private TextView parkingNameView;
    private TextView parkingAddressView;
    private TextView totalSpacesNumberView;
    private TextView freeSpacesNumberView;
    private MaterialButton startBookingButton;

    public static BookingStepOneFragment newInstance(Bundle bundle) {
        BookingStepOneFragment bookingStepOneFragment = new BookingStepOneFragment();
        bookingStepOneFragment.setArguments(bundle);
        return bookingStepOneFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        bookingDialogueViewModel =
                ViewModelProviders.of(this).get(BookingDialogueViewModel.class);

        View view = inflater.inflate(R.layout.fragment_booking_first, container, false);
        initViews(view);

        startBookingButton.setOnClickListener(v -> {
            Bundle bundle = Objects.requireNonNull(getArguments());
            bundle.putString("parking_name", parkingNameView.getText().toString());
            FragmentManager fragmentManager = getFragmentManager();
            BookingStepTwoFragment bookingStepTwoFragment
                    = BookingStepTwoFragment.newInstance(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.booking_step_layout, bookingStepTwoFragment)
                    .commit();
        });

        UUID parkingId = UUID.fromString(Objects.requireNonNull(getArguments()).getString("parking_id"));
        bookingDialogueViewModel.loadQuickParkingInfo(parkingId);
        bookingDialogueViewModel.getQuickParkingInfoData().observe(this, (quickParkingInfoDTO -> {
            Log.i("bookingDialogue", quickParkingInfoDTO.getFreeParkingLots().toString());
            updateInfo(quickParkingInfoDTO);
        }));

        return view;
    }

    private void initViews(View view) {
        parkingNameView = view.findViewById(R.id.parking_name);
        parkingAddressView = view.findViewById(R.id.parking_address);
        totalSpacesNumberView = view.findViewById(R.id.total_spaces_number);
        freeSpacesNumberView = view.findViewById(R.id.free_spaces_number);
        startBookingButton = view.findViewById(R.id.booking_start_button);
    }

    private void updateInfo(QuickParkingInfoDTO quickParkingInfo) {
        String parkingName = quickParkingInfo.getParking().getName();
        parkingNameView.setText(parkingName);

        AddressDTO parkingAddressDTO = quickParkingInfo.getAddress();
        String parkingAddress = parkingAddressDTO.getStreetName() + " "
                + parkingAddressDTO.getStreetNumber();
        parkingAddressView.setText(parkingAddress);

        String totalSpacesNumber = quickParkingInfo.getTotalParkingLots().toString();
        totalSpacesNumberView.setText(totalSpacesNumber);

        String freeSpacesNumber = quickParkingInfo.getFreeParkingLots().toString();
        freeSpacesNumberView.setText(freeSpacesNumber);
    }
}
