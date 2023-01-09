package com.example.proyectoappmovil;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoappmovil.dao.Sesiones;
import com.example.proyectoappmovil.entidades.Fruta;
import com.example.proyectoappmovil.entidades.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FrutasCrud extends AppCompatActivity {


        TextView nameUser;
        Button btnGuardarFruta;
        ImageButton btnCerrarSesion;
        EditText edtNombreFruta,edtPrecioFruta;
        Spinner spinnerTipoFruta;
        ImageView imageViewFruta;
        Usuario usuario;
    FirebaseAuth mAuth;
    StorageReference storageRef;
    FirebaseStorage storage;
    private static String TAG = "infoFotoFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        storage  = FirebaseStorage.getInstance();

        mAuth = FirebaseAuth.getInstance();
        storageRef = storage.getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frutas_crud);



        Bundle b = getIntent().getExtras();

        usuario = b.getParcelable("usuario");
        nameUser = findViewById(R.id.Fruta_User);
        nameUser.setText(usuario.getNombre());

        imageViewFruta = findViewById(R.id.Fruta_img);
        edtNombreFruta = findViewById(R.id.Fruta_Nombre);
        edtPrecioFruta = findViewById(R.id.Fruta_Precio);
        btnGuardarFruta = findViewById(R.id.Fruta_BtnEnviar);
        spinnerTipoFruta = findViewById(R.id.Fruta_Tipo);
        btnCerrarSesion = findViewById(R.id.Fruta_btnLogut);






        btnCerrarSesion.setOnClickListener(view -> startActivity(Sesiones.CloseSession(this)));
        btnGuardarFruta.setOnClickListener(view -> GuardarFruta());

        imageViewFruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                camaraLauncher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){

              //  Bundle extras = result.getData().getExtras();
                //Bitmap b =(Bitmap) extras.get("data");
                //imageViewFruta.setImageBitmap(b);
                imageViewFruta.setImageURI(result.getData().getData());
            }
        }
    });



    private void GuardarFruta() {
        String nombre = edtNombreFruta.getText().toString().replace(" ","");

        GuardarFrutaFoto(nombre);



    }

    private void GuardarFrutaFoto(String nombre) {
        String refe = "frutas/"+nombre.concat(".jpg");
        StorageReference mountainImagesRef = storageRef.child(refe);
        Bitmap bitmap = ((BitmapDrawable) imageViewFruta.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.w(TAG, "Error adding document", exception);            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.w(TAG, "ImagenAgregada");
                BuscarFrutabd(refe);

            }

            }
        );


    }

    private void BuscarFrutabd(String refe) {
        storageRef.child(refe).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.i(TAG,uri.toString());
                GuardarFrutabd(uri.toString());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });








    }

    private void GuardarFrutabd(String toString) {
        String nombre = edtNombreFruta.getText().toString().replace(" ","");
        String precio = edtPrecioFruta.getText().toString();
        String tipo = spinnerTipoFruta.getSelectedItem().toString();
        String link = toString;
        Fruta fruta = new Fruta(link.substring(link.length()-10)+nombre,nombre,precio,tipo,link);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("frutas")
                .document(fruta.getNombre())
                .set(fruta)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Registro exitoso de fruta ");
                        Toast.makeText(FrutasCrud.this,"Fruta agregada con exito",Toast.LENGTH_SHORT).show();
                     //   startActivity(Sesiones.CloseSession(FrutasCrud.this));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document fruta", e);
                    }
                }) ;
    }



}