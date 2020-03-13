package com.test.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Stage1_Loading_Activity extends AppCompatActivity {

    private int chosenlevel;
    private ImageView loadingScreenBG;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_loading_screen);
        hideNavBar();

        final Intent svc=new Intent(this, BackgroundSoundService.class);
        stopService(svc);

        final Intent loading_sound=new Intent(this, LoadingSoundService.class);
        startService(loading_sound);


        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION START
        SharedPreferences levelsharedPreferences = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE);
        chosenlevel = levelsharedPreferences.getInt("chosenlevel", 0);

        if (chosenlevel == 1){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_1);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 2){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_2);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 3){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_3);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 4){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_4);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 5){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_5);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 6){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_6);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 7){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_7);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 8){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_8);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 9){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_9);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 10){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_10);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 11){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_11);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 12){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_12);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 13){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_13);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 14){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_14);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 15){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_15);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        if (chosenlevel == 16){
            loadingScreenBG = findViewById(R.id.loadingScreen_BG_16);
            loadingScreenBG.setVisibility(View.VISIBLE);
        }
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION END
        //******************************************************************************************

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        Thread thread = new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(3000);
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
