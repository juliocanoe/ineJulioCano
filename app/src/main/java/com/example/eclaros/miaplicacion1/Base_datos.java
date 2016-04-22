package com.example.eclaros.miaplicacion1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;


/**
 * Created by jcano on 18/04/2016.
 */
public class Base_datos  extends SQLiteOpenHelper {
    public static final String NOMBREBD = "dbcarrito.sqlite";
    //Versión de la base de datos
    public Base_datos(Context context, int VERSION)
    {
        super(context, NOMBREBD, null, VERSION);
    }

    //Método utilizado cuando se crea la base de datos.
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table categoria (idcategoria integer primary key autoincrement not null, descategoria varchar, estado integer);");
        db.execSQL("create table productos (idproducto integer primary key autoincrement not null, idcategoria integer not null, producto varchar, cantidad integer, precio double, estado integer);");
        db.execSQL("create table cliente (idcliente integer primary key not null, nombre varchar, apellido varchar, telefono integer, direccion varchar, estado integer);");
        db.execSQL("create table pedido (idpedido integer primary key  autoincrement not null, idcliente integer not null, fecha varchar, estado integer, formapago varchar);");
        db.execSQL("create table pedidoDetalle (idpedidoDetalle integer primary key  autoincrement not null, idcliente integer not null, idproducto integer not null, cantidad integer, precio double);");
        Log.d("Todos las tablas: ", "Se crearon las tablas");
    }

    //Método utilizado cuando se actualiza la base de datos
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}
