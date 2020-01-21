package com.test.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Stage1_Prologue_Activity extends AppCompatActivity {

    private ImageButton play_to_stage1;
    private MediaPlayer voiceOver;
    private int chosenlevel;
    private ImageView stage1Details,
                        stage2Details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_prologue);
        hideNavBar();

        play_to_stage1 = findViewById(R.id.stage1_play_fromPrologue);
        play_to_stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playStage1();
            }
        });

        stage1Details = findViewById(R.id.stage1_details);
        stage2Details = findViewById(R.id.stage2_details);

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION START
        SharedPreferences levelsharedPreferences = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE);
        chosenlevel = levelsharedPreferences.getInt("chosenlevel", 0);

        if (chosenlevel == 1){
            stage1Details.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage1_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 2){
            stage2Details.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage2_prologue_voice);
            voiceOver.start();
        }
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION END
        //******************************************************************************************
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

    public void playStage1(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        voiceOver.stop();

        Intent svc=new Intent(this, BackgroundSoundService.class);
        Intent loading_sound=new Intent(this, LoadingSoundService.class);

        stopService(loading_sound);
        startService(svc);

        Intent mainIntent = new Intent(Stage1_Prologue_Activity.this, Stage1_a_Activity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    @Override
    public void onBackPressed(){

    }
}
