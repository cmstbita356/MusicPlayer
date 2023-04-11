package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Activity.FindActivity;
import com.example.musicapp.Model.HistorySearchData;
import com.example.musicapp.R;
import com.example.musicapp.Service.FirebaseHelper;
import com.example.musicapp.Service.StorageData;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> {
    ArrayList<String> data;
    DataSnapshot dataSnapshot;
    Context context;

    public HistorySearchAdapter(ArrayList<String> data, DataSnapshot dataSnapshot, Context context) {
        this.data = data;
        this.dataSnapshot = dataSnapshot;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history_search, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String historyS=data.get(position);
        holder.tv_history.setText(historyS);
        holder.tv_history.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopupMenu(view, historyS);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_history;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_history = itemView.findViewById(R.id.tv_historyy);
        }

    }
    private void showPopupMenu(View view, String his) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_search, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_deleteSearch:
                    String path = "History_Search/" + HistorySearchData.getKeyHis(dataSnapshot, StorageData.id_user, his);
                    FirebaseHelper.deleteData(path);
                    Intent intentA = new Intent(context, FindActivity.class);
                    context.startActivity(intentA);
                    return true;
                case R.id.menu_findSearch:
                    Intent intent = new Intent(context, FindActivity.class);
                    intent.putExtra("history", his);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

}
