package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FeedBackActivity extends AppCompatActivity {

    ImageButton star1,star2,star3,star4,star5;
    EditText editText_feedback;
    TextView voteStar;
    Button bt_feedback,bt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        init();


        FirebaseHelper.getDataChange(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bt_feedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String key = String.valueOf( StorageData.id_user);
                        Map<String, Object> childValues = new HashMap<>();
                        childValues.put("Id_NguoiDung", StorageData.id_user);
                        childValues.put("Star_vote", Integer.parseInt(voteStar.getText().toString()));
                        childValues.put("Feedback", editText_feedback.getText().toString());
                        FirebaseHelper.addData("FeedBack", key, childValues);
                        Toast.makeText(FeedBackActivity.this, "Nhận Xét Thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FeedBackActivity.this, "Quay lại Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                voteStar.setText("1");
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                voteStar.setText("2");
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                voteStar.setText("3");
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star));
                voteStar.setText("4");
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_select));
                voteStar.setText("5");
            }
        });



    }
    private void init(){
        editText_feedback=findViewById(R.id.editText_feedback);
        bt_feedback=findViewById(R.id.bt_feedback);
        bt_cancel=findViewById(R.id.bt_cancel);
        voteStar=findViewById(R.id.voteStar);
        star1=findViewById(R.id.iB_star1);
        star2=findViewById(R.id.iB_star2);
        star3=findViewById(R.id.iB_star3);
        star4=findViewById(R.id.iB_star4);
        star5=findViewById(R.id.iB_star5);

    }
}