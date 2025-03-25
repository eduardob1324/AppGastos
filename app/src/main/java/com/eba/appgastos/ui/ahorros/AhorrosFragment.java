package com.eba.appgastos.ui.ahorros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.adapters.ahorros.AhorroAdapter;
import com.eba.appgastos.databinding.FragmentAhorrosBinding;
import com.eba.appgastos.dtos.AhorroDto;
import com.eba.appgastos.repositorios.AhorroRepository;

import java.math.BigDecimal;
import java.util.List;

public class AhorrosFragment extends Fragment {

    private FragmentAhorrosBinding binding;
    private AhorroAdapter adapter;
    private AhorroRepository ahorroRepository;
    private TextView tvTotalAhorros;
    private RecyclerView listAhorros;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AhorrosViewModel homeViewModel =
                new ViewModelProvider(this).get(AhorrosViewModel.class);
        binding = FragmentAhorrosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initComponents(root);
        List<AhorroDto> ahorros = ahorroRepository.obtenerTodosLosAhorros();
        adapter = new AhorroAdapter(ahorros);
        listAhorros.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        listAhorros.setAdapter(adapter);
        accionesItems(listAhorros);
        tvTotalAhorros.setText("$".concat(String.valueOf(sumarTotalAhorros(ahorros))));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initComponents(View root){
        ahorroRepository = new AhorroRepository(root.getContext());
        listAhorros = root.findViewById(R.id.rvAhorros);
        tvTotalAhorros = root.findViewById(R.id.tvTotalAhorros);

    }

    private void accionesItems(RecyclerView listAhorros){
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
                }else {
                    AhorroDto ahorroUpdate = adapter.get(viewHolder.getAdapterPosition());
                    if (ahorroUpdate.getMontoAhorrado().compareTo(BigDecimal.ZERO) > 0){
                        showEdit( viewHolder.getAdapterPosition());
                    }else {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        Toast.makeText(getActivity(), "No se puede actualizar el ahorro", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        itemTouchHelper.attachToRecyclerView(listAhorros);
    }

    private void showDeleteConfirmationDialog(final RecyclerView.ViewHolder viewHolder, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.itemView.getContext());
        builder.setTitle("Eliminar item")
                .setMessage("¿Estás seguro de que deseas eliminar este Ahorro?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ahorroRepository.eliminarAhorro(adapter.get(position).getId());
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

    private void showEdit( int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ingrese algo");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                AhorroDto ahorroUpdate = adapter.get(position);
                if(!input.getText().toString().isBlank()){
                    ahorroUpdate.setMontoAhorrado(ahorroUpdate.getMontoAhorrado().subtract(new BigDecimal(input.getText().toString().trim())));
                    ahorroRepository.actualizarAhorro(ahorroUpdate);
                    adapter.notifyItemChanged(position);
                    Toast.makeText(getActivity(), "Ahorro actualizado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);
        adapter.notifyItemChanged(position);
        builder.show();
    }

    private BigDecimal sumarTotalAhorros(List<AhorroDto> ahorros){
        BigDecimal result = new BigDecimal(0);
        for (AhorroDto ahorro : ahorros){
            result = result.add(ahorro.getMontoAhorrado());

        }
        return result;
    }
}