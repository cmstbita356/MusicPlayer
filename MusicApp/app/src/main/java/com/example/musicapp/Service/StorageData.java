package com.example.musicapp.Service;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.musicapp.R;

public class StorageData {
    public static int id_user;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static CountDownTimer timer;
    public static boolean isPlayed = false;
    public static boolean isRepeat = false;
}
