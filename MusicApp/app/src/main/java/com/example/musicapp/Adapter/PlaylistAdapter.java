package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {
    ArrayList<Playlist> playlistData;
    ArrayList<Integer> listQuantitySong;
    Context context;

    public PlaylistAdapter(ArrayList<Playlist> playlistData, ArrayList<Integer> listQuantitySong, Context context) {
        this.playlistData = playlistData;
        this.listQuantitySong = listQuantitySong;
        this.context = context;
    }

    @Override
    public int getCount() {
        return playlistData.size();
    }

    @Override
    public Object getItem(int position) {
        return playlistData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return playlistData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyView v;
        if(convertView == null)
        {
            v = new MyView();
            convertView = inflater.inflate(R.layout.item_layout_playlist, null);
            v.imageView = convertView.findViewById(R.id.imageView);
            v.textView_name = convertView.findViewById(R.id.textView_name);
            v.textView_quantity = convertView.findViewById(R.id.textView_quantity);
            convertView.setTag(v);
        }
        else{
            v = (MyView) convertView.getTag();
        }
        String linkHinh = "https://cdn1.vectorstock.com/i/1000x1000/14/35/playlist-black-icon-button-logo-symbol-vector-14551435.jpg";
        Picasso.get().load(linkHinh).into(v.imageView);
        v.textView_name.setText(playlistData.get(position).getTen());
        String textQuantity = String.valueOf(listQuantitySong.get(position)) + " songs";
        v.textView_quantity.setText(textQuantity);
        return convertView;
    }


    private class MyView  {
        ImageView imageView;
        TextView textView_name;
        TextView textView_quantity;
    }
}
