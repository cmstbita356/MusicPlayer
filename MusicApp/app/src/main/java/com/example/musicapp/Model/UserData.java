package com.example.musicapp.Model;

import android.content.Intent;

import com.example.musicapp.Activity.HomeActivity;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;

public class UserData {
    public static User getUserById(DataSnapshot dataSnapshot, int id)
    {
        User user = new User();
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            if(snapshot.child("Id").getValue(Integer.class).equals(id))
            {
                return  snapshot.getValue(User.class);
            }
        }
        return null;
    }
    public static boolean checkLogin(DataSnapshot dataSnapshot, String taikhoan, String matkhau)
    {
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            if(snapshot.child("TaiKhoan").getValue(String.class).equals(taikhoan) && snapshot.child("MatKhau").getValue(String.class).equals(matkhau))
            {
                StorageData.id_user = snapshot.child("Id").getValue(Integer.class);
                return true;
            }
        }
        return false;
    }
    public static boolean CheckExisted(DataSnapshot dataSnapshot, String username)
    {
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            if(snapshot.child("TaiKhoan").getValue(String.class).equals(username))
            {
                return  false;
            }
        }
        return true;
    }
    public static int getMaxId(DataSnapshot dataSnapshot)
    {
        int maxid = 0;
        for (DataSnapshot snapshot : dataSnapshot.child("NguoiDung").getChildren())
        {
            maxid = snapshot.child("Id").getValue(Integer.class);
        }
        return maxid;
    }
}
