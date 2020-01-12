package com.test.helloworld;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class CampaignListActivity extends AppCompatActivity {

    private ImageButton campaignlist_back_button, stage1_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaignlist);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        stage1_button = findViewById(R.id.campaign_stage1_button);
        stage1_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openStage1();
            }
        });

        campaignlist_back_button = findViewById(R.id.campaign_menu_back_button);
        campaignlist_back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                backtoPlayMenuActivity();
            }
        });
    }

    public void openStage1(){
        MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_toOpen_stage1 = new Intent(this, Campaign1_Loading_Activity.class);
        startActivity(intent_toOpen_stage1);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void backtoPlayMenuActivity(){
        MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_backto_playmenu = new Intent(this, PlayMenuActivity.class);
        startActivity(intent_backto_playmenu);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed(){
        MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        this.startActivity(new Intent(CampaignListActivity.this, PlayMenuActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return;
    }
}
