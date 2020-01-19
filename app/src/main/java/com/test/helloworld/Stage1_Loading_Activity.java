package com.test.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Stage1_Loading_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_loading_screen);
        hideNavBar();

        final Intent svc=new Intent(this, BackgroundSoundService.class);
        stopService(svc);

        final Intent loading_sound=new Intent(this, LoadingSoundService.class);
        startService(loading_sound);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        Thread thread = new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(10000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(Stage1_Loading_Activity.this, Stage1_Prologue_Activity.class);
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
    public void onBackPressed(){

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
