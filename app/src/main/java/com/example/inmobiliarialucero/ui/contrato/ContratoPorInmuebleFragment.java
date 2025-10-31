package com.example.inmobiliarialucero.ui.contrato;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliarialucero.R;

public class ContratoPorInmuebleFragment extends Fragment {

    private ContratoPorInmuebleViewModel mViewModel;

    public static ContratoPorInmuebleFragment newInstance() {
        return new ContratoPorInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contrato_por_inmueble, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ContratoPorInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}