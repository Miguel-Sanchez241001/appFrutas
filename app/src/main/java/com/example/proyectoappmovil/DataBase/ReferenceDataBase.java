package com.example.proyectoappmovil.DataBase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReferenceDataBase {

    private DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private StorageReference storageReference;

    public DatabaseReference getDatabaseReference() {
        return databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    public StorageReference getStorageReference(){
        return  storageReference = FirebaseStorage.getInstance().getReference().child("Frutas");

    }


    public ReferenceDataBase() {
    }
}
