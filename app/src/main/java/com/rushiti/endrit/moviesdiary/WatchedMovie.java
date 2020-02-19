package com.rushiti.endrit.moviesdiary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class WatchedMovie extends AppCompatActivity {

    String emriFilmit;
    TextView emrifilmit;
    RatingBar ratingBar;
    TextView ratingText;
    SQLiteDatabase db;
    FilmaDb filmathelper;
    Button confirmwatchedMovie;
    ImageView houseicon;
    ImageView ratingImage;
    EditText description;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_movie);

        MobileAds.initialize(this, "ca-app-pub-4744143765079443~9291563366");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4744143765079443/3125556360");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F0F28A5286B22A291BDD47218CD4F8A6").build());



        houseicon=(ImageView)findViewById(R.id.houseicon);
        houseicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),MainActivity.class);
                startActivity(i);
            }
        });
        description=(EditText)findViewById(R.id.Description);
        ratingImage=(ImageView)findViewById(R.id.RatingImage);
        ratingText=(TextView)findViewById(R.id.ratingText);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
              ratingText.setText(""+ratingBar.getRating());
                float i = ratingBar.getRating();
                if (i < 3.0f) {
                    ratingImage.setImageResource(R.drawable.veryangry);
                } else if (i == 3.0f) {
                    ratingImage.setImageResource(R.drawable.normal);
                } else if (i > 3.0f) {
                    ratingImage.setImageResource(R.drawable.veryhappy);
                }
            }
        });
        emriFilmit=getIntent().getStringExtra("EmriFilmit");
        emrifilmit=(TextView)findViewById(R.id.emriFilmit);
        emrifilmit.setText(emriFilmit);

    }

    public void ConfimWatchedMovie(final View view)
    {

        mInterstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Intent intent=new Intent(WatchedMovie.this,Watched_MoviesList.class);
                startActivity(intent);
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        filmathelper=new FilmaDb(this,"Filma_db",null,2);
        db=filmathelper.getWritableDatabase();
        confirmwatchedMovie=(Button)findViewById(R.id.confirmWatchedMovie);

        if(emrifilmit.getText().toString()!="" && ratingText.getText().toString()!="") {
            ContentValues cv = new ContentValues();
            cv.put("Emri", emrifilmit.getText().toString());
            cv.put("Rate", ratingText.getText().toString());
            cv.put("Description",description.getText().toString());
            db.insert("WatchedMovie", null, cv);
            db.delete("Filmi", "Emri=?", new String[]{emrifilmit.getText().toString()});
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
                Intent intent=new Intent(view.getContext(),Watched_MoviesList.class);
                startActivity(intent);
            }


        }
        else {
            Toast.makeText(this, "Please fill all the informations", Toast.LENGTH_SHORT).show();
        }
    }
}
