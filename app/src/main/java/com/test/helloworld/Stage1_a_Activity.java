package com.test.helloworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private ImageButton pauseButton, resumeButton, restartButton, mainmenuButton, quitButton, fireButton, successToMainMenu, successToQuit;
    private RelativeLayout pauseMenu, missionCompletePopUp, shipBody;
    private AudioManager audioManager;
    private NumberPicker firingAngle;
    private int money, gun, missile, autocannon, currentAngle, currentEnemyLife = 100, currentPlayerLife = 100;

    private CountDownTimer mCountDownTimer, enemyTimer, autocannonTimer;
    private long mTimeLeftInMillis, shopReload, enemyReloadTime = 5000, autocannonReloadTime = 5000;
    private TextView mTextViewCountDown, currentEnemyLifeDisplay;

    private ProgressBar enemyLifeBar, playerLifeBar;
    private RelativeLayout gunTurret, mission_failed_popup;
    private ImageView targetShip, gunfire_effect;

    private int levelMilestone, chosenlevel, levelContUnlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_a);

        hideNavBar();

        gunTurret = findViewById(R.id.gun_turret);
        missionCompletePopUp = findViewById(R.id.success_popup);
        mission_failed_popup = findViewById(R.id.failed_popup);
        shipBody = findViewById(R.id.player_ship);
        targetShip = findViewById(R.id.target_ship);

        slideUp();

        currentEnemyLifeDisplay = findViewById(R.id.currentAngleText);
        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION START
        SharedPreferences sharedPreferences = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE);
        money = sharedPreferences.getInt("money", 0);
        gun = sharedPreferences.getInt("gun", 10000);
        missile = sharedPreferences.getInt("missile", 0);
        autocannon = sharedPreferences.getInt("autocannon", 0);

        shopReload = gun;
        mTimeLeftInMillis = shopReload;
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION END
        //******************************************************************************************

        // ***************************************************************************
        // ************************************************ ENEMY LIFE BAR START
        enemyLifeBar = findViewById(R.id.target_life_points);
        enemyLifeBar.setMax(100);
        enemyLifeBar.setProgress(currentEnemyLife);

        playerLifeBar = findViewById(R.id.player_life_bar);
        playerLifeBar.setMax(100);
        playerLifeBar.setProgress(currentPlayerLife);
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

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireButtonClicked();
                startTimer();
                gunfire_effect_animation();
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

        restartButton = findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartStage1();
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

        successToMainMenu = findViewById(R.id.success_mainmenu_button);
        successToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainMenu();
            }
        });

        successToQuit = findViewById(R.id.success_quit_button);
        successToQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });

        // ************************************************ PAUSE MENU BUTTONS END
        // ***************************************************************************

        enemyAutomaticAttack()
        ;
        // ************************************************ AUTOCANNON START
        // ***************************************************************************
        if (autocannon > 0){
            autocannonFunction();
        }
        // ************************************************ AUTOCANNON END
        // ***************************************************************************
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

    public void fireButtonClicked() {
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.naval_gun_sfx);
        soundfx.start();

        currentAngle = firingAngle.getValue();
        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

        if (currentAngle <= 20 && currentAngle >= 15){
            currentEnemyLife = currentEnemyLife - 20;
        }

        if (currentEnemyLife <= 0){
            money = money + 50;
            SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();
            missionCompletePopUp.setVisibility(View.VISIBLE);
        }

        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));
        enemyLifeBar.setProgress(currentEnemyLife);
    }

    public void startTimer(){
        fireButton.setAlpha(0.5f);
        fireButton.setEnabled(false);
        mTextViewCountDown.setVisibility(View.VISIBLE);

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
        mTimeLeftInMillis = shopReload;
    }

    public void updateCountDownText(){
        int seconds = (int) (mTimeLeftInMillis / 1000);

        String timeLeftFormatted = String.format("%02d", seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    // *********************************************************************************************
    // ********************************************************************** AUTOCANNON START
    public void autocannonFunction(){
        autocannonTimer = new CountDownTimer(autocannonReloadTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                enemyReloadTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                currentEnemyLife = currentEnemyLife - autocannon;
                currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

                MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.autocannon_sfx);
                soundfx.start();

                enemyLifeBar.setProgress(currentEnemyLife);
                autocannonRestart();
            }
        }.start();
    }

    public void autocannonRestart(){
        autocannonReloadTime = 500;

        if (currentEnemyLife <= 0){
            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();
            missionCompletePopUp.setVisibility(View.VISIBLE);
        }
        else {
            autocannonFunction();
        }
    }
    // ********************************************************************** AUTOCANNON END
    // *********************************************************************************************
    // *********************************************************************************************
    // ********************************************************************** ENEMY ATTACK START
    public void enemyAutomaticAttack(){
        enemyTimer = new CountDownTimer(enemyReloadTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                enemyReloadTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                currentPlayerLife = currentPlayerLife - 1;
                playerLifeBar.setProgress(currentPlayerLife);
                resetenemyFireTimer();
            }
        }.start();
    }

    public void resetenemyFireTimer(){
        enemyReloadTime = 2000;

        if (currentPlayerLife <= 0){
            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            mission_failed_popup.setVisibility(View.VISIBLE);
        }
        else {
            enemyAutomaticAttack();
        }
    }
    // ********************************************************************** ENEMY ATTACK END
    // *********************************************************************************************

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

    public void restartStage1(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_open_main = new Intent(this, Stage1_Loading_Activity.class);
        startActivity(intent_open_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
                System.exit(0);
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

    public void slideUp() {
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.slideup);
        shipBody.startAnimation(myanim);
        targetShip.startAnimation(myanim);

        Thread slideUpThread = new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    slideDown();
                }
            }
        };
        slideUpThread.start();
    }

    public void slideDown(){
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.slidedown);
        shipBody.startAnimation(myanim);
        targetShip.startAnimation(myanim);

        Thread slideDownThread = new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    slideUp();
                }
            }
        };
        slideDownThread.start();
    }

    public void gunfire_effect_animation(){
        gunfire_effect = findViewById(R.id.gunfire_effect);
        gunfire_effect.setRotation(90);
        gunfire_effect.setImageResource(R.drawable.main_gun_effect);
        AnimationDrawable gun_fire_effect_animation = (AnimationDrawable) gunfire_effect.getDrawable();
        gun_fire_effect_animation.start();
    }

    public void setLevelMilestone(){
        SharedPreferences sharedPreferences = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE);
        levelMilestone = sharedPreferences.getInt("levelMilestone", 0);
        chosenlevel = sharedPreferences.getInt("chosenlevel", 0);
        levelContUnlocked = sharedPreferences.getInt("levelContUnlocked", 0);

        if (chosenlevel == 1){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("levelMilestone", 1);
            LevelEditor.putInt("levelContUnlocked", 2);
        }
    }
}
