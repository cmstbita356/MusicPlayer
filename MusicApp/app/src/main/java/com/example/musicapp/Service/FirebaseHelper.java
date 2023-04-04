package com.example.musicapp.Service;

import com.example.musicapp.Model.Song;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseHelper {
    public static void getData(ValueEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(listener);
    }
    public static void getDataChange(ValueEventListener listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(listener);
    }
    public static void addData(String path, String key, Map<String, Object> value)
    {
        DatabaseReference parentNode = FirebaseDatabase.getInstance().getReference(path);
        parentNode.child(key).setValue(value);
    }
    public static void addData(String path, Map<String, Object> value)
    {
        DatabaseReference parentNode = FirebaseDatabase.getInstance().getReference(path);
        String key = parentNode.push().getKey();
        parentNode.child(key).setValue(value);
    }
    public static void addData(String path, String key, Map<String, Object> value, OnSuccessListener listener)
    {
        DatabaseReference parentNode = FirebaseDatabase.getInstance().getReference(path);
        parentNode.child(key).setValue(value).addOnSuccessListener(listener);
    }
    public static void editData(String path,  Object value)
    {
        DatabaseReference parentNode = FirebaseDatabase.getInstance().getReference(path);
        parentNode.setValue(value);
    }
    public static void deleteData(String path)
    {
        DatabaseReference parentNode = FirebaseDatabase.getInstance().getReference(path);
        parentNode.removeValue();
    }

}
