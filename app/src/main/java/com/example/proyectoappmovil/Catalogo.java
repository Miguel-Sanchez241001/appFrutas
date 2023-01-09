package com.example.proyectoappmovil;


import static androidx.core.view.ViewCompat.getRootWindowInsets;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoappmovil.R;
import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Detalle;
import com.example.proyectoappmovil.entidades.Tipo;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class Catalogo extends AppCompatActivity {
    ImageButton btnLogut, btnTemporada, btnOrganico, btnVerano;
    TextView User;
    Usuario usuario;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        //getSupportActionBar().hide();
        Bundle b = getIntent().getExtras();

       usuario = b.getParcelable("usuario");
        btnLogut = findViewById(R.id.Cat_btnLogut);
        btnTemporada = findViewById(R.id.Cat_btnTemporada);
        btnOrganico = findViewById(R.id.Cat_btnOrganico);
        btnVerano = findViewById(R.id.Cat_btnVerano);
        User = findViewById(R.id.Cat_User);
        User.setText(usuario.getNombre());


        btnTemporada.setOnClickListener(view -> Redireccion(view, Tipo.TEMPORADA));
        btnOrganico.setOnClickListener(view -> Redireccion(view,Tipo.ORGANICOS));
        btnVerano.setOnClickListener(view -> Redireccion(view,Tipo.VERANO));
        btnLogut.setOnClickListener(view -> startActivity(Sesiones.CloseSession(this)));

    }

    private void Redireccion(View view,String tipo) {
        Intent intent = new Intent(this , FrutasCatalogo.class);
        intent.putExtra("tipo",tipo);
        intent.putExtra("usuario",  usuario);
        startActivity(intent);
    }



    public void DetalleSemi(View v) {
        Intent intent = new Intent(this , DetalleFruta.class);
        intent.putExtra("usuario",  usuario);
        startActivity(intent);

    }
}
