package com.example.musicapp.Model;

public class HistorySearch {
    int Id;
    String Search;

    public HistorySearch(){}

    public HistorySearch (int id, String search){
        Id=id;
        Search=search;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSearch() {
        return Search;
    }

    public void setSearch(String search) {
        Search = search;
    }
}
