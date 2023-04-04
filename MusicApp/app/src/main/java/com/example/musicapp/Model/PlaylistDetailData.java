package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class PlaylistDetailData {
    public static int getQuantitySong(DataSnapshot dataSnapshot, int id_playlist) {
        int count = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren()) {
            if (snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist)) {
                count = count + 1;
            }
        }
        return count;
    }

    public static ArrayList<Integer> getListQuantitySong(DataSnapshot dataSnapshot, ArrayList<Integer> listIdPlaylist) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i : listIdPlaylist) {
            result.add(getQuantitySong(dataSnapshot, i));
        }
        return result;
    }

    public static ArrayList<Song> getSongsByIdPlaylist(DataSnapshot dataSnapshot, int id_playlist) {
        ArrayList<Song> result = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren()) {
            if (snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist)) {
                result.add(SongData.getSongById(snapshot.child("Id_BaiHat").getValue(Integer.class), dataSnapshot));
            }
        }
        return result;
    }

    public static boolean checkExisted(DataSnapshot dataSnapshot, int id_playlist, int id_baihat) {
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren()) {
            if (snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist) && snapshot.child("Id_BaiHat").getValue(Integer.class).equals(id_baihat)) {
                return true;
            }
        }
        return false;
    }

    public static String getKeyPlaylistDetail(DataSnapshot dataSnapshot, int id_playlist, int id_baihat) {
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren()) {
            if (snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist) && snapshot.child("Id_BaiHat").getValue(Integer.class).equals(id_baihat)) {
                return snapshot.getKey();
            }
        }
        return null;
    }
    public static ArrayList<String> getKeysWithIdPlaylist(DataSnapshot dataSnapshot, int id_playlist)
    {
        ArrayList<String> result = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("PlaylistDetail").getChildren())
        {
            if(snapshot.child("Id_Playlist").getValue(Integer.class).equals(id_playlist))
            {
                result.add(snapshot.getKey());
            }
        }
        return result;
    }
    public static boolean checkSongAdded(DataSnapshot dataSnapshot, int id_nguoidung, int id_baihat) {
        ArrayList<Playlist> playlists = PlaylistData.getPlaylist(dataSnapshot, id_nguoidung);
        for (Playlist item : playlists) {
            if (checkExisted(dataSnapshot, item.getId(), id_baihat)) {
                return true;
            }
        }
        return false;
    }

}

