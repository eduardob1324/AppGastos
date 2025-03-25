package com.eba.appgastos.adapters.ahorros;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.dtos.AhorroDto;
public class AhorroViewHolder extends RecyclerView.ViewHolder{

    private final TextView tvCardNombreAhorro, tvCardMontoAhorro, tvCardMontoMetaAhorro, tvCardMontoFaltanteAhorro;
    private final CardView cardAhorros;

    public AhorroViewHolder(@NonNull View view) {
        super(view);

        tvCardNombreAhorro = view.findViewById(R.id.tvCardNombreAhorro);
        tvCardMontoAhorro = view.findViewById(R.id.tvCardMontoAhorro);
        tvCardMontoMetaAhorro = view.findViewById(R.id.tvCardMontoMetaAhorro);
        tvCardMontoFaltanteAhorro = view.findViewById(R.id.tvCardMontoFaltanteAhorro);
        cardAhorros = view.findViewById(R.id.cardAhorro);
    }

    @SuppressLint("SetTextI18n")
    public void render(AhorroDto ahorroDto){
        tvCardNombreAhorro.setText(ahorroDto.getNombre());
        tvCardMontoAhorro.setText("$"+ ahorroDto.getMontoAhorrado().toString());
        tvCardMontoMetaAhorro.setText("$"+ ahorroDto.getMontoMeta().toString());
        tvCardMontoFaltanteAhorro.setText("$"+ahorroDto.getMontoMeta().subtract(ahorroDto.getMontoAhorrado()).toString());
        cardAhorros.setCardBackgroundColor( Color.parseColor("#006507"));
    }
}
