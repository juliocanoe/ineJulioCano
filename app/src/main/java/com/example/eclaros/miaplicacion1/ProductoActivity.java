package com.example.eclaros.miaplicacion1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ProductoActivity extends AppCompatActivity {

    private TextView idproducto;
    private EditText producto;
    private EditText categoria;
    private EditText precio;
    private EditText cantidad;
    private Button guardar, actualizar, atras, cancelar;
    private TableLayout tabla;
    private TableRow fila;
    TableRow.LayoutParams layoutFila;
    public  String kEstado;

    private SQLiteDatabase db;
    public static final int VERSION = 1;
    private Context context;
    private String consulta, llave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        Base_datos crearBD = new Base_datos(context,VERSION);
        db = crearBD.getWritableDatabase();

        idproducto=(TextView)findViewById(R.id.id_categoria);
        producto=(EditText)findViewById(R.id.producto);
        categoria=(EditText)findViewById(R.id.categoria);
        precio=(EditText)findViewById(R.id.precio);
        cantidad=(EditText)findViewById(R.id.cantidad);
        guardar=(Button)findViewById(R.id.enviar);
        actualizar=(Button)findViewById(R.id.actualizar);
        cancelar=(Button)findViewById(R.id.cancelar);
        atras=(Button)findViewById(R.id.salir);
        tabla=(TableLayout)findViewById(R.id.tabla);

        actualizar.setVisibility(View.GONE);
        guardar.setVisibility(View.VISIBLE);

        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("idcategoria", categoria.getText().toString());
                values.put("producto", producto.getText().toString());
                values.put("cantidad", cantidad.getText().toString());
                values.put("precio", precio.getText().toString());
                values.put("estado", "1");
                db.insert("productos", null, values);
                db.close();

                Toast.makeText(getApplicationContext(), "Registro Agregado", Toast.LENGTH_SHORT).show();
                reiniciarActividad();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                consulta =  idcategoria.getText().toString();
//                Toast.makeText(getApplicationContext(), "X"+idcategoria.getText().length(), Toast.LENGTH_SHORT).show();
                ContentValues values = new ContentValues();
                values.put("idcategoria", categoria.getText().toString());
                values.put("producto", producto.getText().toString());
                values.put("cantidad", cantidad.getText().toString());
                values.put("precio", precio.getText().toString());
                values.put("estado", "1");
                db.update("productos", values, "idproducto = " + idproducto.getText().toString(), null);
                db.close();
                Toast.makeText(getApplicationContext(), "Registro Actualizado", Toast.LENGTH_SHORT).show();

                actualizar.setVisibility(View.GONE);
                guardar.setVisibility(View.VISIBLE);
                reiniciarActividad();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                idproducto.setText("");
                categoria.setText("");
                producto.setText("");
                cantidad.setText("");
                precio.setText("");
               // estado.setText("1");
                actualizar.setVisibility(View.GONE);
                guardar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Modificado Cancelada", Toast.LENGTH_SHORT).show();
            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        agregarFilas("Producto", "Cantidad", "Precio","0");
        Cursor productos_existentes=db.rawQuery("SELECT idproducto, producto, cantidad,precio FROM productos", null);
        if(productos_existentes.moveToFirst())
        {
            do{
                agregarFilas(productos_existentes.getString(1),productos_existentes.getString(2),productos_existentes.getString(3),productos_existentes.getString(0));
            }while(productos_existentes.moveToNext());
        }

    }

    private void reiniciarActividad() {
        Intent a=new Intent(getApplicationContext(),ProductoActivity.class);
        startActivity(a);
    }

    private void agregarFilas(String producto1, String cant1,String precio1, String idprod1)
    {
        fila=new TableRow(this);
        fila.setLayoutParams(layoutFila);

        //TextView tvid_categoria=new TextView(this);
        TextView tvproducto=new TextView(this);
        TextView tvcantidad=new TextView(this);
        TextView tvprecio=new TextView(this);

        tvproducto.setText(producto1);
        tvproducto.setBackgroundResource(R.drawable.celda_cuerpo);

        tvcantidad.setText(cant1);
        tvcantidad.setBackgroundResource(R.drawable.celda_cuerpo);
        tvcantidad.setGravity(Gravity.RIGHT);

        tvprecio.setText(precio1);
        tvprecio.setBackgroundResource(R.drawable.celda_cuerpo);
        tvprecio.setGravity(Gravity.RIGHT);

        tvproducto.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));
        tvcantidad.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));
        tvprecio.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));

        fila.addView(tvproducto);
        fila.addView(tvcantidad);
        fila.addView(tvprecio);


        if(idprod1.compareTo("0")!=0)
        {
            ImageView editar=new ImageView(this);
            ImageView eliminar=new ImageView(this);

            editar.setId(Integer.parseInt(idprod1));
            editar.setAdjustViewBounds(true);
            editar.setBackgroundResource(R.drawable.celda_cuerpo);
            editar.setImageResource(R.drawable.editar);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cur = db.rawQuery(" SELECT id_producto, idcategoria, producto, cantidad, precio FROM productos WHERE idproducto ='" + view.getId() + "' ", null);

                    if (cur.moveToFirst()) {
                        //do {
                        String _idproducto = cur.getString(0);
                        String _idcategoria = cur.getString(1);
                        String _producto = cur.getString(2);
                        String _cantidad = cur.getString(3);
                        String _precio = cur.getString(4);

                        // idproducto.setEnabled(true);
                        idproducto.setText(_idproducto);
                        categoria.setText(_idcategoria);
                        producto.setText(_producto);
                        cantidad.setText(_cantidad);
                        precio.setText(_precio);
                        //estado.setText(_estado);

                        actualizar.setVisibility(View.VISIBLE);
                        guardar.setVisibility(View.VISIBLE);

                    }
                }
            });

            eliminar.setId(Integer.parseInt(idprod1));
            eliminar.setAdjustViewBounds(true);
            eliminar.setBackgroundResource(R.drawable.celda_cuerpo);
            eliminar.setImageResource(R.drawable.close);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Eliminando "+view.getId(),Toast.LENGTH_SHORT).show();
                    String[] args=new String[]{""+view.getId()};
                    db.delete("Productos", "idproducto LIKE ?", args);
                    reiniciarActividad();
                }
            });


            editar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            eliminar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));

            fila.addView(editar);
            fila.addView(eliminar);
        }
        else
        {
            TextView vacio = new TextView(this);
            vacio.setText("Acción");
            vacio.setBackgroundResource(R.drawable.celda_cuerpo);
            vacio.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 2));
            fila.addView(vacio);
        }

        tabla.addView(fila);
    }
}