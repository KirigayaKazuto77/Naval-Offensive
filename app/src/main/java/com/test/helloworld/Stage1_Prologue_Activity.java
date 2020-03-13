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
    private ImageView stageDetails;

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

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION START
        SharedPreferences levelsharedPreferences = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE);
        chosenlevel = levelsharedPreferences.getInt("chosenlevel", 0);

        if (chosenlevel == 1){
            stageDetails = findViewById(R.id.stage1_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage1_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 2){
            stageDetails = findViewById(R.id.stage2_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage2_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 3){
            stageDetails = findViewById(R.id.stage3_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage3_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 4){
            stageDetails = findViewById(R.id.stage4_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage4_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 5){
            stageDetails = findViewById(R.id.stage5_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage5_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 6){
            stageDetails = findViewById(R.id.stage6_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage6_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 7){
            stageDetails = findViewById(R.id.stage7_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage7_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 8){
            stageDetails = findViewById(R.id.stage8_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage8_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 9){
            stageDetails = findViewById(R.id.stage9_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage9_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 10){
            stageDetails = findViewById(R.id.stage10_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage10_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 11){
            stageDetails = findViewById(R.id.stage11_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage11_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 12){
            stageDetails = findViewById(R.id.stage12_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage12_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 13){
            stageDetails = findViewById(R.id.stage13_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage13_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 14){
            stageDetails = findViewById(R.id.stage14_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage14_prologue_voice);
            voiceOver.start();
        }
        if (chosenlevel == 15){
            stageDetails = findViewById(R.id.stage15_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage15_prologue_voice);
            voiceOver.start();
        }

        if (chosenlevel == 16){
            stageDetails = findViewById(R.id.stage16_details);
            stageDetails.setVisibility(View.VISIBLE);
            voiceOver = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage16_prologue_voice);
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

        Intent loading_sound=new Intent(this, LoadingSoundService.class);
        stopService(loading_sound);

        Intent mainIntent = new Intent(Stage1_Prologue_Activity.this, Stage1_a_Activity.class);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    @Override
    public void onBackPressed(){

    }
}
