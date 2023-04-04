package com.example.musicapp.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.musicapp.Activity.PlayActivity;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Functions {
    private static Song song;
    private static ArrayList<Song> ListSong;
    public static void miniLayoutPlay(LinearLayout layout, TextView ten, ImageButton bt_play, ImageButton bt_next, ImageButton bt_previous, SharedPreferences sharedPreferences)
    {
        layout.setVisibility(View.GONE);
        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                song = SongData.getSongById(sharedPreferences.getInt("id_song", 0), snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String json = sharedPreferences.getString("playlist", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Song>>(){}.getType();
        ListSong = gson.fromJson(json, type);

        if(StorageData.isPlayed)
        {
            layout.setVisibility(View.VISIBLE);
            ten.setText(sharedPreferences.getString("name_song", "none"));
            if (StorageData.mediaPlayer.isPlaying()){
                bt_play.setImageResource(R.drawable.ic_pause);
            } else {
                bt_play.setImageResource(R.drawable.ic_play);
            }
        }
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StorageData.mediaPlayer.isPlaying()){
                    StorageData.mediaPlayer.pause();
                    bt_play.setImageResource(R.drawable.ic_play);
                } else {
                    StorageData.mediaPlayer.start();
                    bt_play.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong(ten, sharedPreferences);
            }
        });
        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong(ten, sharedPreferences);
            }
        });

        StorageData.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (StorageData.isRepeat) {
                    mp.seekTo(0);
                    mp.start();
                } else {
                    nextSong(ten, sharedPreferences);
                }

            }
        });

    }
    private static void nextSong(TextView ten, SharedPreferences sharedPref)
    {
        try {
            int index = 0;
            for (int i = 0; i < ListSong.size(); i++) {
                if (ListSong.get(i).getId() == song.getId()) {
                    index = i;
                    break;
                }
            }
            song = ListSong.get(index + 1);
            new PlaySong().execute(song.getLink());
        } catch (IndexOutOfBoundsException e) {
            song = ListSong.get(0);
            new PlaySong().execute(song.getLink());
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id_song", song.getId());
        editor.putString("name_song", song.getTen());
        editor.apply();
        ten.setText(song.getTen());
    }
    private static void previousSong(TextView ten, SharedPreferences sharedPref)
    {
        try {
            int index = 0;
            for (int i = 0; i < ListSong.size(); i++) {
                if (ListSong.get(i).getId() == song.getId()) {
                    index = i;
                    break;
                }
            }
            song = ListSong.get(index - 1);
            new PlaySong().execute(song.getLink());
        } catch (IndexOutOfBoundsException e) {
            song = ListSong.get(0);
            new PlaySong().execute(song.getLink());
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id_song", song.getId());
        editor.putString("name_song", song.getTen());
        editor.apply();
        ten.setText(song.getTen());
    }
    public static class PlaySong extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String songLink) {
            super.onPostExecute(songLink);
            try {
                StorageData.mediaPlayer.reset();
                StorageData.mediaPlayer.setDataSource(songLink);
                StorageData.mediaPlayer.prepare();
                StorageData.mediaPlayer.start();

                StorageData.mediaPlayer.seekTo(0);


                StorageData.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (StorageData.isRepeat) {
                            mp.seekTo(0);
                            mp.start();
                        } else {
                            try {
                                int index = 0;
                                for (int i = 0; i < ListSong.size(); i++) {
                                    if (ListSong.get(i).getId() == song.getId()) {
                                        index = i;
                                        break;
                                    }
                                }

                                new PlaySong().execute(song.getLink());
                            } catch (IndexOutOfBoundsException e) {
                                song = ListSong.get(0);
                                new PlaySong().execute(song.getLink());
                            }
                        }

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
