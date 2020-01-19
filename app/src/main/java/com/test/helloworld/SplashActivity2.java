package com.test.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity2 extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        hideNavBar();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        iv = findViewById(R.id.logo_splash);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splash_transition);
        iv.startAnimation(myanim);

        Thread thread = new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(4000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(SplashActivity2.this, MainActivity.class);
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        };
        thread.start();

    }

    @Override
    protected void onResume(){
        super.onResume();
        hideNavBar();
    }

    public void hideNavBar(){
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
