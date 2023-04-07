package com.example.musicapp.Model;

public class FeedBack {
    public FeedBack(){};
    public FeedBack(int id_NguoiDung, int star_vote, String feedback) {
        Id_NguoiDung = id_NguoiDung;
        Star_vote = star_vote;
        Feedback = feedback;
    }

    public int getId_NguoiDung() {
        return Id_NguoiDung;
    }

    public void setId_NguoiDung(int id_NguoiDung) {
        Id_NguoiDung = id_NguoiDung;
    }

    int Id_NguoiDung;

    public int getStar_vote() {
        return Star_vote;
    }

    public void setStar_vote(int star_vote) {
        Star_vote = star_vote;
    }

    int Star_vote;

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    String Feedback;

}
