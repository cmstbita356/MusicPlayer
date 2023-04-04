package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.PlayActivity;
import com.example.musicapp.Model.FavoriteSongData;
import com.example.musicapp.Model.PlaylistDetail;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistDetailAdapter extends RecyclerView.Adapter<PlaylistDetailAdapter.ViewHolder> {
    ArrayList<Song> data = new ArrayList<>();
    int id_playlist;
    DataSnapshot dataSnapshot;
    Context context;

    public PlaylistDetailAdapter(ArrayList<Song> data, int id_playlist, DataSnapshot dataSnapshot, Context context) {
        this.data = data;
        this.id_playlist = id_playlist;
        this.dataSnapshot = dataSnapshot;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaylistDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_layout_playlistdetail, parent, false);

        PlaylistDetailAdapter.ViewHolder viewHolder = new PlaylistDetailAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistDetailAdapter.ViewHolder holder, int position) {
        Song song = data.get(position);
        holder.textView_ten.setText(song.getTen());
        holder.textView_casi.setText(song.getCaSi());
        Picasso.get().load(song.getHinh()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("id", song.getId());
                intent.putExtra("id_playlist", String.valueOf(id_playlist));
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v, song);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private void showPopupMenu(View view, Song song) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_1, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_listen:
                    Intent intent = new Intent(context, PlayActivity.class);
                    intent.putExtra("id", song.getId());
                    intent.putExtra("id_playlist", String.valueOf(id_playlist));
                    context.startActivity(intent);
                    return true;
                case R.id.menu_delete:
                    String path = "PlaylistDetail/" + PlaylistDetailData.getKeyPlaylistDetail(dataSnapshot, id_playlist, song.getId());
                    FirebaseHelper.deleteData(path);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_ten;
        TextView textView_casi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView_ten = itemView.findViewById(R.id.textView_ten);
            textView_casi = itemView.findViewById(R.id.textView_casi);
        }
    }
}
