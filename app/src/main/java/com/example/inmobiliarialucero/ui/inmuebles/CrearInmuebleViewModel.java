package com.example.inmobiliarialucero.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliarialucero.modelo.Inmueble;
import com.example.inmobiliarialucero.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<Inmueble> mInmueble;
    private static Inmueble inmueblelleno;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        inmueblelleno = new Inmueble();
    }

    public LiveData<Uri> getUriMutableLiveData(){
        if(uriMutableLiveData == null){
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public  LiveData<Inmueble> getInmueble() {
        if (mInmueble == null) {
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada",uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }

    public void guardarInmueble(String direccion, String uso, String tipo, String precio, String ambientes, String superficie, boolean disponible){
        try{
            int amb = Integer.parseInt(ambientes);
            int superf = Integer.parseInt(superficie);
            double prec = Double.parseDouble(precio);
            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setUso(uso);
            inmueble.setTipo(tipo);
            inmueble.setValor(prec);
            inmueble.setAmbientes(amb);
            inmueble.setSuperficie(superf);
            inmueble.setDisponible(disponible);
            //convertir la imagen en bit
            byte[] imagen=transformarImagen();
            if (imagen.length == 0){
                Toast.makeText(getApplication(), "Ustes debe ingresar una imagen", Toast.LENGTH_SHORT).show();
                return;
            }
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            String token=ApiClient.leertoken(getApplication());
            Call<Inmueble> llamada=api.CargarInmueble("Bearer " + token, imagenPart, inmuebleBody);
            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al guardar inmueble", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (NumberFormatException ex){
            Toast.makeText(getApplication(),"Error, debe igresar un numero",Toast.LENGTH_LONG).show();
        }
    }

    // Metodo para transformar la imagen
    private byte[] transformarImagen() {
        if (uriMutableLiveData.getValue() == null) {
            Toast.makeText(getApplication(), "No ha seleccionado una foto", Toast.LENGTH_SHORT).show();
            return new byte[]{}; // Devuelve un array vacío
        }

        try {
            Uri uri = uriMutableLiveData.getValue();

            // Llama al metodo para escalar la imagen
            Bitmap bitmapReducido = escalarImagenDesdeUri(uri, 1024, 1024);

            if (bitmapReducido == null) {
                Toast.makeText(getApplication(), "No se pudo decodificar la imagen", Toast.LENGTH_SHORT).show();
                return new byte[]{};
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // Comprimir la imagen a formato JPEG con una calidad del 80%
            bitmapReducido.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            Log.e("ViewModel", "Error al procesar la imagen", e);
            Toast.makeText(getApplication(), "Error al leer la imagen", Toast.LENGTH_SHORT).show();
            return new byte[]{};
        }
    }

    // Metedo auxiliar para escalar la imagen
    private Bitmap escalarImagenDesdeUri(Uri uri, int reqWidth, int reqHeight) throws IOException {
        InputStream inputStream = null;
        try {
            // 1. Obtener las dimensiones originales de la imagen
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // Esto evita que se cargue el bitmap, solo lee sus dimensiones
            inputStream = getApplication().getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(inputStream, null, options);
            if (inputStream != null) {
                inputStream.close(); // Cerramos el stream para poder abrirlo de nuevo
            }

            // 2. Calcular el tamaño
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // 3. Decodifica la imagen con el tamaño calculado
            options.inJustDecodeBounds = false; // Permitimos que se cargue el bitmap
            inputStream = getApplication().getContentResolver().openInputStream(uri); // Abrimos el stream de nuevo
            return BitmapFactory.decodeStream(inputStream, null, options);

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    // Metodo auxiliar para calcular el tamaño de la imagen
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Dimensiones originales de la imagen
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calcula el mayor valor de inSampleSize que es una potencia de 2 y
            // mantiene tanto la altura como el ancho más grandes que las dimensiones solicitadas.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}