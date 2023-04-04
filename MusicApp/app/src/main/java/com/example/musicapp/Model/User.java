package com.example.musicapp.Model;

public class User {
    int Id;
    String TaiKhoan;
    String MatKhau;
    public User(){}
    public User(int id, String taiKhoan, String matKhau) {
        Id = id;
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
