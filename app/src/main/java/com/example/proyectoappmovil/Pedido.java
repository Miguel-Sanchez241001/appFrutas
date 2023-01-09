package com.example.proyectoappmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Fruta;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Pedido extends AppCompatActivity {


    GridView tabla ;
    Usuario usuario;
    ImageButton btnSalir,btnVolver;
    Button btnVerDetallePedido;
    TextView UserNombre, pediTotal;
    ArrayList<String> nombreFrutas;

    private static String TAG = "FrutasPedido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);


        // obteniendo data del intent
        Bundle b = getIntent().getExtras();
        usuario = b.getParcelable("usuario");
        nombreFrutas = b.getStringArrayList("ListaFrutas");


        // RECONOCIMIENTO
        UserNombre = findViewById(R.id.Pedi_Username);
        tabla = findViewById(R.id.Pedi_GrView);
        btnSalir= findViewById(R.id.Pedi_btnSalir);
        pediTotal = findViewById(R.id.Pedi_Total);
        btnVerDetallePedido = findViewById(R.id.Pedi_btnVerDetalle);
        // completa datos
        UserNombre.setText(usuario.getNombre());
        FrutaItemPedidoAdapter adaptador = new FrutaItemPedidoAdapter(this, R.layout.item_fruta_pedido,pediTotal);
        ObtenerFrutas(nombreFrutas,adaptador);
        btnSalir.setOnClickListener(view -> startActivity(Sesiones.CloseSession(this)));
        btnVerDetallePedido.setOnClickListener(view -> startActivity(new Intent(this,DetallePedido.class)));
      //  ActualizarTotal();



    }

    private void ActualizarTotal() {
        Double suma = 0.0;
        Log.i(TAG, tabla.getAdapter().getCount() +" se encontro el item ");

        for (int i =0;i<tabla.getCount();i++) {
           View v = tabla.getChildAt(i);
            Log.i(TAG, v.getContext().toString()+" se encontro el item ");
          TextView precio =  v.findViewById(R.id.Pedi_txtPrecio);
            Double valor = Double.parseDouble(precio.getText().toString().replaceAll("S/. ",""));
            suma = suma+valor;
        }
        pediTotal.setText(suma.toString());

    }

    private void ObtenerFrutas(ArrayList<String> nombreFrutas, FrutaItemPedidoAdapter adaptador) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frutas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Fruta> frutasT = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id,link,precio,tipo,nombre;
                                nombre = document.getData().get("nombre").toString();


                                if (nombreFrutas.contains(nombre)){
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    id = document.getData().get("idFruta").toString();
                                    link = document.getData().get("linkIMG").toString();


                                    precio = document.getData().get("precio").toString();
                                    tipo = document.getData().get("tipo").toString();
                                    Fruta fruta = new Fruta(id,nombre,precio,tipo,link);
                                    Log.d(TAG,  fruta.toString());
                                    frutasT.add(fruta);
                                }
                                //  Log.d(TAG, fruta.toString());
                            }
                            ActualizarTabla(frutasT,adaptador);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void ActualizarTabla(ArrayList<Fruta> frutasT, FrutaItemPedidoAdapter adaptador) {
        adaptador.setFrutas(frutasT);
        tabla.setAdapter(adaptador);

    }
}