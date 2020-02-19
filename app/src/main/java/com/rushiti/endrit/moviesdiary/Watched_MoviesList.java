package com.rushiti.endrit.moviesdiary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Watched_MoviesList extends AppCompatActivity {

    static ArrayList<FilmateShikuara> productList;
    SQLiteDatabase db;
    FilmaDb dbHelper;
    RecyclerView recyclerView;
    static WatchedfilmsAdapter adapterW;
    ImageView homeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched__movies_list);

        SearchView searchView=(SearchView) findViewById(R.id.SearchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterW.getFilter().filter(newText);
                return false;
            }
        });
        homeIcon = (ImageView) findViewById(R.id.HomeIcon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new FilmaDb(this, "Filma_db", null, 2);
        db = dbHelper.getWritableDatabase();
        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Read();
        adapterW = new WatchedfilmsAdapter(this,productList);
        recyclerView.setAdapter(adapterW);
    }

    public void Read() {

        Cursor c = db.rawQuery("SELECT * FROM WatchedMovie", null);
        while (c.moveToNext()) {
            String moviename = c.getString(c.getColumnIndex("Emri"));
            Double rate = c.getDouble(c.getColumnIndex("Rate"));
            String description=c.getString(c.getColumnIndex("Description"));
            productList.add(new FilmateShikuara(moviename, rate,description));
        }

    }
}
