package com.eba.appgastos.adapters.gastos;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.eba.appgastos.R;
import com.eba.appgastos.dtos.GastoDto;
import com.eba.appgastos.utis.Constantes;

public class GastoViewHolder extends RecyclerView.ViewHolder{

    private final TextView tvCardNombre, tvCardMonto, tvCardFecha;
    private final CardView cardGastos;
    private final ImageView imgCard;


    public GastoViewHolder(View view) {
        super(view);

        tvCardNombre = view.findViewById(R.id.tvCardNombre);
        tvCardMonto = view.findViewById(R.id.tvCardMonto);
        tvCardFecha = view.findViewById(R.id.tvCardFecha);
        cardGastos = view.findViewById(R.id.cardGastos);
        imgCard = view.findViewById(R.id.imgCardGastos);
    }

    @SuppressLint("SetTextI18n")
    public void render(GastoDto gastoDto){
        tvCardNombre.setText(gastoDto.getNombre());
        tvCardMonto.setText("$" + gastoDto.getMonto().toString());
        tvCardFecha.setText(Constantes.obtenerFechaHora(gastoDto.getFecha()));
        //cardGastos.setCardBackgroundColor( Color.parseColor("#E94158"));
        cardGastos.setCardBackgroundColor( Color.parseColor("#A50005"));
        imgCard.setImageResource(R.drawable.pay_by_cash);
        if (gastoDto.getTipo() == 'I'){
            cardGastos.setCardBackgroundColor(Color.parseColor("#006507"));
        }
        if(gastoDto.getTipoPago() == 'T' ){
            imgCard.setImageResource(R.drawable.pay_by_credit_card);
        }
    }
}
