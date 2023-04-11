package com.example.musicapp.Model;

import com.example.musicapp.Service.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class HistorySearchData {
    public static ArrayList<String> getListHistory(DataSnapshot dataSnapshot, int id)
    {
        ArrayList<String> ListHis = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("History_Search").getChildren()) {
            if(snapshot.child("id").getValue(Integer.class).equals(id))
            {
                ListHis.add(snapshot.child("search").getValue(String.class));
            }
        }
        return ListHis;
    }
    public static void ClearAllHistory(DataSnapshot dataSnapshot, int id){
        ArrayList<String> ListHis = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("History_Search").getChildren()) {
            if(snapshot.child("id").getValue(Integer.class).equals(id))
            {
                String path = "History_Search/" + snapshot.getKey();
                FirebaseHelper.deleteData(path);
            }
        }
    }


    public static String getKeyHis(DataSnapshot dataSnapshot, int id, String search)
    {
        for (DataSnapshot snapshot : dataSnapshot.child("History_Search").getChildren()) {
            if(snapshot.child("id").getValue(Integer.class).equals(id) && snapshot.child("search").getValue(String.class).equals(search))
            {
                return snapshot.getKey();
            }
        }
        return null;
    }
    public static boolean check(DataSnapshot dataSnapshot, int id, String search)
    {
        for (String hisSearch : getListHistory(dataSnapshot, id))
        {
            if(search.equals(hisSearch))
            {
                return true;
            }
        }
        return false;
    }
}
