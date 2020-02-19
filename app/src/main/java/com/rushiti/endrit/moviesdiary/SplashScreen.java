package com.rushiti.endrit.moviesdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView title;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=(ImageView)findViewById(R.id.Logo);
        title=(TextView)findViewById(R.id.Tittle);
        animation=AnimationUtils.loadAnimation(this,R.anim.transition);
        logo.setAnimation(animation);
        title.setAnimation(animation);

        final Intent i=new Intent(this,MainActivity.class);
        Thread timer=new Thread()
        {
            public void run()
            {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
timer.start();
    }
}
