package com.example.inmobiliarialucero.ui.contrato;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliarialucero.modelo.Inmueble;
import com.example.inmobiliarialucero.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();

    public ContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getListaInmuebles() {
        return listaInmuebles;
    }

    public void ObtenerInmueblesContrato() {
        String token = ApiClient.leertoken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<List<Inmueble>> call = api.InmueblesVigentes("Bearer " + token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    listaInmuebles.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se obtuvieron inmuebles", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorInmueble", throwable.getMessage());
                Toast.makeText(getApplication(), "Error al obtener inmuebles", Toast.LENGTH_LONG).show();
            }
        });
    }

}
