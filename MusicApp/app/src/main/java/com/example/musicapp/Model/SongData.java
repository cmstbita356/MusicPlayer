package com.example.musicapp.Model;

import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongData {
    public static ArrayList<Song> getAllSong(DataSnapshot dataSnapshot) {
        ArrayList<Song> songList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("BaiHat").getChildren()) {
            Song song = snapshot.getValue(Song.class);
            songList.add(song);
        }
        return songList;
    }

    public static Song getSongById(int id, DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.child("BaiHat").getChildren()) {
            if (snapshot.child("Id").getValue(Integer.class).equals(id)) {
                return snapshot.getValue(Song.class);
            }
        }
        return null;
    }
    public static ArrayList<Song> getSongByName(String name, DataSnapshot dataSnapshot)
    {
        ArrayList<Song> songList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("BaiHat").getChildren()) {
            if(snapshot.child("Ten").getValue(String.class).contains(name))
            {
                songList.add(snapshot.getValue(Song.class));
            }
        }
        return songList;
    }
    public static ArrayList<Song> getSongByLanguage(String language, DataSnapshot dataSnapshot)
    {
        ArrayList<Song> songList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("BaiHat").getChildren()) {
            if(snapshot.child("NgonNgu").getValue(String.class).equals(language))
            {
                songList.add(snapshot.getValue(Song.class));
            }
        }
        return songList;
    }
}

