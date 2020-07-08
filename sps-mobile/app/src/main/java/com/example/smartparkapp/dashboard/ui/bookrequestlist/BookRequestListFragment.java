package com.example.smartparkapp.dashboard.ui.bookrequestlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dto.BookRequestDto;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;


public class BookRequestListFragment extends Fragment {

    private BookRequestListViewModel bookRequestListViewModel;
    private ListView bookRequestList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookRequestListViewModel =
                ViewModelProviders.of(this).get(BookRequestListViewModel.class);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        bookRequestList = view.findViewById(R.id.book_requests);

        bookRequestListViewModel.getAllBookRequests();

        bookRequestListViewModel.getBookRequests()
                .observe(this, this::createList);

        bookRequestListViewModel.getArrivalConfirmation()
                .observe(this, value -> {
                    Snackbar.make(Objects.requireNonNull(this.getView()), "Successfully confirmed arrival", 2000);
                    bookRequestListViewModel.getAllBookRequests();
                });

        return view;
    }

    private void createList(List<BookRequestDto> bookRequestDtoList) {
        bookRequestList.setAdapter(new BookRequestListAdapter(this.getContext(), bookRequestDtoList, bookRequestListViewModel));
    }
}