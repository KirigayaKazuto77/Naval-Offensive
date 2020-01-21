package com.test.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CampaignListActivity extends AppCompatActivity {

    public static String levelSharedPreferences;
    private ImageButton campaignlist_back_button,
                stage1_button,
                stage2_button,
                stage3_button,
                stage4_button,
                stage5_button,
                stage6_button,
                stage7_button,
                stage8_button,
                stage9_button,
                stage10_button,
                stage11_button,
                stage12_button,
                stage13_button,
                stage14_button,
                stage15_button,
                stage16_button;
    private int chosenlevel,
                level_2_Unlocked,
                level_3_Unlocked,
                level_4_Unlocked,
                level_5_Unlocked,
                level_6_Unlocked,
                level_7_Unlocked,
                level_8_Unlocked,
                level_9_Unlocked,
                level_10_Unlocked,
                level_11_Unlocked,
                level_12_Unlocked,
                level_13_Unlocked,
                level_14_Unlocked,
                level_15_Unlocked,
                level_16_Unlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaignlist);

        hideNavBar();

        stage1_button = findViewById(R.id.campaign_stage1_button);
        stage2_button = findViewById(R.id.campaign_stage2_button);
        stage3_button = findViewById(R.id.campaign_stage3_button);
        stage4_button = findViewById(R.id.campaign_stage4_button);
        stage5_button = findViewById(R.id.campaign_stage5_button);
        stage6_button = findViewById(R.id.campaign_stage6_button);
        stage7_button = findViewById(R.id.campaign_stage7_button);
        stage8_button = findViewById(R.id.campaign_stage8_button);
        stage9_button = findViewById(R.id.campaign_stage9_button);
        stage10_button = findViewById(R.id.campaign_stage10_button);
        stage11_button = findViewById(R.id.campaign_stage11_button);
        stage12_button = findViewById(R.id.campaign_stage12_button);
        stage13_button = findViewById(R.id.campaign_stage13_button);
        stage14_button = findViewById(R.id.campaign_stage14_button);
        stage15_button = findViewById(R.id.campaign_stage15_button);
        stage16_button = findViewById(R.id.campaign_stage16_button);

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCE INITIALIZATION START
        SharedPreferences sharedPreferences = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE);
        chosenlevel = sharedPreferences.getInt("chosenlevel", 0);
        level_2_Unlocked = sharedPreferences.getInt("level_2_Unlocked", 0);
        level_3_Unlocked = sharedPreferences.getInt("level_3_Unlocked", 0);
        level_4_Unlocked = sharedPreferences.getInt("level_4_Unlocked", 0);
        level_5_Unlocked = sharedPreferences.getInt("level_5_Unlocked", 0);
        level_6_Unlocked = sharedPreferences.getInt("level_6_Unlocked", 0);
        level_7_Unlocked = sharedPreferences.getInt("level_7_Unlocked", 0);
        level_8_Unlocked = sharedPreferences.getInt("level_8_Unlocked", 0);
        level_9_Unlocked = sharedPreferences.getInt("level_9_Unlocked", 0);
        level_10_Unlocked = sharedPreferences.getInt("level_10_Unlocked", 0);
        level_11_Unlocked = sharedPreferences.getInt("level_11_Unlocked", 0);
        level_12_Unlocked = sharedPreferences.getInt("level_12_Unlocked", 0);
        level_13_Unlocked = sharedPreferences.getInt("level_13_Unlocked", 0);
        level_14_Unlocked = sharedPreferences.getInt("level_14_Unlocked", 0);
        level_15_Unlocked = sharedPreferences.getInt("level_15_Unlocked", 0);
        level_16_Unlocked = sharedPreferences.getInt("level_16_Unlocked", 0);

        if(level_2_Unlocked == 0){
            stage2_button.setEnabled(false);
            stage2_button.setAlpha(0.5f);
        }

        if(level_3_Unlocked == 0){
            stage3_button.setEnabled(false);
            stage3_button.setAlpha(0.5f);
        }

        if(level_4_Unlocked == 0){
            stage4_button.setEnabled(false);
            stage4_button.setAlpha(0.5f);
        }

        if(level_5_Unlocked == 0){
            stage5_button.setEnabled(false);
            stage5_button.setAlpha(0.5f);
        }

        if(level_6_Unlocked == 0){
            stage6_button.setEnabled(false);
            stage6_button.setAlpha(0.5f);
        }

        if(level_7_Unlocked == 0){
            stage7_button.setEnabled(false);
            stage7_button.setAlpha(0.5f);
        }

        if(level_8_Unlocked == 0){
            stage8_button.setEnabled(false);
            stage8_button.setAlpha(0.5f);
        }

        if(level_9_Unlocked == 0){
            stage9_button.setEnabled(false);
            stage9_button.setAlpha(0.5f);
        }

        if(level_10_Unlocked == 0){
            stage10_button.setEnabled(false);
            stage10_button.setAlpha(0.5f);
        }

        if(level_11_Unlocked == 0){
            stage11_button.setEnabled(false);
            stage11_button.setAlpha(0.5f);
        }

        if(level_12_Unlocked == 0){
            stage12_button.setEnabled(false);
            stage12_button.setAlpha(0.5f);
        }

        if(level_13_Unlocked == 0){
            stage13_button.setEnabled(false);
            stage13_button.setAlpha(0.5f);
        }

        if(level_14_Unlocked == 0){
            stage14_button.setEnabled(false);
            stage14_button.setAlpha(0.5f);
        }

        if(level_15_Unlocked == 0){
            stage15_button.setEnabled(false);
            stage15_button.setAlpha(0.5f);
        }

        if(level_16_Unlocked == 0){
            stage16_button.setEnabled(false);
            stage16_button.setAlpha(0.5f);
        }
        //****************************************************************** SHAREDPREFERENCE INITIALIZATION END
        //******************************************************************************************

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        //******************************************************************************************
        //****************************************************************** ONCLICK BUTTONS START
        stage1_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 1);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage2_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 2);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage3_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 3);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage4_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 4);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage5_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 5);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage6_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 6);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage7_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 7);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage8_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 8);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage9_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 9);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage10_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 10);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage11_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 11);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage12_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 12);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage13_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 13);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage14_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 14);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage15_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 15);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        stage16_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(CampaignListActivity.this, R.raw.button_click_sfx);
                soundfx.start();

                SharedPreferences.Editor LevelEditor = getSharedPreferences(levelSharedPreferences, MODE_PRIVATE).edit();
                LevelEditor.putInt("chosenlevel", 16);
                LevelEditor.commit();
                LevelEditor.apply();

                Intent intent_toOpen_stage1 = new Intent(CampaignListActivity.this, Stage1_Loading_Activity.class);
                startActivity(intent_toOpen_stage1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        //****************************************************************** ONCLICK BUTTONS END
        //******************************************************************************************

        campaignlist_back_button = findViewById(R.id.campaign_menu_back_button);
        campaignlist_back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                backtoPlayMenuActivity();
            }
        });
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
