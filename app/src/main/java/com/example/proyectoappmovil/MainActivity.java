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
import android.widget.ImageButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
     Button btnRegistrar;
     ImageButton btnLoguear;
  //  DatabaseReference refe;
    EditText edtCorreo, edtPassword;
    AwesomeValidation awesomeValidation;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  refe = new ReferenceDataBase().getDatabaseReference();
        mAuth = FirebaseAuth.getInstance();
      //  supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
      //  getSupportActionBar().hide(); // ocultar action bar
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.Login_edtCorreo, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.Login_password, ".{6,}",R.string.invalid_password);
        btnRegistrar = findViewById(R.id.Login_btnRegistrar);
        btnLoguear = findViewById(R.id.Login_btnIngresar);


        edtCorreo = findViewById(R.id.Login_edtCorreo);
        edtPassword = findViewById(R.id.Login_password);
        btnRegistrar.setOnClickListener(view -> Registrar(view));
        btnLoguear.setOnClickListener(view -> Loguear(view) );


        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            buscarUsuario(user.getEmail());
        }

    }

    public void Registrar(View v){

        Intent intent = new Intent(this, RegistroUser.class);
        startActivity(intent);

    }

    public void Loguear(View v){
        /**
        boolean[] login = {false,false};

       String edtCorreo2, edtPassword2;
       if(edtCorreo.getText().toString().equals("admin")){
           edtCorreo2 = "admin";
       }else{
           edtCorreo2 = edtCorreo.getText().toString().replace(".","_");
       }

        edtPassword2= edtPassword.getText().toString();

        refe.child("Usuarios").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String a = String.valueOf(task.getResult().getValue());
                    Log.i("firebase",a);
                    if (a.contains(edtCorreo2)){
                        String password = task.getResult().child(edtCorreo2).child("login").child("password").getValue().toString();
                        String nombre = task.getResult().child(edtCorreo2).child("nombre").getValue().toString();
                        String apellido = task.getResult().child(edtCorreo2).child("apellido").getValue().toString();
                        String idUsuario = task.getResult().child(edtCorreo2).child("idUsuario").getValue().toString();
                        Integer admin = Integer.parseInt(task.getResult().child(edtCorreo2).child("login").child("admin").getValue().toString());
                        Usuario usuario = new Usuario(idUsuario,nombre,apellido,new Login(edtCorreo2,password,admin));
                        Log.i("firebase",usuario.toString());

                        if(edtPassword2.equals(password)){

                            if (admin==1){
                               intent = new Intent(MainActivity.this , Catalogo.class);
                                intent.putExtra("usuario",  usuario);
                                Toast.makeText(MainActivity.this,"Ingresaste",Toast.LENGTH_LONG).show();
                            }else {

                                intent = new Intent(MainActivity.this , FrutasCrud.class);
                                intent.putExtra("usuario",  usuario);
                                Toast.makeText(MainActivity.this,"Ingresaste",Toast.LENGTH_LONG).show();
                            }
                            startActivity(intent);

                        }else{
                            Toast.makeText(MainActivity.this,"Datos incorrectos",Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,"Datos incorrectos",Toast.LENGTH_LONG).show();
                    }

                }
            }

        });

**/

        if (awesomeValidation.validate()){
            signIn(edtCorreo.getText().toString(),edtPassword.getText().toString());
        }else{
            Toast.makeText(MainActivity.this,"Completa los datos ",Toast.LENGTH_SHORT).show();
        }


    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(TAG,"inicio de sesion correcto");
                    buscarUsuario(task.getResult().getUser().getEmail());


                }else{
                    Toast.makeText(MainActivity.this, "Contraseña y/o email incorrectos ", Toast.LENGTH_SHORT).show();
                    Log.i(TAG,"ERROR login");
                }
            }
        });
        // [END sign_in_with_email]

    }

    private void Redireccion(@NonNull String uid,Usuario user) {
        Log.i(TAG,user.toString());
        if (uid.equals("admin@admin.com")) {
            Intent i = new Intent(this,FrutasCrud.class);
            i.putExtra("usuario",user);
            startActivity(i);
        }else{
            Intent i = new Intent(this,Catalogo.class);
            i.putExtra("usuario",user);
            startActivity(i);
        }

    }

    private void buscarUsuario(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(email);

        docRef.get()
              .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.i(TAG,documentSnapshot.toString());
                        Gson a = new Gson();
                        Log.i(TAG,documentSnapshot.getData().toString());
                        Usuario user = a.fromJson(documentSnapshot.getData().toString(),Usuario.class);

                        Redireccion(email,user);
                    }
                });


    }

}