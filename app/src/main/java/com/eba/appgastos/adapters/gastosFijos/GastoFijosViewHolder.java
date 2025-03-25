package com.eba.appgastos.adapters.gastosFijos;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.utis.Constantes;

public class GastoFijosViewHolder extends RecyclerView.ViewHolder{
    private final TextView tvCardNombre, tvCardMonto, tvCardFecha, tvcardMontoAhorrado;
    private final CardView cardGastosFijos;


    public GastoFijosViewHolder(View view) {
        super(view);
        tvCardNombre = view.findViewById(R.id.tvCardGastoFijoNombre);
        tvCardMonto = view.findViewById(R.id.tvCardGastoFijoMonto);
        tvCardFecha = view.findViewById(R.id.tvCardGastoFijoFecha);
        tvcardMontoAhorrado = view.findViewById(R.id.tvCardGastoFijoMontoAbonado);
        cardGastosFijos = view.findViewById(R.id.cardGastoFijo);
    }

    @SuppressLint("SetTextI18n")
    public void render(GastoFijoDto gastoFijoDto){
        tvCardNombre.setText(gastoFijoDto.getNombre());
        tvCardMonto.setText("$" + gastoFijoDto.getMontoAbonado().toString());
        tvcardMontoAhorrado.setText("$" + gastoFijoDto.getMonto().toString());
        tvCardFecha.setText("Fecha limite pago: " + Constantes.obtenerFecha(gastoFijoDto.getFechaLimitePago()));
        cardGastosFijos.setCardBackgroundColor( Color.parseColor("#A50005"));
        if (gastoFijoDto.getPagado() =='S'){
            cardGastosFijos.setCardBackgroundColor(Color.parseColor("#006507"));
        }
    }

}
