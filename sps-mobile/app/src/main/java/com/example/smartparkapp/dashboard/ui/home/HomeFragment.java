package com.example.smartparkapp.dashboard.ui.home;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.smartparkapp.R;
import com.example.smartparkapp.dashboard.ui.dialogue.BookingDialogueFragment;
import com.example.smartparkapp.dto.ParkingDTO;
import com.example.smartparkapp.model.ParkingLocation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private HomeViewModel homeViewModel;
    private GoogleMap map;
    private Map<Marker, ParkingDTO> markerParkingMap = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        fragmentTransaction.add(R.id.map, mapFragment);
        fragmentTransaction.commit();
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i("MAP_READY", "Map is ready to be used");
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        placeMarkers();
        LatLng initialLocation = getInitialLocation();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(initialLocation, 10);
        map.moveCamera(cameraUpdate);
        map.setOnMarkerClickListener(this);
        homeViewModel.loadParkingsLocations();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle bundle = new Bundle();
        ParkingDTO parkingDTO = markerParkingMap.get(marker);
        bundle.putString("parking_id", Objects.requireNonNull(parkingDTO).getId().toString());
        bundle.putString("parking_name", Objects.requireNonNull(parkingDTO).getName());
        BookingDialogueFragment bookingDialogue = BookingDialogueFragment.newInstance(bundle);
        bookingDialogue.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "BOOKING_DIALOGUE");
        return true;
    }

    private LatLng getInitialLocation() {
        if (Geocoder.isPresent()) {
            try {
                Geocoder geocoder = new Geocoder(getContext());
                String defaultLocation = "Chisinau";
                List<Address> addresses = geocoder.getFromLocationName(defaultLocation, 3);
                List<LatLng> latLngs = addresses.stream()
                                                .filter(address -> address.hasLongitude() && address.hasLongitude())
                                                .map(address -> new LatLng(address.getLatitude(), address.getLongitude()))
                                                .collect(Collectors.toList());


                return latLngs.get(0);

            } catch (IOException ex) {
                Log.i("getInitialLocation", "Failed to get location from name");
            }
        }
        Log.i("getInitialLocation", "Geocoder is not present");
        return new LatLng(0, 0);
    }

    private void placeMarkers() {
        this.homeViewModel.getParkingsLocationsData().observe(this, parkingsLocations -> {
            for (ParkingLocation parkingLocation : parkingsLocations) {
                MarkerOptions markerOptions = createMarker(parkingLocation);
                Marker parkingMarker = map.addMarker(markerOptions);
                markerParkingMap.put(parkingMarker,
                        new ParkingDTO(UUID.fromString(parkingLocation.getParkingId()),
                                parkingLocation.getParkingName()));
            }
        });
    }

    private MarkerOptions createMarker(ParkingLocation parkingLocation) { ;
        LatLng parkingLatLng = new LatLng(parkingLocation.getLatitude(), parkingLocation.getLongitude());
        return new MarkerOptions()
                .position(parkingLatLng)
                .title(parkingLocation.getParkingName());
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.loadParkingsLocations();
    }
}