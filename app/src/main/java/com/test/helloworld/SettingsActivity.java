package com.test.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton back_button;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration));
        getWindow().setEnterTransition(enterTransition);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        volumeControl();

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openMainActivity();
            }
        });
    }

    public void volumeControl(){
        try {
            SeekBar volume_seekbar = findViewById(R.id.volume_seekbar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volume_seekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volume_seekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            volume_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        catch (Exception e){

        }
    }

    public void openMainActivity(){
        MediaPlayer soundfx = MediaPlayer.create(SettingsActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_open_main = new Intent(this, MainActivity.class);
        startActivity(intent_open_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed(){
        MediaPlayer soundfx = MediaPlayer.create(SettingsActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        this.startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return;
    }

}
