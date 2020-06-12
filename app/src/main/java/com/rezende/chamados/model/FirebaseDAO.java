package com.rezende.chamados.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDAO {

    // --Commented out by Inspection (19/05/2020 13:01):private static final String TAG = "chamados";
    private static DatabaseReference mDatabase;// ...
    private static FirebaseAuth mAuth;// ...

    /*public static void executeQuery(Object obj, String table){
        //Global.getInstance();
        String id = mDatabase.push().getKey();
        setUserId(id);
        mDatabase.child(table).child(getUserId()).setValue(obj);
    }*/

    public static DatabaseReference getConnection() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return mDatabase;
    }

    public static DatabaseReference getmDatabase() {
        if (mDatabase == null ){
            return getConnection();
        }
        return mDatabase;
    }

    public static FirebaseAuth getAuthentication() {
        mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public static FirebaseAuth getmAuth() {
        if (mAuth == null ){
            return getAuthentication();
        }
        return mAuth;
    }

}
