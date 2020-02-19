package com.rushiti.endrit.moviesdiary;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    static ProductAdapter adapter;
    Dialog dialog;
    ImageView addFilm;
    SQLiteDatabase db;
    FilmaDb database;
    ImageView watchedMovie;
    static TextView clicktoaddmovies;
    static LinearLayout recycleviewlayout;
    private AdView mAdView;


    static List<Filmat> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("F0F28A5286B22A291BDD47218CD4F8A6").build();
        mAdView.loadAd(adRequest);


        dialog=new Dialog(this);
        database=new FilmaDb(this,"Filma_db",null,2);
        db=database.getWritableDatabase();

        clicktoaddmovies=(TextView)findViewById(R.id.ClickToAddMovies);
        recycleviewlayout=(LinearLayout)findViewById(R.id.recycleviewlayout);

        watchedMovie=(ImageView)findViewById(R.id.WatchedMovies);
        watchedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Watched_MoviesList.class);
                startActivity(intent);
            }
        });
        addFilm=(ImageView)findViewById(R.id.addFilm);
        addFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText addFilmEdit;
                Button addFilmButton;
                dialog.setContentView(R.layout.custompopup);
                addFilmEdit=(EditText)dialog.findViewById(R.id.addfilmedittext);
                addFilmButton=(Button)dialog.findViewById(R.id.addfilmBUTTON);
                addFilmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContentValues cv=new ContentValues();
                        cv.put("Emri",addFilmEdit.getText().toString());
                        db.insert("Filmi",null,cv);
                        productList.add(new Filmat(addFilmEdit.getText().toString()));
                        adapter.notifyDataSetChanged();
                        if(adapter.getItemCount()==0)
                        {
                            recycleviewlayout.setVisibility(View.INVISIBLE);
                            clicktoaddmovies.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            recycleviewlayout.setVisibility(View.VISIBLE);
                            clicktoaddmovies.setVisibility(View.INVISIBLE);

                        }
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        productList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ReadData();

        adapter=new ProductAdapter(this,productList);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()==0)
        {
            recycleviewlayout.setVisibility(View.INVISIBLE);
            clicktoaddmovies.setVisibility(View.VISIBLE);

        }
        else
        {
            recycleviewlayout.setVisibility(View.VISIBLE);
            clicktoaddmovies.setVisibility(View.INVISIBLE);

        }

    }


    public void ReadData()
    {
        Cursor c=db.rawQuery("SELECT * FROM Filmi",null);
        while (c.moveToNext())
        {
            productList.add(new Filmat(c.getString(c.getColumnIndex("Emri"))));
        }
        c.close();
    }
}
