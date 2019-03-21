package com.example.renad.exchangeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        iv=(ImageView)findViewById(R.id.iv);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.mytransaction);
        iv.startAnimation(animation);

        final Intent intint= new Intent(this,First_page.class);

        Thread timer= new Thread(){
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    startActivity(intint);
                    finish();
                }
            }
        };

        timer.start();
    }
}
