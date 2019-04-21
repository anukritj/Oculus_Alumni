package com.spit.fest.oculus.Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtils
{
//    private DatabaseReference databaseReference;
    private static DatabaseUtils databaseUtils;
    private static FirebaseDatabase firebaseDatabase;

//    private void setFirebaseInstance()
//    {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);
//        databaseReference = firebaseDatabase.getReference();
//        databaseReference.keepSynced(true);
//    }

    private FirebaseDatabase getFirebaseDatabase()
    {
        if (firebaseDatabase == null)
        {
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }

    public DatabaseReference getDatabaseInstance()
    {
//        if (databaseReference == null)
//            setFirebaseInstance();
        return getFirebaseDatabase().getReference();
    }

    public static DatabaseUtils getDatabaseReference() {
        if (databaseUtils == null)
            databaseUtils = new DatabaseUtils();
        return databaseUtils;
    }
}
