package com.example.inmobiliarialucero.ui.Inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mutableMapaActual;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        mutableMapaActual = new MutableLiveData<>();
    }

    public LiveData<MapaActual> getMutableMapaActual() {
        if (mutableMapaActual == null) {
            mutableMapaActual = new MutableLiveData<>();
        }
        return mutableMapaActual;
    }

    public void cargarMapa() {
        mutableMapaActual.setValue(new MapaActual());
    }

    public class MapaActual implements OnMapReadyCallback {
        private final LatLng SANLUIS = new LatLng(-33.301726, -66.337752);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("San Luis Capital"));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(SANLUIS)      // Sets the center of the map to Mountain View
                    .zoom(20)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition( cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}