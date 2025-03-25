package com.eba.appgastos.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gastosDB.db";
    private static final int DATABASE_VERSION = 3;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE = "CREATE TABLE gastos ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nombre TEXT, "
            + "monto TEXT, "
            + "fecha INTEGER, "
            + "month_year INTEGER,"
            + "tipo CHAR(1) NOT NULL, "
            + "tipoPago CHAR(1) NOT NULL, "
            + "esAhorro CHAR(1), "
            + "idAhorro INTEGER, "
            + "idGastoFijo INTEGER, "
            + "FOREIGN KEY(idAhorro) REFERENCES ahorros(id), "
            + "FOREIGN KEY(idGastoFijo) REFERENCES gastos_fijos(id)"
            + ");";

    private static final String CREATE_TABLE_AHORROS = "CREATE TABLE gasto_fijos ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nombre TEXT, "
            + "monto TEXT, "
            + "montoAbonado TEXT, "
            + "is_servicio CHAR(1) , "
            + "pagado CHAR(1), "
            + "fecha_limite_pago INTEGER "
            + ");";

    private static final String CREATE_TABLE_GASTO_FIJO = "CREATE TABLE ahorros ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nombre TEXT, "
            + "monto_ahorrado TEXT, "
            + "monto_meta TEXT "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_AHORROS);
        db.execSQL(CREATE_TABLE_GASTO_FIJO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS gastos");
        db.execSQL("DROP TABLE IF EXISTS ahorros");
        db.execSQL("DROP TABLE IF EXISTS gasto_fijos");
        onCreate(db);
    }

}
