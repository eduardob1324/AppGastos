package com.eba.appgastos.ui.ahorros.ahorrosForm;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.eba.appgastos.databinding.FragmentAhorrosFormBinding;
import com.eba.appgastos.dtos.AhorroDto;
import com.eba.appgastos.repositorios.AhorroRepository;

import java.math.BigDecimal;
import java.util.Calendar;


public class AhorrosFormFragment extends Fragment {

    private FragmentAhorrosFormBinding binding;
    private Button btnCancelar, btnGuardar;
    private EditText edtNombre, edtMontoInicial, edtMontoMeta;

    private AhorroDto ahorro;
    private AhorroRepository ahorroRepository;
    private Long fechaMilis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AhorrosFormViewModel notificationsViewModel = new ViewModelProvider(this).get(AhorrosFormViewModel.class);

        binding = FragmentAhorrosFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents();
        btnAdd();
        btnCancelar();


        return root;
    }

    private void initComponents() {
        edtNombre = binding.edtFormAhorrosNombre;
        edtMontoMeta = binding.edtFormAhorroMontoMeta;
        edtMontoInicial = binding.edtFormAhorroMontoInicial;
        btnGuardar = binding.btnAddFormAhorros;
        btnCancelar = binding.btnCancelarformAhorros;
    }


    private void btnCancelar() {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
    }

    private void btnAdd() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCampoTexto(edtNombre)  || validaCampoTexto(edtMontoMeta)) {
                    Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                } else {
                    ahorro = new AhorroDto();
                    ahorro.setNombre(edtNombre.getText().toString());
                    ahorro.setMontoAhorrado(new BigDecimal(0));
                    if (!edtMontoInicial.getText().toString().isBlank()){
                        ahorro.setMontoAhorrado(new BigDecimal(edtMontoInicial.getText().toString()));
                    }
                    ahorro.setMontoMeta(new BigDecimal(edtMontoMeta.getText().toString()));
                    ahorroRepository = new AhorroRepository(getContext());
                    long id = ahorroRepository.insertarAhorro(ahorro);
                    if (id != 0) {
                        Toast.makeText(getActivity(), "Ahorro guardado", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.popBackStack();
                    } else {
                        Toast.makeText(getActivity(), "error al guardar ahorro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validaCampoTexto(EditText text){
        return text.getText().toString().isBlank();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}