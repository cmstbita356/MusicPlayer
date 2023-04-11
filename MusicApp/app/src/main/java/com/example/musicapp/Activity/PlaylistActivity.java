package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.musicapp.Adapter.PlaylistAdapter;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.PlaylistData;
import com.example.musicapp.Model.PlaylistDetailData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistActivity extends AppCompatActivity {
    GridView gridView;
    ImageButton imageButton_back;
    Context context = this;
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_setting;
    ImageButton iB_library;
    ImageButton imageButton_addPlaylist;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        init();

        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Playlist> playlists = PlaylistData.getPlaylist(dataSnapshot, StorageData.id_user);
                ArrayList<Integer> listIdPlaylist = new ArrayList<>();
                for (Playlist item : playlists)
                {
                    listIdPlaylist.add(item.getId());
                }
                ArrayList<Integer> listQuantitySong = PlaylistDetailData.getListQuantitySong(dataSnapshot, listIdPlaylist);
                PlaylistAdapter adapter = new PlaylistAdapter(playlists, listQuantitySong, context);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context, PlaylistDetailActivity.class);
                        intent.putExtra("id_playlist", playlists.get(position).getId());
                        intent.putExtra("ten_playlist", playlists.get(position).getTen());
                        intent.putExtra("soluong_baihat", listQuantitySong.get(position));
                        startActivity(intent);
                    }
                });

                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Playlist playlist = playlists.get(position);
                        PopupMenu popupMenu = new PopupMenu(context, view);
                        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_2, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(item -> {
                            switch (item.getItemId()) {
                                case R.id.menu_open:
                                    Intent intent = new Intent(context, PlaylistDetailActivity.class);
                                    intent.putExtra("id_playlist", playlist.getId());
                                    intent.putExtra("ten_playlist", playlist.getTen());
                                    intent.putExtra("soluong_baihat", listQuantitySong.get(position));
                                    startActivity(intent);
                                    return true;
                                case R.id.menu_rename:
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                                    View mView = getLayoutInflater().inflate(R.layout.dialog_layout_changenameplaylist, null);

                                    EditText editText_namePlaylist = mView.findViewById(R.id.editText_namePlaylist);
                                    Button button_ok = mView.findViewById(R.id.button_ok);

                                    editText_namePlaylist.setText(playlist.getTen());

                                    mBuilder.setView(mView);
                                    AlertDialog dialog = mBuilder.create();
                                    dialog.show();

                                    button_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Object value =  editText_namePlaylist.getText().toString();
                                            String path = "Playlist/" + playlist.getId() + "/Ten";
                                            FirebaseHelper.editData(path, value);
                                            dialog.cancel();
                                        }
                                    });
                                    return true;
                                case R.id.menu_delete:
                                    String path = "Playlist/" + playlist.getId();
                                    FirebaseHelper.deleteData(path);
                                    ArrayList<String> keys = PlaylistDetailData.getKeysWithIdPlaylist(dataSnapshot, playlist.getId());
                                    for(String s : keys)
                                    {
                                        path = "PlaylistDetail/" + s;
                                        FirebaseHelper.deleteData(path);
                                    }
                                    return true;
                                default:
                                    return false;
                            }
                        });
                        popupMenu.show();
                        return true;
                    }
                });

                imageButton_addPlaylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_layout_addplaylist, null);

                        EditText editText_namePlaylist = mView.findViewById(R.id.editText_namePlaylist);
                        Button button_ok = mView.findViewById(R.id.button_ok);

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                        button_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String key = String.valueOf(PlaylistData.getMaxId(dataSnapshot) + 1);
                                Map<String, Object> childValues = new HashMap<>();
                                childValues.put("Id", PlaylistData.getMaxId(dataSnapshot) + 1);
                                childValues.put("Id_NguoiDung", StorageData.id_user);
                                childValues.put("Ten", editText_namePlaylist.getText().toString());
                                FirebaseHelper.addData("Playlist", key, childValues);
                                dialog.cancel();
                            }
                        });


                    }
                });
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
        gridView = findViewById(R.id.gridView);
        imageButton_back = findViewById(R.id.imageButton_back);
        iB_home = findViewById(R.id.iB_home);
        iB_search = findViewById(R.id.iB_search);
        iB_setting = findViewById(R.id.iB_setting);
        iB_library = findViewById(R.id.iB_library);
        imageButton_addPlaylist = findViewById(R.id.imageButton_addPlaylist);

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