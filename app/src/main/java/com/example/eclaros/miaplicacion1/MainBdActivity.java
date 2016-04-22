package com.example.eclaros.miaplicacion1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompatSideChannelService;
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
import android.database.sqlite.SQLiteStatement;


public class MainBdActivity extends AppCompatActivity {
    private TextView idcategoria;
    private EditText categoria;
    private EditText estado;
    private Button guardar, actualizar, atras, cancelar;
    private TableLayout tabla;
    private TableRow fila;
    TableRow.LayoutParams layoutFila;
    public  String kEstado;

    private SQLiteDatabase db;
    public static final int VERSION = 1;
    //private static final String TABLE_CATEGORIA = "categoria";
    private Context context;
    private String consulta, llave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bd);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=this;

        Base_datos crearBD = new Base_datos(context,VERSION);
        db = crearBD.getWritableDatabase();

        idcategoria=(TextView)findViewById(R.id.id_categoria);
        categoria=(EditText)findViewById(R.id.categoria);
        estado=(EditText)findViewById(R.id.estado);
        guardar=(Button)findViewById(R.id.enviar);
        actualizar=(Button)findViewById(R.id.actualizar);
        cancelar=(Button)findViewById(R.id.cancelar);
        atras=(Button)findViewById(R.id.salir);
        tabla=(TableLayout)findViewById(R.id.tabla);
        actualizar.setVisibility(View.GONE);
        //cancelar.setVisibility(View.GONE);
        guardar.setVisibility(View.VISIBLE);

        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
