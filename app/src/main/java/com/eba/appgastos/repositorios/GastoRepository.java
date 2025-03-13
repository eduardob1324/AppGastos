package com.eba.appgastos.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eba.appgastos.config.DbHelper;
import com.eba.appgastos.dtos.GastoDto;
import com.eba.appgastos.utis.Constantes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GastoRepository {
    private DbHelper dbHelper;
    public GastoRepository(Context context){
        this.dbHelper = new DbHelper(context);
    }

    public long insertarGasto(GastoDto gastoDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", gastoDto.getNombre());
        values.put("monto", gastoDto.getMonto().toString());
        values.put("fecha", gastoDto.getFecha());
        values.put("month_year", gastoDto.getMonthYear());
        values.put("tipo", Character.toString(gastoDto.getTipo()));
        values.put("tipoPago", Character.toString(gastoDto.getTipoPago()));
        values.put("esAhorro", Character.toString(gastoDto.getEsAhorro()));
        values.put("idAhorro", gastoDto.getIdAhorro());
        values.put("idGastoFijo", gastoDto.getIdGastoFijo());

        long id = db.insert("gastos", null, values);
        db.close();
        return id;
    }

    public List<GastoDto> obtenerTodosLosGastos() {
        List<GastoDto> gastos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM gastos WHERE month_year = ?";
        Cursor cursor = db.rawQuery(query, new String[]{Constantes.obtenerMonthYear()});
       // Cursor cursor = db.rawQuery(query, null);
        GastoDto gasto = null;
        if (cursor.moveToFirst()) {
            do {
                gasto = new GastoDto();
                gasto.setId(cursor.getLong(0));
                gasto.setNombre(cursor.getString(1));
                gasto.setMonto(new BigDecimal(cursor.getString(2)));
                gasto.setFecha(cursor.getLong(3));
                gasto.setMonthYear(cursor.getInt(4));
                gasto.setTipo(cursor.getString(5).charAt(0));
                gasto.setTipoPago(cursor.getString(6).charAt(0));
                gasto.setEsAhorro(cursor.getString(7).charAt(0));
                gasto.setIdAhorro(cursor.getLong(8));
                gasto.setIdGastoFijo(cursor.getLong(9));
                gastos.add(gasto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gastos;
    }

    public void eliminarGasto(Long idGasto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "DELETE FROM gastos WHERE id = ?";
        db.execSQL(query, new Object[]{idGasto});
        db.close();
    }


}
