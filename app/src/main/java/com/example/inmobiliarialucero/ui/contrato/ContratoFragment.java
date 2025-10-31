package com.example.inmobiliarialucero.ui.contrato;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inmobiliarialucero.R;
import com.example.inmobiliarialucero.databinding.FragmentContratoBinding;
import com.example.inmobiliarialucero.ui.Inicio.InicioViewModel;

import java.util.ArrayList;


public class ContratoFragment extends Fragment {
    private FragmentContratoBinding binding;
    private ContratoViewModel vm;
    private ContratoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);

        // Condigurar RecyclerView
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        binding.listaInmuebles.setLayoutManager(glm);

        // Adapter vacío inicial
        adapter = new ContratoAdapter(new ArrayList<>(), getContext(), getLayoutInflater(), inmueble -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmueble);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.detalleInmuebleFragment, bundle);
        });
        binding.listaInmuebles.setAdapter(adapter);

        // Observar lista de inmuebles
        vm.getListaInmuebles().observe(getViewLifecycleOwner(), inmuebles -> {
            if (inmuebles != null) {
                adapter.setInmuebles(inmuebles);
            }
        });

        vm.ObtenerInmueblesContrato();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}