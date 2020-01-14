package com.test.helloworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class Stage1_a_Activity extends AppCompatActivity {

    private ImageButton pauseButton, resumeButton, mainmenuButton, quitButton, fireButton;
    private RelativeLayout pauseMenu;
    private AudioManager audioManager;
    private NumberPicker firingAngle;
    private int currentAngle, currentEnemyLife = 100;

    private static final long START_TIME_IN_MILLIS = 3000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown, currentEnemyLifeDisplay;

    private ProgressBar enemyLifeBar;

    private ImageView gunTurret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_a);

        gunTurret = findViewById(R.id.gun_turret);

        // ***************************************************************************
        // ************************************************ ENEMY LIFE BAR START
        enemyLifeBar = findViewById(R.id.target_life_points);
        enemyLifeBar.setMax(100);
        // ************************************************ ENEMY LIFE BAR END
        // ***************************************************************************
        // ************************************************ PAUSE BUTTON START
        pauseMenu = findViewById(R.id.pause_menu);

        pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPauseMenu();
            }
        });
        // ************************************************ PAUSE BUTTON END
        // ***************************************************************************
        // ************************************************ ANGLE PICKER START
        firingAngle = findViewById(R.id.angle_fire);
        firingAngle.setMaxValue(45);
        firingAngle.setMinValue(0);

        currentAngle = firingAngle.getValue();

        firingAngle.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                gunTurret.setRotation(360 - newVal);
            }
        });
        // ************************************************ ANGLE PICKER FINISH
        // ***************************************************************************
        // ************************************************ TIMER START
        mTextViewCountDown = findViewById(R.id.fire_reload_time);
        // ************************************************ TIME FINISH
        // ***************************************************************************
        // ************************************************ FIRE BUTTON START
        fireButton = findViewById(R.id.fire_button);

        currentEnemyLifeDisplay = findViewById(R.id.currentAngleText);

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireButtonClicked();
                startTimer();
            }
        });
        // ************************************************ FIRE BUTTON FINISH
        // ***************************************************************************
        // ************************************************ PAUSE MENU BUTTONS START

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        volumeControl();

        resumeButton = findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePauseMenu();
            }
        });

        mainmenuButton = findViewById(R.id.mainmenu_button);
        mainmenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainMenu();
            }
        });

        quitButton = findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });

        // ************************************************ PAUSE MENU BUTTONS END
        // ***************************************************************************

    }

    public void fireButtonClicked() {
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.naval_gun_sfx);
        soundfx.start();

        currentAngle = firingAngle.getValue();
        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

        if (currentAngle <= 20 && currentAngle >= 15){
            currentEnemyLife = currentEnemyLife - 20;
        }

        if (currentEnemyLife == 0){
            showPauseMenu();
        }

        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));
        enemyLifeBar.setProgress(currentEnemyLife);
    }

    public void startTimer(){
        fireButton.setAlpha(0.5f);
        mTextViewCountDown.setVisibility(View.VISIBLE);
        fireButton.setEnabled(false);
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                resetTimer();
                fireButton.setAlpha(1f);
                mTextViewCountDown.setVisibility(View.GONE);
                fireButton.setEnabled(true);
            }
        }.start();
    }

    public void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
    }

    public void updateCountDownText(){
        int seconds = (int) (mTimeLeftInMillis / 1000);

        String timeLeftFormatted = String.format("%02d", seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public void showPauseMenu() {
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        if (pauseMenu.getVisibility() == View.GONE) {
            pauseMenu.setVisibility(View.VISIBLE);
        }
    }

    public void hidePauseMenu() {
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        if (pauseMenu.getVisibility() == View.VISIBLE) {
            pauseMenu.setVisibility(View.GONE);
        }
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

    public void toMainMenu(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_open_main = new Intent(this, MainActivity.class);
        startActivity(intent_open_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void quitGame(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        final AlertDialog.Builder builder = new AlertDialog.Builder(Stage1_a_Activity.this);
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
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onBackPressed() {
        if (pauseMenu.getVisibility() == View.GONE) {
            pauseMenu.setVisibility(View.VISIBLE);
        }
        else {
            pauseMenu.setVisibility(View.GONE);
        }
    }
}
