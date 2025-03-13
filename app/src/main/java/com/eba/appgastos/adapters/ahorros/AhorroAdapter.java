package com.eba.appgastos.adapters.ahorros;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eba.appgastos.R;
import com.eba.appgastos.dtos.AhorroDto;
import com.eba.appgastos.dtos.GastoDto;

import java.util.List;

public class AhorroAdapter extends RecyclerView.Adapter<AhorroViewHolder>{

    private final List<AhorroDto> ahorros;

    public AhorroAdapter(List<AhorroDto> ahorros) {
        this.ahorros = ahorros;
    }

    @NonNull
    @Override
    public AhorroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ahorro, parent, false);
        return new AhorroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AhorroViewHolder holder, int position) {
        holder.render(ahorros.get(position));
    }

    @Override
    public int getItemCount() {
        return ahorros.size();
    }

    public void removeItem(int position) {
        ahorros.remove(position);
        notifyItemRemoved(position);
    }

    public AhorroDto get(long pos){
        int intValue = (int) pos;
        return ahorros.get(intValue);
    }
}
