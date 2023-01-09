package com.example.proyectoappmovil.dao;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.proyectoappmovil.Catalogo;
import com.example.proyectoappmovil.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Sesiones {


    private static String TAG = "SESION";
    public static Intent   CloseSession(Context context ){

        FirebaseAuth.getInstance().signOut();
        Log.i(TAG,"sesion cerrada con exito");
        Toast.makeText(context,"Sesion cerrada ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


}
