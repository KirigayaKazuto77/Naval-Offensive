package com.test.helloworld;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class Stage1_Prologue_Activity extends AppCompatActivity {

    private ImageButton play_to_stage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_prologue);

        play_to_stage1 = findViewById(R.id.stage1_play_fromPrologue);
        play_to_stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playStage1();
            }
        });

        MediaPlayer soundfx = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.stage1_prologue_voice);
        soundfx.start();
    }


    public void playStage1(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_Prologue_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

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
