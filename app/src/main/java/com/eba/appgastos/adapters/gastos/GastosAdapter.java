package com.eba.appgastos.adapters.gastos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.dtos.GastoDto;

import java.util.List;

public class GastosAdapter extends RecyclerView.Adapter<GastoViewHolder> {

    private final List<GastoDto> gastos;
    public GastosAdapter(List<GastoDto> gastos) {
        this.gastos = gastos;
    }

    @NonNull
    @Override
    public GastoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gasto, parent, false);
        return new GastoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoViewHolder holder, int position) {
        holder.render(gastos.get(position));
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    public void removeItem(int position) {
        gastos.remove(position);
        notifyItemRemoved(position);
    }

    public GastoDto get(long pos){
        int intValue = (int) pos;
        return gastos.get(intValue);
    }
}
