package com.ALife.alife.view.Firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {
    String token="x";
    FirebaseAuth mAuth;
    DatabaseReference databaseReference, registerUsers;

    public FirebaseInstanceIdService(String email, String phone, String password,String id) {
        mAuth = FirebaseAuth.getInstance();
       // this.userID = mAuth.getCurrentUser().getUid();
        registerUsers = FirebaseDatabase.getInstance().getReference().child("Customers");
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            private String userID;

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    this.userID = mAuth.getCurrentUser().getUid();
                    token=this.userID;
                    databaseReference = registerUsers.child(userID);

                    //databaseReference.child("id").setValue(userID);
                    databaseReference.child("email").setValue(email);
                    databaseReference.child("phone").setValue(phone);
                    databaseReference.child("password").setValue(password);



                }
            }
        });
    }


    private void registerToken(String token) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();

        /*Request request = new Request.Builder()
                .url()
                .post(body)
                .build();


        try {
            client.newCall(request).execute();
        }catch (Exception e){

        }*/
    }

    public String getToken() {
        return token;
    }
}
