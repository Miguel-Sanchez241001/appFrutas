package com.example.proyectoappmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Fruta;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalleFruta extends AppCompatActivity {
    TextView user;
    Usuario usuario;
    ImageButton btnLoguot, btnVolver;
    ArrayList<Fruta> frutas;
    ImageView item1,item2,item3,item4;
    TextView nombItem1,nombItem2,nombItem3,nombItem4,preItem1,preItem2,preItem3,preItem4;
    ProgressBar progressBar1,progressBar2,progressBar3,progressBar4;
    private static String TAG = "FrutaListado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // actualizacion de datos del usuario actual
        Bundle b = getIntent().getExtras();
        usuario = b.getParcelable("usuario");
        user = findViewById(R.id.Detalle_User);
        user.setText(usuario.getNombre());

        frutas = new ArrayList<>();
        // botones basicos de volver y cerrado de sesion
        btnLoguot = findViewById(R.id.Detalle_btnLogut);
        btnVolver = findViewById(R.id.Detalle_btnRegresar);
        btnLoguot.setOnClickListener(view -> startActivity(Sesiones.CloseSession(this)));
        btnVolver.setOnClickListener(view -> Volver(view));
        ObteneFrutas();
        // mapeando los objetos de cada fruta
        nombItem1 = findViewById(R.id.Deta_nomItem1);
        nombItem2 = findViewById(R.id.Deta_nomItem2);
        nombItem3 = findViewById(R.id.Deta_nomItem3);
        nombItem4 = findViewById(R.id.Deta_nomItem4);

        item1 = findViewById(R.id.Deta_Item1);
        item2 = findViewById(R.id.Deta_Item2);
        item3 = findViewById(R.id.Deta_Item3);
        item4 = findViewById(R.id.Deta_Item4);

        preItem1 = findViewById(R.id.Deta_preItem1);
        preItem2 = findViewById(R.id.Deta_preItem2);
        preItem3 = findViewById(R.id.Deta_preItem3);
        preItem4 = findViewById(R.id.Deta_preItem4);

        progressBar1 = findViewById(R.id.Deta_proItem1);
        progressBar2 = findViewById(R.id.Deta_proItem2);
        progressBar3 = findViewById(R.id.Deta_proItem3);
        progressBar4 = findViewById(R.id.Deta_proItem4);

        // Completar datos con las frutas de la bd



    }

    private void completarDatosFrutas(ArrayList<Fruta> frutasT) {
        completarDatosNombres(frutasT);
        completarDatosImagen(frutasT);
        completarDatosPrecio(frutasT);

    }

    private void completarDatosPrecio(ArrayList<Fruta> frutasT) {
        ArrayList<String> precios = new ArrayList<>();
        for (Fruta fruta:frutasT) {
            precios.add( "S/. "+fruta.getPrecio()+" KG");
        }
        Log.d(TAG, "completarDatosPrecio: " + precios.toString());

        preItem1.setText(precios.get(0));
        preItem2.setText(precios.get(1));
        preItem3.setText(precios.get(2));
        preItem4.setText(precios.get(3));


    }

    private void completarDatosImagen(ArrayList<Fruta> frutasT) {
        Picasso
                .with(DetalleFruta.this)
                .load(frutasT.get(0).getLinkIMG())
                .into(item1,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar1 != null) {
                            progressBar1.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        Picasso
                .with(DetalleFruta.this)
                .load(frutasT.get(1).getLinkIMG())
                .into(item2,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar2 != null) {
                            progressBar2.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        Picasso
                .with(DetalleFruta.this)
                .load(frutasT.get(2).getLinkIMG())
                .into(item3,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar3 != null) {
                            progressBar3.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
        Picasso
                .with(DetalleFruta.this)
                .load(frutasT.get(3).getLinkIMG())
                .into(item4,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar4 != null) {
                            progressBar4.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


    }

    private void completarDatosNombres(ArrayList<Fruta> frutasT) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Fruta fruta:frutasT) {
            nombres.add( fruta.getNombre());
        }
        Log.d(TAG, "completarDatosNombre " + nombres.toString());

        nombItem1.setText(nombres.get(0));
        nombItem2.setText(nombres.get(1));
        nombItem3.setText(nombres.get(2));
        nombItem4.setText(nombres.get(3));
    }

    private void ObteneFrutas() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("frutas")
                .whereEqualTo("tipo", "VERANO")
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
                            ActualizarListaFrutas(frutasT);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


      //  Log.i(TAG,frutas.toString());
    }

    private void ActualizarListaFrutas(ArrayList<Fruta> frutasT) {
        frutas = frutasT;
        Log.i(TAG,frutasT.toString());
        Log.i(TAG,frutasT.get(1).toString());
      //  Fruta fruta = g.fromJson(frutasT.get(1).toString(),Fruta.class);
        Log.i(TAG,frutas.toString());
        completarDatosFrutas(frutasT);
    }





    private void Volver(View view) {
        Intent intent = new Intent(this, Catalogo.class);
        intent.putExtra("usuario",usuario);
        startActivity(intent);

    }


}