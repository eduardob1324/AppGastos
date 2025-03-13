package com.eba.appgastos.adapters.gastosFijos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.dtos.GastoFijoDto;

import java.util.List;

public class GastosFijosAdapter extends RecyclerView.Adapter<GastoFijosViewHolder> {
    private final List<GastoFijoDto> gastosfijos;
    public GastosFijosAdapter(List<GastoFijoDto> gastosfijos) {
        this.gastosfijos = gastosfijos;
    }


    @NonNull
    @Override
    public GastoFijosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gasto_fijo, parent, false);
        return new GastoFijosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GastoFijosViewHolder holder, int position) {
        holder.render(gastosfijos.get(position));
    }

    @Override
    public int getItemCount() {
        return gastosfijos.size();
    }

    public void removeItem(int position) {
        gastosfijos.remove(position);
        notifyItemRemoved(position);
    }

    public GastoFijoDto get(long pos){
        int intValue = (int) pos;
        return gastosfijos.get(intValue);
    }
}
