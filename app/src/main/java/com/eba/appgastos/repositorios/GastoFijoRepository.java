package com.eba.appgastos.repositorios;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.eba.appgastos.config.DbHelper;
import com.eba.appgastos.dtos.GastoFijoDto;
import com.eba.appgastos.utis.Constantes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GastoFijoRepository {

    private DbHelper dbHelper;

    public GastoFijoRepository(Context context){
        this.dbHelper = new DbHelper(context);
    }

    public long insertarGastoFijo(GastoFijoDto gastoFijoDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", gastoFijoDto.getNombre());
        values.put("monto", String.valueOf(gastoFijoDto.getMonto()));
        values.put("montoAbonado", String.valueOf(gastoFijoDto.getMontoAbonado()));
        values.put("is_servicio", Character.toString(gastoFijoDto.getIsServicio()));
        values.put("pagado", Character.toString(gastoFijoDto.getPagado()));
        values.put("fecha_limite_pago", gastoFijoDto.getFechaLimitePago());
        long id = db.insert("gasto_fijos", null, values);
        db.close();
        return id;
    }

    public List<GastoFijoDto> obtenerTodosLosGastosFijos() {
        List<GastoFijoDto> gastosFijos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM gasto_fijos";
        Cursor cursor = db.rawQuery(query, null);
        GastoFijoDto gastoFijo = null;

        if (cursor.moveToFirst()) {
            do {
                gastoFijo = new GastoFijoDto();
                gastoFijo.setId(cursor.getLong(0));
                gastoFijo.setNombre(cursor.getString(1));
                gastoFijo.setMonto(new BigDecimal(cursor.getString(2)));
                gastoFijo.setMontoAbonado(new BigDecimal(cursor.getString(3)));
                gastoFijo.setIsServicio(cursor.getString(4).charAt(0));
                gastoFijo.setPagado(cursor.getString(5).charAt(0));
                gastoFijo.setFechaLimitePago(cursor.getLong(6));
                gastosFijos.add(gastoFijo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gastosFijos;
    }

    public List<GastoFijoDto> obtenerTodosLosGastosFijosNoPagados() {
        List<GastoFijoDto> gastosFijos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM gasto_fijos WHERE pagado = 'N'";
        Cursor cursor = db.rawQuery(query, null);
        GastoFijoDto gastoFijo = null;

        if (cursor.moveToFirst()) {
            do {
                gastoFijo = new GastoFijoDto();
                gastoFijo.setId(cursor.getLong(0));
                gastoFijo.setNombre(cursor.getString(1));
                gastoFijo.setMonto(new BigDecimal(cursor.getString(2)));
                gastoFijo.setMontoAbonado(new BigDecimal(cursor.getString(3)));
                gastoFijo.setIsServicio(cursor.getString(4).charAt(0));
                gastoFijo.setPagado(cursor.getString(5).charAt(0));
                gastoFijo.setFechaLimitePago(cursor.getLong(6));
                gastosFijos.add(gastoFijo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gastosFijos;
    }

    public void actualizarGastoFijo(GastoFijoDto gastoFijoDto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("montoAbonado", String.valueOf(gastoFijoDto.getMontoAbonado()));
        values.put("pagado", Character.toString(gastoFijoDto.getPagado()));
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(gastoFijoDto.getId())};
        int rowsAffected = db.update("gasto_fijos", values, whereClause, whereArgs);
        db.close();
    }

    public GastoFijoDto obtenerGastoFijoPorId(Long id) {
        GastoFijoDto gastoFijo = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM gasto_fijos WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            gastoFijo = new GastoFijoDto();
            gastoFijo.setId(cursor.getLong(0));
            gastoFijo.setNombre(cursor.getString(1));
            gastoFijo.setMonto(new BigDecimal(cursor.getString(2)));
            gastoFijo.setMontoAbonado(new BigDecimal(cursor.getString(3)));
            gastoFijo.setIsServicio(cursor.getString(4).charAt(0));
            gastoFijo.setPagado(cursor.getString(5).charAt(0));
            gastoFijo.setFechaLimitePago(cursor.getLong(6));
        }
        cursor.close();
        db.close();
        return gastoFijo;
    }

    public void eliminarGasto(Long idGastoFijo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "DELETE FROM gasto_fijos WHERE id = ?";
        db.execSQL(query, new Object[]{idGastoFijo});
        db.close();
    }

    public void actualizarPagado(int month, int year) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Crear los valores para el campo 'pagado' a 'N'
        ContentValues values = new ContentValues();
        values.put("pagado", "N"); // Establecer el valor 'N' en la columna 'pagado'
        values.put("fecha_limite_pago" , Constantes.obtenerUltimoDiaDelMes(year, month));
        values.put("montoAbonado", String.valueOf(new BigDecimal(0)));
        // Actualizar todos los registros que no estén ya marcados como pagados
        String whereClause = "pagado != ?";
        String[] whereArgs = {"N"};
        // Ejecutar la actualización
        db.update("gasto_fijos", values, whereClause, whereArgs);
        db.close();
    }
}
