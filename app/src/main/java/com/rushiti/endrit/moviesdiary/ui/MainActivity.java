package com.rushiti.endrit.moviesdiary.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.rushiti.endrit.moviesdiary.ui.dialog.NewFilmAddedCallback;
import com.rushiti.endrit.moviesdiary.R;
import com.rushiti.endrit.moviesdiary.ui.dialog.NewFilmDialog;

public class MainActivity extends AppCompatActivity {
    NewFilmAddedCallback newFilmAddedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAds();
    }

    private void initAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) { }
        });
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("F0F28A5286B22A291BDD47218CD4F8A6").build();
        adView.loadAd(adRequest);
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        PageTabAdapter tabsPagerAdapter = new PageTabAdapter(getSupportFragmentManager(), 0);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.main_tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton addNewFilm = findViewById(R.id.add_new_film);
        addNewFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionAdd();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.menu_licences) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onActionAdd() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        NewFilmDialog dialogFragment = new NewFilmDialog(newFilmAddedCallback);
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    public void setCallback(NewFilmAddedCallback newFilmAddedCallback) {
        this.newFilmAddedCallback = newFilmAddedCallback;
    }
}
