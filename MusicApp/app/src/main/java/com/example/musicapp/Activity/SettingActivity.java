package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.FeedBackAdapter;
import com.example.musicapp.Model.FeedBack;
import com.example.musicapp.Model.FeedBackData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.Functions;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ImageButton iB_home;
    ImageButton iB_search;
    ImageButton iB_library;
    Spinner spinner_sleep;
    SeekBar seekBar_volume;
    LinearLayout logOut;
    Context context = this;

    LinearLayout miniLayout_Play;
    TextView txv_namesong;
    ImageButton bt_play;
    ImageButton bt_previous;
    ImageButton bt_next;

    Button addFeedback;
    RecyclerView recyclerVieww;
    TextView countStar;
    ArrayList<FeedBack> lisFB=new ArrayList<>();
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lisFB=FeedBackData.getAllFeedBack(dataSnapshot);
                countStar.setText(CountStar()+"/5 ");
                FeedBackAdapter adapter = new FeedBackAdapter(lisFB, dataSnapshot, context);
                recyclerVieww.setLayoutManager(new LinearLayoutManager(context));
                recyclerVieww.setAdapter(adapter);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.RB_all:
                                lisFB=FeedBackData.getAllFeedBack(dataSnapshot);
                                FeedBackAdapter adapter = new FeedBackAdapter(lisFB, dataSnapshot, context);
                                recyclerVieww.setLayoutManager(new LinearLayoutManager(context));
                                recyclerVieww.setAdapter(adapter);
                                break;
                            case R.id.RB_positive:
                                lisFB=FeedBackData.getAllFeedBack(dataSnapshot);
                                ArrayList<FeedBack>fb=new ArrayList<FeedBack>();
                                for(int i=0;i<lisFB.size();i++){
                                    if(lisFB.get(i).getStar_vote()>=4){
                                        fb.add(lisFB.get(i));
                                    }
                                }
                                adapter = new FeedBackAdapter(fb, dataSnapshot, context);
                                recyclerVieww.setLayoutManager(new LinearLayoutManager(context));
                                recyclerVieww.setAdapter(adapter);
                                break;
                            case R.id.RB_ascending:
                                lisFB=FeedBackData.getAllFeedBack(dataSnapshot);
                                for(int i=0;i<lisFB.size();i++){
                                    for(int j=0;j<lisFB.size()-1;j++){
                                        if(lisFB.get(j).getStar_vote()>lisFB.get(j+1).getStar_vote()){
                                            FeedBack temp=lisFB.get(j);
                                            lisFB.set(j,lisFB.get(j+1));
                                            lisFB.set(j+1,temp);
                                        }
                                    }
                                }
                                adapter = new FeedBackAdapter(lisFB, dataSnapshot, context);
                                recyclerVieww.setLayoutManager(new LinearLayoutManager(context));
                                recyclerVieww.setAdapter(adapter);
                                break;
                        }
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        //Khôi phục giá trị
        SharedPreferences sharedPreferences = getSharedPreferences("SettingPrefs", Context.MODE_PRIVATE);
        int progress = sharedPreferences.getInt("volume", 50);
        seekBar_volume.setProgress(progress);
        float volume = progress / 100f;
        StorageData.mediaPlayer.setVolume(volume, volume);

        seekBar_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Tính toán giá trị âm lượng mới từ giá trị của SeekBar (0-100)
                float volume = progress / 100f;
                // Cập nhật âm lượng của MediaPlayer
                StorageData.mediaPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Lưu giá trị
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("volume", seekBar.getProgress());
                editor.apply();
            }
        });

        // Sleep timer
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sleep.setAdapter(adapter);
        spinner_sleep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = spinner_sleep.getSelectedItem().toString();
                int value = Integer.parseInt(selectedValue);
                int valuems = value * 60 * 1000;
                if(value != 0)
                {
                    StorageData.timer = new CountDownTimer( valuems ,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            StorageData.mediaPlayer.stop();
                        }
                    }.start();
                    String s = "After " + selectedValue + " minute music will stop";
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(StorageData.timer != null)
                    {
                        StorageData.timer.cancel();
                        Toast.makeText(getApplicationContext(), "Stop sleep timer", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout_logout, null);

                Button button_no = mView.findViewById(R.id.button_no);
                Button button_yes = mView.findViewById(R.id.button_yes);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                button_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StorageData.mediaPlayer.reset();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                });
                button_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        ///////////
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

        iB_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LibraryActivity.class);
                startActivity(intent);
            }
        });

        addFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FeedBackActivity.class);
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
        iB_home = findViewById(R.id.iB_home);
        iB_search = findViewById(R.id.iB_search);
        iB_library = findViewById(R.id.iB_library);
        spinner_sleep = findViewById(R.id.spinner_sleep);
        seekBar_volume = findViewById(R.id.seekBar_volume);
        logOut = findViewById(R.id.logOut);
        addFeedback=findViewById(R.id.addFeedback);
        recyclerVieww=findViewById(R.id.recyclerVieww);
        countStar=findViewById(R.id.countStar);


        miniLayout_Play = findViewById(R.id.miniLayout_Play);
        bt_play = findViewById(R.id.bt_play);
        txv_namesong = findViewById(R.id.txv_namesong);
        bt_next = findViewById(R.id.bt_next);
        bt_previous = findViewById(R.id.bt_previous);
        radioGroup=findViewById(R.id.RadioGroup);
    }
    private float CountStar(){
        float count=0;
        for(int i=0;i<lisFB.size();i++){
            count+=lisFB.get(i).getStar_vote();
        }
        return Math.round(count/lisFB.size()) ;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("PlayPrefs", Context.MODE_PRIVATE);
        Functions.miniLayoutPlay(miniLayout_Play, txv_namesong, bt_play, bt_next, bt_previous, sharedPreferences);
    }

}