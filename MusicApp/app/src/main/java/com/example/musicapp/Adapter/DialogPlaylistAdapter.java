package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.PlaylistData;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.UserData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DialogPlaylistAdapter extends RecyclerView.Adapter<DialogPlaylistAdapter.ViewHolder> {
    ArrayList<Playlist> data;
    int id_song;
    //DataSnapshot dataSnapshot;
    Context context;

    public DialogPlaylistAdapter(ArrayList<Playlist> data, int id_song, Context context) {
        this.data = data;
        this.id_song = id_song;
        this.context = context;
    }

    @NonNull
    @Override
    public DialogPlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dialog_layout_playlist, parent, false);

        DialogPlaylistAdapter.ViewHolder viewHolder = new DialogPlaylistAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DialogPlaylistAdapter.ViewHolder holder, int position) {
        Playlist playlist = data.get(position);
        String linkHinh = "https://cdn1.vectorstock.com/i/1000x1000/14/35/playlist-black-icon-button-logo-symbol-vector-14551435.jpg";
        Picasso.get().load(linkHinh).into(holder.imageView);
        holder.textView_name.setText(playlist.getTen());


        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isExisted = PlaylistDetailData.checkExisted(dataSnapshot, playlist.getId(), id_song);
                if(isExisted)
                {
                    holder.imageButton_check.setImageResource(R.drawable.ic_checked);
                }
                else
                {
                    holder.imageButton_check.setImageResource(R.drawable.ic_uncheck);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PlaylistDetailData.checkExisted(dataSnapshot, playlist.getId(), id_song))
                        {
                            holder.imageButton_check.setImageResource(R.drawable.ic_uncheck);
                            String path = "PlaylistDetail/" + PlaylistDetailData.getKeyPlaylistDetail(dataSnapshot, playlist.getId(), id_song);
                            FirebaseHelper.deleteData(path);
                            //isExisted = false;

                        }
                        else
                        {
                            holder.imageButton_check.setImageResource(R.drawable.ic_checked);
                            Map<String, Object> childValues = new HashMap<>();
                            childValues.put("Id_BaiHat", id_song);
                            childValues.put("Id_Playlist", playlist.getId());
                            FirebaseHelper.addData("PlaylistDetail", childValues);
                            Toast.makeText(context, "Added to " + playlist.getTen(), Toast.LENGTH_SHORT).show();
                            //isExisted = true;
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_name;
        ImageButton imageButton_check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView_name = itemView.findViewById(R.id.textView_name);
            imageButton_check = itemView.findViewById(R.id.imageButton_check);
        }
    }
}
