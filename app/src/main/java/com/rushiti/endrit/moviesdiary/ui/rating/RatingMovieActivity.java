package com.rushiti.endrit.moviesdiary.ui.rating;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.db.FilmaDb;

public class RatingMovieActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_rating_for_movie);

        MobileAds.initialize(this, "ca-app-pub-4744143765079443~9291563366");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4744143765079443/3125556360");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F0F28A5286B22A291BDD47218CD4F8A6").build());

        description = findViewById(R.id.Description);
        ratingImage = findViewById(R.id.RatingImage);
        ratingText = findViewById(R.id.ratingText);
        ratingBar = findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText.setText("" + ratingBar.getRating());
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
        emriFilmit = getIntent().getStringExtra("EmriFilmit");
        emrifilmit = findViewById(R.id.emriFilmit);
        emrifilmit.setText(emriFilmit);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmWatchedMovie(final View view) {
        filmathelper = new FilmaDb(this, "Filma_db", null, 2);
        db = filmathelper.getWritableDatabase();
        confirmwatchedMovie = findViewById(R.id.confirmWatchedMovie);

        if (!emrifilmit.getText().toString().equals("") && !ratingText.getText().toString().equals("")) {
            ContentValues cv = new ContentValues();
            cv.put("Emri", emrifilmit.getText().toString());
            cv.put("Rate", ratingText.getText().toString());
            cv.put("Description", description.getText().toString());
            db.insert("WatchedMovie", null, cv);
            db.delete("Filmi", "Emri=?", new String[]{emrifilmit.getText().toString()});
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        } else {
            Toast.makeText(this, "Please fill all the informations", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
