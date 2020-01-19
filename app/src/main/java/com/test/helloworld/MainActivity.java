package com.test.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {


    private ImageButton settings_button, play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideNavBar();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        Intent svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        settings_button = findViewById(R.id.settings);
        settings_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(MainActivity.this, R.raw.button_click_sfx);
                soundfx.start();
                openSettingsActivity();
            }
        });

        play_button = findViewById(R.id.play);
        play_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer soundfx = MediaPlayer.create(MainActivity.this, R.raw.button_click_sfx);
                soundfx.start();
                openPlayMenuActivity();
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

    public void openSettingsActivity(){
        Intent intent_open_settings = new Intent(this, SettingsActivity.class);
        startActivity(intent_open_settings);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openPlayMenuActivity(){
        Intent intent_open_playmenu = new Intent(this, PlayMenuActivity.class);
        startActivity(intent_open_playmenu);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed(){

        MediaPlayer soundfx = MediaPlayer.create(MainActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are your sure you want to Exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                onDestroy();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clickexit(View v){
        MediaPlayer soundfx = MediaPlayer.create(MainActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are your sure you want to Exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                onDestroy();
                System.exit(0);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
