package com.example.inmobiliarialucero.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliarialucero.R;
import com.example.inmobiliarialucero.databinding.FragmentInmuebleBinding;
import com.example.inmobiliarialucero.modelo.Inmueble;

import java.util.ArrayList;

public class InmueblesFragment extends Fragment {

    private FragmentInmuebleBinding binding;
    private InmueblesViewModel vm;
    private InmuebleAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        // Configurar RecyclerView
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        binding.listaInmuebles.setLayoutManager(glm);

        // Adapter vacío inicial
        adapter = new InmuebleAdapter(new ArrayList<>(), getContext(), getLayoutInflater(), inmueble -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.detalleInmuebleFragment, bundle);
        });
        binding.listaInmuebles.setAdapter(adapter);

        // FAB para crear nuevo inmueble
        binding.fabAgregarInmueble.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.crearInmuebleFragment);
        });

        // Observar lista de inmuebles
        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                adapter.setInmuebles(inmuebles);
            }
        });

        vm.obtenerListaInmuebles();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
