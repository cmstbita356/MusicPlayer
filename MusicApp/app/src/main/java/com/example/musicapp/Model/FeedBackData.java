package com.example.musicapp.Model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class FeedBackData {
    public static ArrayList<FeedBack> getAllFeedBack(DataSnapshot dataSnapshot)
    {
        ArrayList<FeedBack> ListFB = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.child("FeedBack").getChildren()) {
            FeedBack FB = snapshot.getValue(FeedBack.class);
            ListFB.add(FB);
        }
        return ListFB;
    }


    public static FeedBack getMyFeedBack(DataSnapshot dataSnapshot, int id)
    {
        FeedBack FB = new FeedBack();
        for (DataSnapshot snapshot : dataSnapshot.child("FeedBack").getChildren()) {
            if(snapshot.child("id").getValue(Integer.class).equals(id))
            {
                FB = snapshot.getValue(FeedBack.class);
            }
        }
        return FB;
    }
    public static int getMaxId(DataSnapshot dataSnapshot)
    {
        int maxid = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("FeedBack").getChildren())
        {
            maxid = snapshot.child("Id_NguoiDung").getValue(Integer.class);
        }
        return maxid;
    }
}
