package com.example.proyectoappmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.proyectoappmovil.entidades.Fruta;
import com.squareup.picasso.Picasso;

public class ListaFrutas extends AppCompatActivity {

    ImageView imgFuta;
    Fruta fruta;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_frutas);
        imgFuta = findViewById(R.id.list_imgF);
        progressBar = findViewById(R.id.list_progres);
        Bundle b = getIntent().getExtras();
        fruta = b.getParcelable("fruta");
        progressBar.setVisibility(View.VISIBLE);

        Picasso
                .with(ListaFrutas.this)
                .load(fruta.getLinkIMG())
                .into(imgFuta, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}