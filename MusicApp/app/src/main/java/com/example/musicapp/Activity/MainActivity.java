package com.example.musicapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.HomeAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongData;
import com.example.musicapp.Model.UserData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Context context = this;
    Button button_submit;
    Button button_dangky;
    EditText editText_taikhoan;
    EditText editText_matkhau;
    TextView textView_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        button_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
        FirebaseHelper.getData(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                button_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taikhoan = editText_taikhoan.getText().toString();
                        String matkhau = editText_matkhau.getText().toString();
                        if (UserData.checkLogin(dataSnapshot, taikhoan, matkhau))
                        {
                            Intent intent = new Intent(context, HomeActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            textView_error.setText("Incorrect username or password.");
                            editText_taikhoan.setText("");
                            editText_matkhau.setText("");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void init()
    {
        button_submit = findViewById(R.id.button_submit);
        editText_taikhoan = findViewById(R.id.editText_taikhoan);
        editText_matkhau = findViewById(R.id.editText_matkhau);
        button_dangky = findViewById(R.id.button_dangky);
        textView_error = findViewById(R.id.textView_error);
    }
}