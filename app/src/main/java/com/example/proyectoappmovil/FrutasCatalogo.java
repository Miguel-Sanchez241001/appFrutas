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
import android.widget.Toast;

import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Fruta;
import com.example.proyectoappmovil.entidades.Tipo;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FrutasCatalogo extends AppCompatActivity {
    GridView tabla ;
    Usuario usuario;
    ImageButton btnSalir,btnVolver;
    TextView username;
    Button btnOrganico,btnTemporada,btnVerano,btnEnviarPedido;
    FrutaItemAdapter adaptador;
    String tipo;
    ArrayList<String> pedidoFrutas;
    private static String TAG = "FrutasCatalogoLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frutas_catalogo);
        pedidoFrutas = new ArrayList<>();
       // recoleccion de datos del Intent
        Bundle b = getIntent().getExtras();
        usuario = b.getParcelable("usuario");
        tipo =  b.getString("tipo");


      // mapeo de los compoenentes del activity
        tabla = findViewById(R.id.M_GrView);
        username = findViewById(R.id.FruCat_userNombre);
        btnSalir = findViewById(R.id.FruCat_btnSalir);
        btnVolver = findViewById(R.id.FruCat_BtnVolver);
        btnOrganico = findViewById(R.id.FruCat_btnorganico);
        btnTemporada = findViewById(R.id.FruCat_btnTemporada);
        btnVerano = findViewById(R.id.FruCat_btnVerano);
        btnEnviarPedido = findViewById(R.id.FruCat_btnEnviarPedido);

        // asignacion del usuario en linea
        username.setText(usuario.getNombre());

        btnSalir.setOnClickListener(view -> startActivity(Sesiones.CloseSession(this)));
        btnVolver.setOnClickListener(view -> Volver(view));
        btnEnviarPedido.setOnClickListener(view -> EnviarPedido());

        adaptador = new FrutaItemAdapter(this, R.layout.item_fruta);
        ObteneFrutas(tipo);






        btnTemporada.setOnClickListener(view -> ActualzarContenido(view, Tipo.TEMPORADA));
        btnOrganico.setOnClickListener(view -> ActualzarContenido(view, Tipo.ORGANICOS));
        btnVerano.setOnClickListener(view -> ActualzarContenido(view, Tipo.VERANO));
    }

    private void EnviarPedido() {


        Intent intent = new Intent(this, Pedido.class);
        intent.putExtra("usuario",usuario);
        intent.putStringArrayListExtra("ListaFrutas",adaptador.nombreFruta);
        startActivity(intent);
        
        
        
    }

    private void ActualzarContenido(View view, String TEMPORADA) {
        ObteneFrutas(TEMPORADA);
        Toast.makeText(this,"Catalogo "+ TEMPORADA,Toast.LENGTH_SHORT).show();
    }


    private void ObteneFrutas(String organicos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frutas")
                .whereEqualTo("tipo", organicos)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Fruta> frutasT = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String id,link,precio,tipo,nombre;
                                id = document.getData().get("idFruta").toString();
                                link = document.getData().get("linkIMG").toString();
                                nombre = document.getData().get("nombre").toString();
                                precio = document.getData().get("precio").toString();
                                tipo = document.getData().get("tipo").toString();
                                Fruta fruta = new Fruta(id,nombre,precio,tipo,link);
                                Log.d(TAG,  fruta.toString());
                                frutasT.add(fruta);
                                //  Log.d(TAG, fruta.toString());
                            }
                            ActualizarTabla(frutasT);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        //  Log.i(TAG,frutas.toString());
    }

    private void ActualizarTabla(ArrayList<Fruta> frutasT) {
         adaptador.setFrutas(frutasT);

         if(adaptador.nombreFruta.size()>0){
             pedidoFrutas.addAll(adaptador.nombreFruta);
         }
         Log.i(TAG,pedidoFrutas.toString()+"frutas por el momento");
         adaptador.notifyDataSetChanged();
        tabla.setAdapter(adaptador);
    }


    private void Volver(View view) {
        Intent intent = new Intent(this, Catalogo.class);
        intent.putExtra("usuario",usuario);
        startActivity(intent);

    }
}