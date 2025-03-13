package com.eba.appgastos.ui.gastos;

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
import com.eba.appgastos.adapters.gastos.GastosAdapter;
import com.eba.appgastos.databinding.FragmentGastosBinding;
import com.eba.appgastos.dtos.AhorroDto;
import com.eba.appgastos.dtos.GastoDto;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.repositorios.AhorroRepository;
import com.eba.appgastos.repositorios.GastoFijoRepository;
import com.eba.appgastos.repositorios.GastoRepository;
import java.math.BigDecimal;
import java.util.List;


public class GastosFragment extends Fragment {
    private FragmentGastosBinding binding;

    private TextView tvTotalGastos, tvTotalIngresos, tvTotalRestante;
    private GastosAdapter adapter;
    private GastoRepository gastoRepository;
    private AhorroRepository ahorroRepository;
    private GastoFijoRepository gastoFijoRepository;
    private RecyclerView listGastos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GastosViewModel dashboardViewModel = new ViewModelProvider(this).get(GastosViewModel.class);

        binding = FragmentGastosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents(root);
        List<GastoDto> gastos = gastoRepository.obtenerTodosLosGastos();
        adapter = new GastosAdapter(gastos);
        listGastos.setAdapter(adapter);
        setearTextos(gastos);
        accionesItems(listGastos);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initComponents(View root){
        gastoRepository = new GastoRepository(root.getContext());
        ahorroRepository = new AhorroRepository(root.getContext());
        gastoFijoRepository = new GastoFijoRepository(root.getContext());
        tvTotalGastos = root.findViewById(R.id.tvTotalGastos);
        tvTotalIngresos = root.findViewById(R.id.tvTotalIngresos);
        tvTotalRestante = root.findViewById(R.id.tvTotalRestante);
        listGastos = root.findViewById(R.id.rvGastos);
        listGastos.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void accionesItems(RecyclerView listGastos){
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
        itemTouchHelper.attachToRecyclerView(listGastos);
    }

    private void showDeleteConfirmationDialog(final RecyclerView.ViewHolder viewHolder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
        builder.setTitle("Eliminar item")
                .setMessage("¿Estás seguro de que deseas eliminar este gasto?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(adapter.get(position).getIdAhorro() > 0){
                            AhorroDto ahorroUpdate = ahorroRepository.obtenerAhorroPorId(adapter.get(position).getIdAhorro());
                            ahorroUpdate.setMontoAhorrado(ahorroUpdate.getMontoAhorrado().subtract(adapter.get(position).getMonto()));
                            ahorroRepository.actualizarAhorro(ahorroUpdate);
                        }

                        if(adapter.get(position).getIdGastoFijo() > 0 ){
                            GastoFijoDto gastofijoUpdate = gastoFijoRepository.obtenerGastoFijoPorId(adapter.get(position).getIdGastoFijo());
                            gastofijoUpdate.setMontoAbonado(gastofijoUpdate.getMontoAbonado().subtract(adapter.get(position).getMonto()));
                            gastofijoUpdate.setPagado('N');
                            gastoFijoRepository.actualizarGastoFijo(gastofijoUpdate);
                        }
                        gastoRepository.eliminarGasto(adapter.get(position).getId());
                        adapter.removeItem(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(viewHolder.itemView.getContext(), "Item eliminado", Toast.LENGTH_SHORT).show();
                        List<GastoDto> gastos = gastoRepository.obtenerTodosLosGastos();
                        setearTextos(gastos);
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

    private void setearTextos( List<GastoDto> gastos){
        tvTotalIngresos.setText("$".concat(String.valueOf(sumarIngresos(gastos))));
        tvTotalGastos.setText("$".concat(String.valueOf(sumarGastos(gastos))));
        tvTotalRestante.setText("$".concat(String.valueOf(sumarIngresos(gastos).subtract(sumarGastos(gastos)))));
    }

    private BigDecimal sumarIngresos(List<GastoDto> gastos){
        BigDecimal result = new BigDecimal(0);
        for (GastoDto gasto : gastos){
            if (gasto.getTipo() == 'I'){
                result = result.add(gasto.getMonto());
            }
        }
        return result;
    }

    private BigDecimal sumarGastos(List<GastoDto> gastos){
        BigDecimal result = new BigDecimal(0);
        for (GastoDto gasto : gastos){
            if (gasto.getTipo() != 'I'){
                result = result.add(gasto.getMonto());
            }
        }
        return result;
    }


}