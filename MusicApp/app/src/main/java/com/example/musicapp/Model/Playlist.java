package com.example.musicapp.Model;

public class Playlist {
    int Id;
    int Id_NguoiDung;
    String Ten;

    public Playlist() {
    }

    public Playlist(int id, int id_NguoiDung, String ten) {
        Id = id;
        Id_NguoiDung = id_NguoiDung;
        Ten = ten;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId_NguoiDung() {
        return Id_NguoiDung;
    }

    public void setId_NguoiDung(int id_NguoiDung) {
        Id_NguoiDung = id_NguoiDung;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
