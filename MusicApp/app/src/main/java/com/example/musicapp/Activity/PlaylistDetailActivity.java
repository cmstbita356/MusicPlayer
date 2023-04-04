package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicapp.Adapter.PlayAdapter;
import com.example.musicapp.Adapter.PlaylistDetailAdapter;
import com.example.musicapp.Model.PlaylistDetail;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistDetailActivity extends AppCompatActivity {
    ImageView imageView_playlist;
    TextView textView_playlistName;
    TextView textView_quantitySong;
    RecyclerView recyclerView;
    ImageButton imageButton_back;
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_setting;
    ImageButton iB_library;
    Context context = this;
    ArrayList<Song> songs = new ArrayList<>();

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);
        init();
        String linkHinh = "https://cdn1.vectorstock.com/i/1000x1000/14/35/playlist-black-icon-button-logo-symbol-vector-14551435.jpg";
        Picasso.get().load(linkHinh).into(imageView_playlist);

        Intent intent = getIntent();
        textView_playlistName.setText(intent.getStringExtra("ten_playlist"));
        String textQuantity = String.valueOf(intent.getIntExtra("soluong_baihat", 0)) + " songs";
        int id_playlist = intent.getIntExtra("id_playlist", 0);
        textView_quantitySong.setText(textQuantity);
        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                songs = PlaylistDetailData.getSongsByIdPlaylist(dataSnapshot, id_playlist);
                PlaylistDetailAdapter adapter = new PlaylistDetailAdapter(songs, id_playlist, dataSnapshot, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iB_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        iB_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindActivity.class);
                startActivity(intent);
            }
        });

        iB_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        iB_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
                startActivity(intent);
            }
        });

        miniLayout_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);

                context.startActivity(intent);
            }
        });
    }
    private void init()
    {
        imageView_playlist = findViewById(R.id.imageView_playlist);
        textView_playlistName = findViewById(R.id.textView_playlistName);
        textView_quantitySong = findViewById(R.id.textView_quantitySong);
        recyclerView = findViewById(R.id.recyclerView);
        imageButton_back = findViewById(R.id.imageButton_back);
        iB_home = findViewById(R.id.iB_home);
        iB_search = findViewById(R.id.iB_search);
        iB_setting = findViewById(R.id.iB_setting);
        iB_library = findViewById(R.id.iB_library);

        miniLayout_Play = findViewById(R.id.miniLayout_Play);
        bt_play = findViewById(R.id.bt_play);
        txv_namesong = findViewById(R.id.txv_namesong);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("PlayPrefs", Context.MODE_PRIVATE);
        Functions.miniLayoutPlay(miniLayout_Play, txv_namesong, bt_play, bt_next, bt_previous, sharedPreferences);
    }
}