package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class FavoriteSongData {
    public static ArrayList<Song> getListFavoriteSong(DataSnapshot dataSnapshot, int id_nguoidung)
    {
        ArrayList<Song> songList = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("YeuThich").getChildren()) {
            if(snapshot.child("Id_NguoiDung").getValue(Integer.class).equals(id_nguoidung))
            {
                int id_baihat = snapshot.child("Id_BaiHat").getValue(Integer.class);
                songList.add(SongData.getSongById(id_baihat, dataSnapshot));
            }
        }
        return songList;
    }
    public static boolean check(DataSnapshot dataSnapshot, int id_nguoidung, int id_baihat)
    {
        for (Song song : getListFavoriteSong(dataSnapshot, id_nguoidung))
        {
            if(id_baihat == song.getId())
            {
                return true;
            }
        }
        return false;
    }
    public static String getKeyFavoriteSong(DataSnapshot dataSnapshot, int id_nguoidung, int id_baihat)
    {
        for (DataSnapshot snapshot : dataSnapshot.child("YeuThich").getChildren()) {
            if(snapshot.child("Id_NguoiDung").getValue(Integer.class).equals(id_nguoidung) && snapshot.child("Id_BaiHat").getValue(Integer.class).equals(id_baihat))
            {
                return snapshot.getKey();
            }
        }
        return null;
    }
}
