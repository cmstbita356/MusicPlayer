package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class PlaylistData {
    public static ArrayList<Playlist> getPlaylist(DataSnapshot dataSnapshot, int id_user)
    {
        ArrayList<Playlist> result = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("Playlist").getChildren()) {
            if(snapshot.child("Id_NguoiDung").getValue(Integer.class).equals(id_user))
            {
                Playlist playlist = snapshot.getValue(Playlist.class);
                result.add(playlist);
            }
        }
        return result;
    }
    public static int getMaxId(DataSnapshot dataSnapshot, int id_nguoidung)
    {
        int maxid = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("Playlist").getChildren())
        {
            maxid = snapshot.child("Id").getValue(Integer.class);
        }
        return maxid;
    }
}
