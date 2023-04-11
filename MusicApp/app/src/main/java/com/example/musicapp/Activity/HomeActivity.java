package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.musicapp.Adapter.HomeAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ImageButton iB_search;
    ImageButton iB_library;
    ImageButton iB_setting;

    RecyclerView recyclerView_Anh;
    RecyclerView recyclerView_VietNam;
    RecyclerView recyclerView_Khac;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();



        SharedPreferences sharedPreferences = getSharedPreferences("SettingPrefs", Context.MODE_PRIVATE);
        int progress = sharedPreferences.getInt("volume", 50);
        float volume = progress / 100f;
        StorageData.mediaPlayer.setVolume(volume, volume);


        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                HomeAdapter adapter_Anh = new HomeAdapter(SongData.getSongByLanguage("Anh", dataSnapshot), context);
                recyclerView_Anh.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_Anh.setAdapter(adapter_Anh);

                HomeAdapter adapter_VietNam = new HomeAdapter(SongData.getSongByLanguage("Viet", dataSnapshot), context);
                recyclerView_VietNam.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_VietNam.setAdapter(adapter_VietNam);

                HomeAdapter adapter_Khac = new HomeAdapter(SongData.getSongByLanguage("Khac", dataSnapshot), context);
                recyclerView_Khac.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView_Khac.setAdapter(adapter_Khac);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        iB_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindActivity.class);
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
        iB_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        // minilayout play
        miniLayout_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);

                context.startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("PlayPrefs", Context.MODE_PRIVATE);
        Functions.miniLayoutPlay(miniLayout_Play, txv_namesong, bt_play, bt_next, bt_previous, sharedPreferences);
    }

    private void init()
    {
        iB_search = findViewById(R.id.iB_search);
        recyclerView_Anh = findViewById(R.id.recyclerView_Anh);
        recyclerView_VietNam = findViewById(R.id.recyclerView_VietNam);
        recyclerView_Khac = findViewById(R.id.recyclerView_Khac);
        iB_library = findViewById(R.id.iB_library);
        iB_setting = findViewById(R.id.iB_setting);

        miniLayout_Play = findViewById(R.id.miniLayout_Play);
        bt_play = findViewById(R.id.bt_play);
        txv_namesong = findViewById(R.id.txv_namesong);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
    }
}