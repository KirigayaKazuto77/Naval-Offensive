package com.test.helloworld;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class PlayMenuActivity extends AppCompatActivity {

    private ImageButton back_button, to_campaign_button, shop_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);

        hideNavBar();

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        to_campaign_button = findViewById(R.id.campaign_button);
        to_campaign_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCampaignActivity();
            }
        });

        back_button = findViewById(R.id.play_menu_back_button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });

        shop_button = findViewById(R.id.shop_button);
        shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShop();
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

    public void openMainActivity(){
        MediaPlayer soundfx = MediaPlayer.create(PlayMenuActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_open_main = new Intent(this, MainActivity.class);
        startActivity(intent_open_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openCampaignActivity(){
        MediaPlayer soundfx = MediaPlayer.create(PlayMenuActivity.this, R.raw.button_click_sfx);
        soundfx.start();
        Intent intent_open_campaign = new Intent(this, CampaignListActivity.class);
        startActivity(intent_open_campaign);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openShop(){
        MediaPlayer soundfx = MediaPlayer.create(PlayMenuActivity.this, R.raw.button_click_sfx);
        soundfx.start();
        Intent intent_open_campaign = new Intent(this, ShopActivity.class);
        startActivity(intent_open_campaign);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed(){
        MediaPlayer soundfx = MediaPlayer.create(PlayMenuActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        this.startActivity(new Intent(PlayMenuActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return;
    }

}
