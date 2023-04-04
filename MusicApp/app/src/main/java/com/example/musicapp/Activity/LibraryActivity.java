package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicapp.Model.UserData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LibraryActivity extends AppCompatActivity {
    LinearLayout favoriteSongs;
    LinearLayout playlists;
    LinearLayout downloadSongs;
    TextView textView_user;
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_setting;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        init();

        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView_user.setText(UserData.getUserById(dataSnapshot, StorageData.id_user).getTaiKhoan());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        favoriteSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        playlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(intent);
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

        miniLayout_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init()
    {
        favoriteSongs = findViewById(R.id.favoriteSongs);
        playlists = findViewById(R.id.playlists);
        downloadSongs = findViewById(R.id.downloadSongs);
        textView_user = findViewById(R.id.textView_user);
        iB_home = findViewById(R.id.iB_home);
        iB_search = findViewById(R.id.iB_search);
        iB_setting = findViewById(R.id.iB_setting);

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