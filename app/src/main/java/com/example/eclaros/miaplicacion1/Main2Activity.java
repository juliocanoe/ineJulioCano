package com.example.eclaros.miaplicacion1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {
    private TextView titulo;
    private Button abmcategorias;
    private Button abmproductos;
    private Button abmclientes;
    private Button listarproductos;
    private Button listarclientes;
    private Button listarpedidos;
    private Button atras;
    private String mensaje_recibido;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;

        //Recibimos el mensaje del anterior activitu.
        Intent datos=getIntent();
        mensaje_recibido=datos.getStringExtra("mensaje");

        //Declaramos los componentes
        titulo=(TextView)findViewById(R.id.titulo_menu);
        abmcategorias=(Button)findViewById(R.id.abmcategorias);
        abmproductos=(Button)findViewById(R.id.abmproductos);
        abmclientes=(Button)findViewById(R.id.abmclientes);
        listarproductos=(Button)findViewById(R.id.listarproductos);
        listarclientes=(Button)findViewById(R.id.listarclientes);
        listarpedidos=(Button)findViewById(R.id.listarpedidos);
        atras=(Button)findViewById(R.id.salir);

        abmcategorias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent a = new Intent(context, MainBdActivity.class);
                startActivity(a);
            }
        });
        abmproductos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent a = new Intent(context, ProductoActivity.class);
                    startActivity(a);

            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

}
