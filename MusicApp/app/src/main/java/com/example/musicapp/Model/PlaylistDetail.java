package com.example.musicapp.Model;

public class PlaylistDetail {
    int Id_Playlist;
    int Id_BaiHat;

    public PlaylistDetail() {
    }

    public PlaylistDetail(int id_Playlist, int id_BaiHat) {
        Id_Playlist = id_Playlist;
        Id_BaiHat = id_BaiHat;
    }

    public int getId_Playlist() {
        return Id_Playlist;
    }

    public void setId_Playlist(int id_Playlist) {
        Id_Playlist = id_Playlist;
    }

    public int getId_BaiHat() {
        return Id_BaiHat;
    }

    public void setId_BaiHat(int id_BaiHat) {
        Id_BaiHat = id_BaiHat;
    }
}
