package com.example.smartparkapp.dashboard.ui.dialogue.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dashboard.ui.dialogue.BookingDialogueFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class BookingStepThreeFragment extends Fragment {

    private TextView bookingResponseText;
    private TextView bookedSpotText;
    private TextView parkingName;
    private MaterialButton closeBookingDialogueButton;

    public static BookingStepThreeFragment newInstance(Bundle bundle) {
        BookingStepThreeFragment bookingStepThreeFragment = new BookingStepThreeFragment();
        bookingStepThreeFragment.setArguments(bundle);
        return bookingStepThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_three, container, false);
        initViews(view);

        Bundle bundle = Objects.requireNonNull(getArguments());
        String bookingResponseMessage = bundle.getString("bookingResponseMessage");
        bookingResponseText.setText(bookingResponseMessage);

//        String bookedSpot = bundle.getString("bookedSpot");
//        String bookedSpotMessage = "Your spot is: \n" + bookedSpot;
//        bookedSpotText.setText(bookedSpotMessage);

        BookingDialogueFragment dialogueFragment = (BookingDialogueFragment) getParentFragment();
        closeBookingDialogueButton.setOnClickListener(v -> Objects.requireNonNull(dialogueFragment).dismiss());
        return view;
    }

    private void initViews(View view) {
        bookingResponseText = view.findViewById(R.id.booking_response_message);
        bookedSpotText = view.findViewById(R.id.booking_spot);
        parkingName = view.findViewById(R.id.parking_name);
        closeBookingDialogueButton = view.findViewById(R.id.booking_close_button);
    }
}
