package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.PlayActivity;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {
    ArrayList<Song> data;
    Context context;

    public FindAdapter(ArrayList<Song> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FindAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_layout_find, parent, false);

        FindAdapter.ViewHolder viewHolder = new FindAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FindAdapter.ViewHolder holder, int position) {
        Song song = data.get(position);
        holder.textView_ten.setText(song.getTen());
        holder.textView_casi.setText(song.getCaSi());
        Picasso.get().load(data.get(position).getHinh()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("id", song.getId());
                intent.putExtra("id_playlist", "khong");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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
