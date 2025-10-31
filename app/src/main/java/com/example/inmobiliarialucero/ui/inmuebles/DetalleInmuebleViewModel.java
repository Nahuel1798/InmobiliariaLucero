package com.example.inmobiliarialucero.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.inmobiliarialucero.modelo.Inmueble;
import com.example.inmobiliarialucero.request.ApiClient;

import retrofit2.Callback;
import retrofit2.Response;


public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;


    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        if (mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
        if (inmueble!=null){
            mInmueble.setValue(inmueble);
        }
    }

    public void actualizarInmueble(Inmueble inmueble) {
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        String token = ApiClient.leertoken(getApplication());
        inmueble.setIdInmueble(mInmueble.getValue().getIdInmueble()); // Primero asignar el ID
        retrofit2.Call<Inmueble> call = api.actualizarInmueble( "Bearer "+token, inmueble); // Luego crear el call

        call.enqueue(new retrofit2.Callback<Inmueble>() {
            @Override
            public void onResponse(retrofit2.Call<Inmueble> call, retrofit2.Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente (" + response.code() + ")", Toast.LENGTH_SHORT).show();
                    mInmueble.postValue(response.body()); // Refresca el LiveData
                } else {
                    Toast.makeText(getApplication(), "No se pudo actualizar el inmueble", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Inmueble> call, Throwable t) {
                Toast.makeText(getApplication(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}