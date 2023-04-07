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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.FindAdapter;
import com.example.musicapp.Model.HistorySearch;
import com.example.musicapp.Model.HistorySearchData;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class    FindActivity extends AppCompatActivity {
    EditText editText_find;
    ImageButton bt_find;
    RecyclerView recyclerView_find;
    Context context = this;
    ImageButton iB_home;
    ImageButton iB_library;
    ImageButton iB_setting;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;
    TextView HistorySreach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        init();
        DatabaseReference Mdata= FirebaseDatabase.getInstance().getReference();
        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bt_find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HistorySearch his=new HistorySearch( StorageData.id_user,editText_find.getText().toString());
                        Mdata.child("History_Search").push().setValue(his);
                        ArrayList<Song> listSong = SongData.getSongByName(editText_find.getText().toString(), dataSnapshot);
                        FindAdapter adapter = new FindAdapter(listSong, context);
                        recyclerView_find.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                        recyclerView_find.setAdapter(adapter);
                    }
                });
                HistorySreach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ArrayList<String> historySearch = HistorySearchData.getListHistory(dataSnapshot, StorageData.id_user);
                        HistorySearchAdapter r = new HistorySearchAdapter(historySearch, dataSnapshot, context);
                        recyclerView_find.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView_find.setAdapter(r);
                    }
                });
                Intent intent=getIntent();
                if(intent.getStringExtra("history")!=null)
                {
                    editText_find.setText(intent.getStringExtra("history"));
                    bt_find.callOnClick();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        iB_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
        editText_find = findViewById(R.id.editText_find);
        bt_find = findViewById(R.id.bt_find);
        recyclerView_find = findViewById(R.id.recyclerView_find);
        iB_home = findViewById(R.id.iB_home);
        iB_library = findViewById(R.id.iB_library);
        iB_setting = findViewById(R.id.iB_setting);
        HistorySreach=findViewById(R.id.tv_historysearch);

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