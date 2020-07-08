package com.example.smartparkapp.dashboard.ui.dialogue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dashboard.ui.dialogue.steps.BookingStepOneFragment;

import java.util.Objects;

public class BookingDialogueFragment extends DialogFragment {

    public BookingDialogueFragment() {
    }

    public static BookingDialogueFragment newInstance(Bundle bundle) {
        BookingDialogueFragment bookingDialogueFragment = new BookingDialogueFragment();
        bookingDialogueFragment.setArguments(bundle);
        return  bookingDialogueFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        FragmentManager fragmentManager = getChildFragmentManager();
        BookingStepOneFragment stepOneFragment = BookingStepOneFragment.newInstance(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.booking_step_layout, stepOneFragment, "STEP_ONE_FRAGMENT")
                .commit();
    }
}
