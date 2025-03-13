package com.eba.appgastos.utis;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public  class  Constantes {

    private static final Map <String, Character> datos;

    static {
        datos = new HashMap<>();
        datos.put("Gasto",'G');
        datos.put("Ingreso",'I');
        datos.put("Tarjeta",'T');
        datos.put("Efectivo",'E');
        datos.put("Si",'S');
        datos.put("No",'N');
    }

    public static char obtenerChar(String st){
        char value = ' ';
        if (datos.containsKey(st)) value = datos.get(st);
       return value;
    }

    @SuppressLint("SimpleDateFormat")
    public static String obtenerFechaHora(Long fechaMilis){
        Date fecha = new Date(fechaMilis);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy: HH.mm");
        return formato.format(fecha);
    }

    @SuppressLint("SimpleDateFormat")
    public static String obtenerFecha(Long fechaMilis){
        Date fecha = new Date(fechaMilis);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }

    public static long obtenerUltimoDiaDelMes(int year, int month) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(year, month, 1);
        calendario.add(Calendar.MONTH, 1);
        calendario.set(Calendar.DAY_OF_MONTH, 1);
        calendario.add(Calendar.DAY_OF_MONTH, -1);
        // Devolvemos el último día del mes
        return calendario.getTimeInMillis();
    }

    public static String obtenerMonthYear() {
        Calendar calendario = Calendar.getInstance();
        String month = String.valueOf(calendario.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendario.get(Calendar.YEAR));
        return month+year;

    }
}