//INSERTA NUEVO REGISTRO
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                consulta =  idcategoria.getText().toString();
                 consulta= categoria.getText().toString();

                if (consulta.equals("")) {
                    Toast.makeText(getApplicationContext(), "Categoria esta vacio!", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put("descategoria", categoria.getText().toString());
                    values.put("estado", estado.getText().toString());
                    db.insert("categoria", null, values);
                    db.close();
                    Toast.makeText(getApplicationContext(), "Registro Agregado", Toast.LENGTH_SHORT).show();
                    actualizar.setVisibility(View.GONE);
                    //cancelar.setVisibility(View.GONE);
                    guardar.setVisibility(View.VISIBLE);
                    reiniciarActividad();

                }

            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                consulta =  idcategoria.getText().toString();
//                Toast.makeText(getApplicationContext(), "X"+idcategoria.getText().length(), Toast.LENGTH_SHORT).show();
                ContentValues values = new ContentValues();
                    //values.put("idcategoria", idcategoria.getText().toString());
                values.put("descategoria", categoria.getText().toString());
                values.put("estado", estado.getText().toString());
                db.update("categoria", values, "idcategoria = " + idcategoria.getText().toString(), null);
                db.close();
                Toast.makeText(getApplicationContext(), "Registro Actualizado", Toast.LENGTH_SHORT).show();
                actualizar.setVisibility(View.GONE);
                //cancelar.setVisibility(View.GONE);
                guardar.setVisibility(View.VISIBLE);
                reiniciarActividad();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                idcategoria.setText("");
                categoria.setText("");
                estado.setText("1");
                actualizar.setVisibility(View.GONE);
                //cancelar.setVisibility(View.GONE);
                guardar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Modificado Cancelada", Toast.LENGTH_SHORT).show();
            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        agregarFilas("Categoria", "Estado", "0");
        Cursor categorias_existentes=db.rawQuery("SELECT idcategoria,descategoria,estado FROM categoria", null);
        if(categorias_existentes.moveToFirst())
        {
            do{
                agregarFilas(categorias_existentes.getString(1),categorias_existentes.getString(2),categorias_existentes.getString(0));
            }while(categorias_existentes.moveToNext());
        }

    }

    private void reiniciarActividad() {
        Intent a=new Intent(getApplicationContext(),MainBdActivity.class);
        startActivity(a);
    }

    private void agregarFilas(String cat1,String estado1,String id1)
    {
        fila=new TableRow(this);
        fila.setLayoutParams(layoutFila);

        final TextView nombre_cat=new TextView(this);
        final TextView estado_cat=new TextView(this);

        nombre_cat.setText(cat1);
        nombre_cat.setBackgroundResource(R.drawable.celda_cuerpo);

        estado_cat.setText(estado1);
        estado_cat.setBackgroundResource(R.drawable.celda_cuerpo);
        estado_cat.setGravity(Gravity.RIGHT);

        llave = id1;
        nombre_cat.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));
        estado_cat.setLayoutParams(new TableRow.LayoutParams(0,
                TableRow.LayoutParams.MATCH_PARENT, 5));

        fila.addView(nombre_cat);
        fila.addView(estado_cat);


        if(id1.compareTo("0")!=0)
        {
            ImageView editar=new ImageView(this);
            ImageView edregistro=new ImageView(this);
            ImageView eliminar=new ImageView(this);
            ImageView comprar=new ImageView(this);

            edregistro.setId(Integer.parseInt(id1));
            edregistro.setAdjustViewBounds(true);
            edregistro.setBackgroundResource(R.drawable.celda_cuerpo);
            edregistro.setImageResource(R.drawable.editar);

            edregistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Cursor cur = db.rawQuery(" SELECT * FROM categoria WHERE idcategoria ='" + view.getId() + "' ", null);

                    if (cur.moveToFirst()) {
                        //do {
                        String _idcategoria = cur.getString(0);
                        String _descategoria = cur.getString(1);
                        String _estado = cur.getString(2);

                        // idproducto.setEnabled(true);
                        idcategoria.setText(_idcategoria);
                        categoria.setText(_descategoria);
                        estado.setText(_estado);

                        actualizar.setVisibility(View.VISIBLE);
                        //cancelar.setVisibility(View.GONE);
                        guardar.setVisibility(View.GONE);

                    }
                }
            });

            editar.setId(Integer.parseInt(id1));
            editar.setAdjustViewBounds(true);
            editar.setBackgroundResource(R.drawable.celda_cuerpo);
            editar.setImageResource(R.drawable.refresh);
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Actualizando " + view.getId(), Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();


                    consulta = "SELECT estado FROM categoria where idcategoria='" + view.getId() + "'";
                    Cursor productos_existentes = db.rawQuery(consulta.toString(), null);
                    if (productos_existentes.moveToFirst()) {
                        Toast.makeText(context, "****" + productos_existentes.getString(0), Toast.LENGTH_LONG).show();
//                        if ((Integer.parseInt(productos_existentes.getString(0))%2)==1 ) {

                        if (Integer.parseInt(productos_existentes.getString(0)) == 1) {
                            values.put("estado", "2");
                        } else {
                            values.put("estado", "1");
                        }
                        //values.put("estado", "" + ((Integer.parseInt(productos_existentes.getString(0))%2)+1));
                    } else {
                        values.put("estado", "0");
                    }
                    //values.put("estado", "7");
                    String[] args = new String[]{"" + view.getId()};
                    db.update("categoria", values, "idcategoria LIKE ?", args);
                    reiniciarActividad();
                    categoria.setText(consulta);
                }
            });

            eliminar.setId(Integer.parseInt(id1));
            eliminar.setAdjustViewBounds(true);
            eliminar.setBackgroundResource(R.drawable.celda_cuerpo);
            eliminar.setImageResource(R.drawable.close);
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Eliminando " + view.getId(), Toast.LENGTH_SHORT).show();
                    String[] args = new String[]{"" + view.getId()};
                    db.delete("categoria", "idcategoria LIKE ?", args);
                    reiniciarActividad();
                }
            });


            editar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            eliminar.setLayoutParams(new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));

            fila.addView(edregistro);
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
            fila.addView(vacio
            );
        }

        tabla.addView(fila);
    }
}
