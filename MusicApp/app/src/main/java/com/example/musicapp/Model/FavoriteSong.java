package com.example.musicapp.Model;

public class FavoriteSong {
    int Id_NguoiDung;
    int Id_BaiHat;

    public FavoriteSong(){}
    public FavoriteSong(int id_NguoiDung, int id_BaiHat) {
        Id_NguoiDung = id_NguoiDung;
        Id_BaiHat = id_BaiHat;
    }

    public int getId_NguoiDung() {
        return Id_NguoiDung;
    }

    public void setId_NguoiDung(int id_NguoiDung) {
        Id_NguoiDung = id_NguoiDung;
    }

    public int getId_BaiHat() {
        return Id_BaiHat;
    }

    public void setId_BaiHat(int id_BaiHat) {
        Id_BaiHat = id_BaiHat;
    }
}
