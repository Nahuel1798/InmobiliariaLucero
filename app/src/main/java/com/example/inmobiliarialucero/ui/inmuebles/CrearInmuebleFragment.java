package com.example.inmobiliarialucero.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialucero.R;
import com.example.inmobiliarialucero.databinding.FragmentCrearInmuebleBinding;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel vm;
    private FragmentCrearInmuebleBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;


    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        abrirGaleria();
        binding.btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        binding.btnGuardarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarInmueble();
            }
        });
        vm.getUriMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivFotoInmueble.setImageURI(uri);
            }
        });
        return binding.getRoot();
    }

    private void cargarInmueble(){
        String direccion=binding.etDireccion.getText().toString();
        String uso=binding.etUso.getText().toString();
        String tipo=binding.etTipo.getText().toString();
        double latitud=0;
        double longitud=0;
        String precio=binding.etPrecio.getText().toString();
        String ambientes= binding.etAmbientes.getText().toString();
        boolean disponible=binding.cbDisponible.isChecked();
        String superficie=binding.etSuperficie.getText().toString();
        vm.guardarInmueble(direccion,uso,tipo,precio,ambientes,superficie,disponible);
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                vm.recibirFoto(result);
            }
        });
    }

}