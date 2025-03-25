package com.eba.appgastos.ui.gastosFijos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.adapters.gastosFijos.GastosFijosAdapter;
import com.eba.appgastos.databinding.FragmentGastosFijosBinding;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.repositorios.GastoFijoRepository;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;


public class
GastosFijosFragment extends Fragment {
    private FragmentGastosFijosBinding binding;
    private TextView tvSerficios;
    private RecyclerView listaGastosFijos;
    private GastosFijosAdapter adapter;
    private GastoFijoRepository gastoFijoRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GastosFijosViewModel notificationsViewModel =
                new ViewModelProvider(this).get(GastosFijosViewModel.class);

        binding = FragmentGastosFijosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents(root);
        resetPagado();
        List<GastoFijoDto> gastoFijos = gastoFijoRepository.obtenerTodosLosGastosFijos();
        adapter = new GastosFijosAdapter(gastoFijos);
        listaGastosFijos.setAdapter(adapter);
        accionesItems(listaGastosFijos);
        tvSerficios.setText("$".concat(String.valueOf(sumarTotalServicios(gastoFijos))));
        return root;
    }


    private void initComponents(View root){
        gastoFijoRepository = new GastoFijoRepository(root.getContext());
        listaGastosFijos = root.findViewById(R.id.rvGastosFijos);
        listaGastosFijos.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        tvSerficios = root.findViewById(R.id.tvTotalServicios);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void accionesItems(RecyclerView listGastosFijos){
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    showDeleteConfirmationDialog(viewHolder, viewHolder.getAdapterPosition());
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(listGastosFijos);
    }

    private void showDeleteConfirmationDialog(final RecyclerView.ViewHolder viewHolder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
        builder.setTitle("Eliminar item")
                .setMessage("¿Estás seguro de que deseas eliminar este gasto fijo?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gastoFijoRepository.eliminarGasto(adapter.get(position).getId());
                        adapter.removeItem(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(viewHolder.itemView.getContext(), "Item eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                })
                .create()
                .show();
    }
    public void resetPagado() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (dayOfMonth == 1) {
            gastoFijoRepository.actualizarPagado(month, year);
        }
    }

    private BigDecimal sumarTotalServicios(List<GastoFijoDto> gastosFijos){
        BigDecimal result = new BigDecimal(0);
        for (GastoFijoDto gastofijo : gastosFijos){
            if (gastofijo.getIsServicio() == 'S'){
                result = result.add(gastofijo.getMontoAbonado());
            }
        }
        return result;
    }
}