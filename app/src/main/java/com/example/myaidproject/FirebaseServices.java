package com.example.myaidproject;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class FirebaseServices {
    private static FirebaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore   fire;
    private FirebaseStorage storage;
    private Uri selectedImageURL;
    private  Userr currentUser;
    private Course selectedCourse;
    private boolean userChangeFlag;

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }
    public void setSelectedImageURL(Uri selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

    public FirebaseServices() {
        this.currentUser = currentUser;
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public FirebaseAuth getAuth() {

        return auth;
    }

    public FirebaseFirestore getFire() {

        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public static FirebaseServices getInstance() {
        if ( instance == null){
            instance= new FirebaseServices();
        }
        return instance;
    }

    public Userr getCurrentUser()
    {
        return this.currentUser;
    }

    public static FirebaseServices reloadInstance(){
        instance=new FirebaseServices();
        return instance;
    }
    public boolean isUserChangeFlag() {
        return userChangeFlag;
    }
    public void setUserChangeFlag(boolean userChangeFlag) {
        this.userChangeFlag = userChangeFlag;
    }
    public void getCurrentObjectUser(UserCallback callback) {  ArrayList<Userr> usersInternal = new ArrayList<>();
        fire.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot: queryDocumentSnapshots.getDocuments()){
                    Userr user = dataSnapshot.toObject(Userr.class);
                    if (auth.getCurrentUser() != null && auth.getCurrentUser().getEmail().equals(user.getUserName())) {
                        usersInternal.add(user);

                    }
                }
                if (usersInternal.size() > 0)
                    currentUser = usersInternal.get(0);

                callback.onUserLoaded(currentUser);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
