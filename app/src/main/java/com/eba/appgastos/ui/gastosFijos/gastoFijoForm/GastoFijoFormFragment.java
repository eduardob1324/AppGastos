package com.eba.appgastos.ui.gastosFijos.gastoFijoForm;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.eba.appgastos.R;
import com.eba.appgastos.databinding.FragmentGastoFijoFormBinding;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.repositorios.GastoFijoRepository;

import java.math.BigDecimal;
import java.util.Calendar;


public class GastoFijoFormFragment extends Fragment {

    private FragmentGastoFijoFormBinding binding;
    private EditText edtNombre, edtMonto, edtFecha;
    private Button btnCancelar, btnGuardar;
    private Spinner spFormGastosFijosEsServicio;
    private Long fechaMilis;
    private GastoFijoDto gastoFijo;
    private GastoFijoRepository gastoFijoRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GastoFijoFormViewModel notificationsViewModel =  new ViewModelProvider(this).get(GastoFijoFormViewModel.class);

        binding = FragmentGastoFijoFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents();
        selectFecha();
        setupSpinners();
        btnAdd();
        btnCancelar();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initComponents(){
        edtNombre = binding.edtFormGastosFijosNombre;
        edtMonto = binding.edtFormGastosFijosMonto;
        edtFecha = binding.edtFormGastosFijosFecha;
        spFormGastosFijosEsServicio = binding.spFormGastosFijosEsServicio;
        btnGuardar = binding.btnAddFormGastosFijos;
        btnCancelar = binding.btnCancelarFormGastosFijos;
        gastoFijo = new GastoFijoDto();
        gastoFijoRepository = new GastoFijoRepository(getContext());
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

    private void btnAdd() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaCampoTexto(edtNombre) || validaCampoTexto(edtMonto) || validaCampoTexto(edtMonto) || validaCampoTexto(edtFecha) || gastoFijo.getIsServicio() ==' ') {
                    Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                } else {
                    gastoFijo.setNombre(edtNombre.getText().toString());
                    gastoFijo.setMonto(new BigDecimal(edtMonto.getText().toString()));
                    gastoFijo.setMontoAbonado(new BigDecimal(0));
                    gastoFijo.setPagado('N');
                    gastoFijo.setFechaLimitePago(fechaMilis);
                    long id = gastoFijoRepository.insertarGastoFijo(gastoFijo);
                    if (id != 0) {
                        Toast.makeText(getActivity(), "Gasto fijo guardado", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.popBackStack();
                    } else {
                        Toast.makeText(getActivity(), "error al guardar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validaCampoTexto(EditText text){
        return text.getText().toString().isBlank();
    }

    private void btnCancelar(){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
    }

    private void setupSpinners() {
        String[] mespago = getResources().getStringArray(R.array.options_spiner_form_gastos_fijos_es_servicio);
        ArrayAdapter<String> adapterMesPago = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mespago);
        adapterMesPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosFijosEsServicio.setAdapter(adapterMesPago);
        spFormGastosFijosEsServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gastoFijo.setIsServicio(' ');
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione es servicio:")){
                    gastoFijo.setIsServicio(parent.getItemAtPosition(position).toString().charAt(0));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



}