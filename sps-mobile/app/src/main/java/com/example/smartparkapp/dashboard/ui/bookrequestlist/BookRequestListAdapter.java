package com.example.smartparkapp.dashboard.ui.bookrequestlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dto.BookRequestDto;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class BookRequestListAdapter extends BaseAdapter {

    private List<BookRequestDto> bookRequestDtoList;
    private LayoutInflater inflater;
    private BookRequestListViewModel bookRequestListViewModel;

    public BookRequestListAdapter(Context context, List<BookRequestDto> bookRequestDtoList, BookRequestListViewModel bookRequestListViewModel) {
        this.bookRequestDtoList = bookRequestDtoList;
        this.inflater = LayoutInflater.from(context);
        this.bookRequestListViewModel = bookRequestListViewModel;
    }

    @Override
    public int getCount() {
        return bookRequestDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookRequestDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bookrequest_row, null, true);
        }

        TextView parkingName = convertView.findViewById(R.id.parking_name_text);
        TextView parkingLotName = convertView.findViewById(R.id.parking_lot_name_text);
        TextView bookRequestStatus = convertView.findViewById(R.id.book_request_status_text);
        MaterialButton confirmArrivalButton = convertView.findViewById(R.id.confirm_arrival_button);

        BookRequestDto bookRequestDto = bookRequestDtoList.get(position);

        parkingName.setText(bookRequestDto.getParking());
        parkingLotName.setText(bookRequestDto.getParkingLot());
        bookRequestStatus.setText(bookRequestDto.getBookRequestStatus().getStatus());

        confirmArrivalButton.setOnClickListener(view -> bookRequestListViewModel.confirmArrival(bookRequestDto));

        return convertView;
    }
}
