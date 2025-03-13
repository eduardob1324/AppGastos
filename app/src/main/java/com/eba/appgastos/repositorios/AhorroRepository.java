package com.eba.appgastos.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.eba.appgastos.config.DbHelper;
import com.eba.appgastos.dtos.AhorroDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AhorroRepository {
    private DbHelper dbHelper;
    public AhorroRepository(Context context){
        this.dbHelper = new DbHelper(context);
    }

    public long insertarAhorro(AhorroDto ahorroDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", ahorroDto.getNombre());
        values.put("fecha_inicio", ahorroDto.getFechaInicio());
        values.put("monto_ahorrado", ahorroDto.getMontoAhorrado().toString());
        values.put("monto_meta", ahorroDto.getMontoMeta().toString());
        long id = db.insert("ahorros", null, values);
        db.close();
        return id;
    }

    public List<AhorroDto> obtenerTodosLosAhorros() {
        List<AhorroDto> ahorros = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM ahorros";
        Cursor cursor = db.rawQuery(query, null);
        AhorroDto ahorro = null;

        if (cursor.moveToFirst()) {
            do {
                ahorro = new AhorroDto();
                ahorro.setId(cursor.getLong(0));
                ahorro.setNombre(cursor.getString(1));
                ahorro.setFechaInicio(cursor.getLong(2));
                ahorro.setMontoAhorrado(new BigDecimal(cursor.getString(3)));
                ahorro.setMontoMeta(new BigDecimal(cursor.getString(4)));
                ahorros.add(ahorro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ahorros;
    }

    public AhorroDto obtenerAhorroPorId(Long id) {
        AhorroDto ahorro = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM ahorros WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            ahorro = new AhorroDto();
            ahorro.setId(cursor.getLong(0));
            ahorro.setNombre(cursor.getString(1));
            ahorro.setFechaInicio(cursor.getLong(2));
            ahorro.setMontoAhorrado(new BigDecimal(cursor.getString(3)));
            ahorro.setMontoMeta(new BigDecimal(cursor.getString(4)));
        }
        cursor.close();
        db.close();
        return ahorro;
    }

    public boolean actualizarAhorro(AhorroDto ahorro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("monto_ahorrado", ahorro.getMontoAhorrado().doubleValue());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(ahorro.getId())};
        int rowsAffected = db.update("ahorros", values, whereClause, whereArgs);
        db.close();
        return rowsAffected > 0;
    }


    public void eliminarAhorro(Long idAhorro) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "DELETE FROM ahorros WHERE id = ?";
        db.execSQL(query, new Object[]{idAhorro});
        db.close();
    }
}
