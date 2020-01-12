package com.test.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;

import androidx.appcompat.app.AppCompatActivity;

public class Campaign1_Loading_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign1_loading_screen);

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
                    Intent mainIntent = new Intent(Campaign1_Loading_Activity.this, Stage1_Prologue_Activity.class);
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        };
        thread.start();
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
