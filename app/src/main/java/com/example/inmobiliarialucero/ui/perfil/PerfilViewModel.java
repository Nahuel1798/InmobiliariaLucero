package com.example.inmobiliarialucero.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliarialucero.modelo.Propietario;
import com.example.inmobiliarialucero.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<Propietario> propietario = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getEstado() {
        return mEstado;
    }

    public LiveData<String> getNombre() {
        return mNombre;
    }

    public LiveData<Propietario> getPropietario() {
        return propietario;
    }

    public void cambioBoton(String nombreboton, String nombre, String apellido, String dni, String telefono, String email) {
        if (nombreboton.equalsIgnoreCase("EDITAR")) {
            mEstado.setValue(true);
            mNombre.setValue("GUARDAR");
        } else {
            mEstado.setValue(false);
            mNombre.setValue("EDITAR");

            Propietario actualizado = new Propietario();
            actualizado.setIdPropietario(propietario.getValue().getIdPropietario());
            actualizado.setNombre(nombre);
            actualizado.setApellido(apellido);
            actualizado.setDni(dni);
            actualizado.setTelefono(telefono);
            actualizado.setEmail(email);

            String token = ApiClient.leertoken(getApplication());
            Log.d("PERFIL", "Token usado para actualizar: " + token);

            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call = api.actualizarProp("Bearer " + token, actualizado);

            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        propietario.postValue(response.body());
                        Toast.makeText(getApplication(), "ACTUALIZADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                        Log.d("PERFIL", "Perfil actualizado correctamente: " + response.body().getNombre());
                    } else {
                        Toast.makeText(getApplication(), "ERROR AL ACTUALIZAR: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("PERFIL", "Error: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "ERROR EN LA API: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("PERFIL", "Fallo en actualización: " + throwable.getMessage());
                }
            });
        }
    }

    public void obtenerPerfil() {
        String token = ApiClient.leertoken(getApplication());
        Log.d("PERFIL", "Token leído: " + token);

        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Propietario> call = api.getPropietario("Bearer " + token);

        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    propietario.postValue(response.body());
                    Log.d("PERFIL", "Perfil cargado: " + response.body().getNombre());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener perfil: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("PERFIL", "Respuesta sin éxito: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en la conexión: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PERFIL", "Fallo de red: " + throwable.getMessage());
            }
        });
    }
}
