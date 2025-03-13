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
    private EditText edtNombre, edtMonto, edtMontoMeta, edtFecha;

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
        selectFecha();
        btnAdd();
        btnCancelar();


        return root;
    }

    private void initComponents() {
        edtNombre = binding.edtFormAhorrosNombre;
        edtMonto = binding.edtFormAhorroMonto;
        edtMontoMeta = binding.edtFormAhorroMontoMeta;
        edtFecha = binding.edtFormAhorrofechaFin;
        btnGuardar = binding.btnAddFormAhorros;
        btnCancelar = binding.btnCancelarformAhorros;
    }

    private void selectFecha() {
        edtFecha.setOnClickListener(v -> {
            // Obtener la fecha actual
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, yearSelected, monthSelected, dayOfMonth) -> {
                        // Establecer la fecha seleccionada
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(yearSelected,
                                monthSelected,
                                dayOfMonth,
                                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                Calendar.getInstance().get(Calendar.MINUTE),
                                0);
                        selectedCalendar.set(Calendar.MILLISECOND, 0);
                        fechaMilis = selectedCalendar.getTimeInMillis();
                        String selectedDate = dayOfMonth + "/" + (monthSelected + 1) + "/" + yearSelected;
                        edtFecha.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });
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
                if (validaCampoTexto(edtNombre) || validaCampoTexto(edtMonto) || validaCampoTexto(edtMontoMeta) || validaCampoTexto(edtFecha)) {
                    Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                } else {
                    ahorro = new AhorroDto();
                    ahorro.setNombre(edtNombre.getText().toString());
                    ahorro.setMontoAhorrado(new BigDecimal(edtMonto.getText().toString()));
                    ahorro.setMontoMeta(new BigDecimal(edtMontoMeta.getText().toString()));
                    ahorro.setFechaInicio(fechaMilis);
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