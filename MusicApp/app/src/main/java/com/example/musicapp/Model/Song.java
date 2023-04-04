package com.example.musicapp.Model;

public class Song {
    int Id;
    String Ten;
    String CaSi;
    String Hinh;
    String Link;
    String NgonNgu;
    public Song() {

    }

    public Song(int id, String ten, String caSi, String hinh, String link, String ngonNgu) {
        Id = id;
        Ten = ten;
        CaSi = caSi;
        Hinh = hinh;
        Link = link;
        NgonNgu = ngonNgu;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getCaSi() {
        return CaSi;
    }

    public void setCaSi(String caSi) {
        CaSi = caSi;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getNgonNgu() {
        return NgonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        NgonNgu = ngonNgu;
    }
}
