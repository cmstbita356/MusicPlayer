package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.widget.AdapterView.AdapterContextMenuInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.FavoriteAdapter;
import com.example.musicapp.Model.FavoriteSongData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton imageButton_back;
    Context context = this;
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_setting;
    ImageButton iB_library;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();

        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Song> songs = FavoriteSongData.getListFavoriteSong(dataSnapshot, StorageData.id_user);
                FavoriteAdapter adapter = new FavoriteAdapter(songs, dataSnapshot, context);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
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