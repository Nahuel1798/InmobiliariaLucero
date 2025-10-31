package com.example.inmobiliarialucero.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliarialucero.R;
import com.example.inmobiliarialucero.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliarialucero.modelo.Inmueble;
import com.example.inmobiliarialucero.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;
    private Inmueble inmueble;


    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inicializamos el binding y el ViewModel
        inmueble = new Inmueble();
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        // Observamos los datos del inmueble
        View view = binding.getRoot();
        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                    binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                    binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                    binding.etDireccion.setText(inmueble.getDireccion());
                    binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
                    binding.etUso.setText(inmueble.getUso());
                    binding.etTipo.setText(inmueble.getTipo());
                    binding.cbDisponible.setChecked(inmueble.isDisponible());

                    // Cargar imagen con Glide
                    Glide.with(requireContext())
                            .load(ApiClient.BASE_URL + inmueble.getImagen())
                            .placeholder(null)
                            .error("error")
                            .into(binding.ivFotoInmueble);
            }
        });

        binding.btnGuardarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mv.actualizarInmueble(inmueble);
            }
        });

        mv.recuperarInmueble(getArguments());
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
