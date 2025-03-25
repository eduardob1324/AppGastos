package com.eba.appgastos.ui.gastos.gastosForm;
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
import com.eba.appgastos.databinding.FragmentGastosFormBinding;
import com.eba.appgastos.dtos.AhorroDto;
import com.eba.appgastos.dtos.GastoDto;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.repositorios.AhorroRepository;
import com.eba.appgastos.repositorios.GastoFijoRepository;
import com.eba.appgastos.repositorios.GastoRepository;
import com.eba.appgastos.utis.Constantes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GastosFormFragment extends Fragment {

    private Spinner spFormGastosTipo, spFormGastosTipo_pago, spFormGastosEsAhorro, spFormGastosIdAhorro, spFormGastosIdGasto;
    private EditText edtNombre, edtMonto;
    private Button btnCancelar, btnGuardar;
    private FragmentGastosFormBinding binding;
    private int spValid = 0;
    private GastoDto gasto;
    private GastoRepository gastoRepository;
    private AhorroRepository ahorroRepository;
    private GastoFijoRepository gastoFijoRepository;
    Map<String, AhorroDto> mapAhorros;
    Map<String, GastoFijoDto> mapGastosFijos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       GastosFormViewModel notificationsViewModel =  new ViewModelProvider(this).get(GastosFormViewModel.class);

        binding = FragmentGastosFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents();
        setupSpinners();
        bntCancelarAddOnclick();
        bntGuardarAddOnclick();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initComponents(){
        edtNombre = binding.edtFormaGastosNombre;
        edtMonto = binding.edtFormaGastosMonto;
        spFormGastosTipo = binding.spFormGastosTipo;
        spFormGastosTipo_pago = binding.spFormGastosTipoPago;
        spFormGastosEsAhorro = binding.spFormGastosEsAhorro;
        spFormGastosIdAhorro = binding.spFormGastosIdAhorro;
        spFormGastosIdGasto = binding.spFormGastosIdGasto;
        btnCancelar = binding.btnCancelarformGastos;
        btnGuardar = binding.btnAddFormGastos;
        gasto = new GastoDto();
        gastoRepository = new GastoRepository(getContext());
        ahorroRepository = new AhorroRepository(getContext());
        gastoFijoRepository = new GastoFijoRepository(getContext());
    }

    private void bntCancelarAddOnclick(){
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.popBackStack();
            }
        });
    }

    private void bntGuardarAddOnclick(){
        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(validaCampoTexto(edtNombre) || validaCampoTexto(edtMonto) || spValid <= 3 ){
                    System.out.println(spValid);
                    Toast.makeText(getActivity(), "No puede haber campos vacios", Toast.LENGTH_SHORT).show();
                }else{
                    gasto.setNombre(edtNombre.getText().toString());
                    gasto.setMonto(new BigDecimal(edtMonto.getText().toString()));
                    gasto.setFecha(System.currentTimeMillis());
                    gasto.setMonthYear(Integer.parseInt(Constantes.obtenerMonthYear()));
                    if(gasto.getIdAhorro() != null){
                        AhorroDto ahorro = ahorroRepository.obtenerAhorroPorId(gasto.getIdAhorro());
                        ahorro.setMontoAhorrado(ahorro.getMontoAhorrado().add(gasto.getMonto()));
                        ahorroRepository.actualizarAhorro(ahorro);
                    }
                    if(gasto.getIdGastoFijo() != null){
                        actualizarMontoAbonadoGastoFijo();
                    }
                    long id = gastoRepository.insertarGasto(gasto);
                    if(id != 0){
                        Toast.makeText(getActivity(), "Gasto guardado", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(v);
                        navController.popBackStack();
                    }else{
                        Toast.makeText(getActivity(), "error al guardar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validaCampoTexto(EditText text){
        return text.getText().toString().isBlank();
    }
    private void setupSpinners() {

        String[] tiposGasto = getResources().getStringArray(R.array.options_spiner_form_gastos_tipo);
        ArrayAdapter<String> adapterTiposGasto = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tiposGasto);
        adapterTiposGasto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosTipo.setAdapter(adapterTiposGasto);
        spFormGastosTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione tipo:")){
                    gasto.setTipo(parent.getItemAtPosition(position).toString().charAt(0));
                    if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Gasto Fijo")){
                        spFormGastosEsAhorro.setVisibility(View.VISIBLE);
                        spValid++;
                    }else{
                        spFormGastosEsAhorro.setVisibility(View.GONE);
                        spValid+=3;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        String[] tiposPago = getResources().getStringArray(R.array.options_spiner_form_gastos_tipo_pago);
        ArrayAdapter<String> adapterTiposPago = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tiposPago);
        adapterTiposPago.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosTipo_pago.setAdapter(adapterTiposPago);
        spFormGastosTipo_pago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione tipo_pago:")){
                    gasto.setTipoPago(parent.getItemAtPosition(position).toString().charAt(0));
                    spValid ++;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] ahorroOptions = getResources().getStringArray(R.array.options_spiner_form_gastos_esAhorro);
        ArrayAdapter<String> adapterAhorro = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ahorroOptions);
        adapterAhorro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosEsAhorro.setAdapter(adapterAhorro);
        spFormGastosEsAhorro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione es ahorro:")){
                    gasto.setEsAhorro(parent.getItemAtPosition(position).toString().charAt(0));
                    spValid ++;
                    ocultarSp(parent.getItemAtPosition(position).toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        mapAhorros = ahorroRepository.obtenerTodosLosAhorros().stream()
                .collect(Collectors.toMap(AhorroDto::getNombre, objeto -> objeto));
        List<String> clavesAhorros = mapAhorros.keySet().stream().collect(Collectors.toList());
        clavesAhorros.add(0, "Seleccione Ahorro:");
        ArrayAdapter<String> adapterIdAhorro = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, clavesAhorros);
        adapterIdAhorro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosIdAhorro.setAdapter(adapterIdAhorro);
        spFormGastosIdAhorro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione Ahorro:")){
                    AhorroDto ahorroDto = mapAhorros.get(parent.getItemAtPosition(position).toString());
                    if (ahorroDto != null){
                        gasto.setIdAhorro(ahorroDto.getId());
                    }
                    spValid++;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mapGastosFijos = gastoFijoRepository.obtenerTodosLosGastosFijosNoPagados().stream()
                .collect(Collectors.toMap(GastoFijoDto::getNombre, objeto -> objeto));
        List<String> clavesGastosFijos = mapGastosFijos.keySet().stream().collect(Collectors.toList());
        clavesGastosFijos.add(0, "Seleccione gasto fijo:");
        ArrayAdapter<String> adapterIdGasto = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, clavesGastosFijos);
        adapterIdGasto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFormGastosIdGasto.setAdapter(adapterIdGasto);
        spFormGastosIdGasto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getItemAtPosition(position).toString().equalsIgnoreCase("Seleccione gasto fijo:")){
                    GastoFijoDto gastoFijoDto = mapGastosFijos.get(parent.getItemAtPosition(position).toString());
                    if (gastoFijoDto != null){
                        System.out.println(gastoFijoDto.getId());
                        gasto.setIdGastoFijo(gastoFijoDto.getId());
                    }
                    spValid ++;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void ocultarSp(String op){
        if ("No".equals(op)) {
            spFormGastosIdGasto.setVisibility(View.VISIBLE);
            spFormGastosIdAhorro.setVisibility(View.GONE);
        }
        if ("Si".equals(op)) {
            spFormGastosIdAhorro.setVisibility(View.VISIBLE);
            spFormGastosIdGasto.setVisibility(View.GONE);
        }
    }

    private void actualizarMontoAbonadoGastoFijo(){
        GastoFijoDto gastofijo = gastoFijoRepository.obtenerGastoFijoPorId(gasto.getIdGastoFijo());
        gastofijo.setMontoAbonado(gastofijo.getMontoAbonado().add(gasto.getMonto()));
        if(gastofijo.getMonto().subtract(gastofijo.getMontoAbonado()).compareTo(BigDecimal.ZERO) == 0){
            gastofijo.setPagado('S');
        }
        gastoFijoRepository.actualizarGastoFijo(gastofijo);
    }


}