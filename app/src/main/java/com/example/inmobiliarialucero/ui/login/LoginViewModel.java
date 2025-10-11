package com.example.inmobiliarialucero.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliarialucero.MainActivity;
import com.example.inmobiliarialucero.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void logueo(String usuario, String contrasenia) {
        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            mMensaje.setValue("Error, campos vacíos");
            return;
        }
        ApiClient.InmoServicio inmoServicio = ApiClient.getInmoServicio();
        Call<String> call = inmoServicio.login(usuario, contrasenia);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardartoken(getApplication(), token);
                    Log.d("token", token);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(intent);
                } else {
                    Log.d("token", response.message());
                    Log.d("token", response.code() + "");
                    Log.d("token", response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("token", throwable.getMessage());

            }
        });
    }

}
