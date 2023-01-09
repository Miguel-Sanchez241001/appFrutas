package com.example.proyectoappmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.proyectoappmovil.DataBase.ReferenceDataBase;
import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Login;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroUser extends AppCompatActivity {
    Button btnRegistrar;
    EditText Nombre,Apellido,Correo,Password;
    DatabaseReference refe;
    AwesomeValidation awesomeValidation;
     FirebaseAuth mAuth;

     private static String TAG = "ResgitroUser";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        refe = new ReferenceDataBase().getDatabaseReference();
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_user);

        // validacion de los datos
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.Re_edtCorreo, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.Re_edtpassword, ".{6,}",R.string.invalid_password);

        btnRegistrar = findViewById(R.id.Re_btnRegitro);
        btnRegistrar.setOnClickListener(view -> RegistroUsuario(view));
        Nombre = findViewById(R.id.Re_edtNombre);
        Apellido = findViewById(R.id.Re_edtApellido);
        Correo = findViewById(R.id.Re_edtCorreo);
        Password = findViewById(R.id.Re_edtpassword);




    }


    public void Registrar(View v){
        String id =  refe.push().getKey();
        String idd = Correo.getText().toString().replace(".","_");
        Login login = new Login(Correo.getText().toString(),Password.getText().toString(),1 );
        Usuario usuario = new Usuario(id,Nombre.getText().toString(),Apellido.getText().toString(),login);

        try {
            refe.child("Usuarios").child(idd).setValue(usuario);
            Log.e("Fire","Registrado");
            Toast.makeText(this,"Registrado",Toast.LENGTH_LONG).show();

        }catch (RuntimeException e ){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            Log.e("Fire",e.toString()+" "+idd);
        }


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void RegistroUsuario(View v){
      //  String id =  refe.push().getKey();
      //  String idd = Correo.getText().toString().replace(".","_");

        if (awesomeValidation.validate()) {
            RegisterFirebase(Correo.getText().toString(),Password.getText().toString());
        }else {
            Toast.makeText(RegistroUser.this,"Completa los datos ",Toast.LENGTH_SHORT).show();
        }
    }

    private void RegistroBDUser(String UID) {

        Login lo  = new Login(UID,Correo.getText().toString(),Password.getText().toString(),1);
        Usuario user = new Usuario(Correo.getText().toString(),Nombre.getText().toString(),Apellido.getText().toString(),lo);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .document(user.getLogin().getCorreo())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Registro exitoso de usuario ");
                        startActivity(Sesiones.CloseSession(RegistroUser.this));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                }) ;


    }

    private void RegisterFirebase(String correo, String password) {

        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("RegistroUsuario", "createUserWithEmail:success");
                            Toast.makeText(RegistroUser.this, "Usuario registrado.",
                                        Toast.LENGTH_SHORT).show();
                                    RegistroBDUser(task.getResult().getUser().getUid());
                        } else {
                            Log.w("RegistroUsuario", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroUser.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}