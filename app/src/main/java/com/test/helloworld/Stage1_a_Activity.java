package com.test.helloworld;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
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

    private ImageButton pauseButton, resumeButton, restartButton, mainmenuButton, quitButton, fireButton, successToMainMenu,
            levels_button, successToQuit, missileButton, tryAgainButton, failedCampaignListButton, failedToMainMenuButton,
            failedToQuitButton;
    private RelativeLayout pauseMenu, missionCompletePopUp, shipBody;
    private AudioManager audioManager;
    private NumberPicker firingAngle;
    private int money, gun, missile, autocannon, currentAngle, currentEnemyLife, currentPlayerLife = 200, remainingMissile,
                enemyDamage, upperAngle, lowerAngle;
    private double enemyDistance1, enemyDistance2, enemyDistance3, enemyDistance4;

    private CountDownTimer mCountDownTimer, enemyTimer, autocannonTimer, missileReloadTime, missileLaunchAnim;
    private long mTimeLeftInMillis, shopReload, enemyReloadTime, getEnemyReloadTimeRestart, autocannonReloadTime = 5000,
                 missileReloadTimeDuration = 3000;
    private TextView mTextViewCountDown, currentEnemyLifeDisplay, remainingMissilesDisplay, missileReloadDisplay,
                        enemyDistanceDisplay, displayReward;

    private ProgressBar enemyLifeBar, playerLifeBar;
    private RelativeLayout gunTurret, mission_failed_popup, firstSecondaryGun, secondSecondaryGun, attackOnEnemy, theatrical_launch,
                            theatrical_frame;
    private ImageView gunfire_effect, harpoonMissile, harpoonMissileImpact, targetShip, secondaryGunEffect, secondSecondaryGunEffect,
            missileLauncher, playerShipExplosion;

    private int chosenlevel, oneQuarterLife, twoQuarterLife, threeQuarterLife,
                oneQuarterLowerAngle, oneQuarterUpperAngle,
                twoQuarterLowerAngle, twoQuarterUpperAngle,
                threeQuarterLowerAngle, threeQuarterUpperAngle;

    private MediaPlayer autocannonSFX, enemyAttackSFX;
    public Intent gameplay_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_1_a);

        final Intent svc=new Intent(this, BackgroundSoundService.class);
        stopService(svc);

        hideNavBar();


        theatrical_launch = findViewById(R.id.theatrical_launch);
        theatrical_frame = findViewById(R.id.attack_theatrical_frame);
        enemyDistanceDisplay = findViewById(R.id.target_distance);
        displayReward = findViewById(R.id.displayReward);

        // ***************************************************************************
        // ************************************************ ENEMY LIFE BAR START
        enemyLifeBar = findViewById(R.id.target_life_points);

        playerLifeBar = findViewById(R.id.player_life_bar);
        playerLifeBar.setMax(200);
        playerLifeBar.setProgress(currentPlayerLife);
        // ************************************************ ENEMY LIFE BAR END
        // ***************************************************************************

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCES INITIALIZATION START
        SharedPreferences sharedPreferences = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE);
        money = sharedPreferences.getInt("money", 0);
        gun = sharedPreferences.getInt("gun", 10000);
        missile = sharedPreferences.getInt("missile", 0);
        autocannon = sharedPreferences.getInt("autocannon", 0);

        if (autocannon == 5){
            firstSecondaryGun = findViewById(R.id.secondary_gun_first);
            firstSecondaryGun.setVisibility(View.VISIBLE);
        }

        if (autocannon == 10){
            firstSecondaryGun = findViewById(R.id.secondary_gun_first);
            firstSecondaryGun.setVisibility(View.VISIBLE);

            secondSecondaryGun = findViewById(R.id.secondary_gun_second);
            secondSecondaryGun.setVisibility(View.VISIBLE);
        }

        if (missile == 1){
            missileLauncher = findViewById(R.id.missile_launcher_1);
            missileLauncher.setVisibility(View.VISIBLE);
        }

        if (missile == 2){
            missileLauncher = findViewById(R.id.missile_launcher_2);
            missileLauncher.setVisibility(View.VISIBLE);
        }

        if (missile == 4){
            missileLauncher = findViewById(R.id.missile_launcher_4);
            missileLauncher.setVisibility(View.VISIBLE);
        }

        shopReload = gun;
        mTimeLeftInMillis = shopReload;


        SharedPreferences levelsharedPreferences = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE);
        chosenlevel = levelsharedPreferences.getInt("chosenlevel", 0);

        if (chosenlevel == 1){
            targetShip = findViewById(R.id.target_ship_1);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_1);

            currentEnemyLife = 100;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 10000;
            enemyReloadTime = 10000;
            enemyDamage = 0;

            enemyDistance1 = 8;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle1 = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle1 + 2;
            lowerAngle = (int)exact_angle1 - 2;

            enemyDistance2 = 12;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 10;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 9;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 2;
            oneQuarterLowerAngle = (int)exact_angle4 -2;
        }

        if (chosenlevel == 2){
            targetShip = findViewById(R.id.target_ship_2);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_2);

            currentEnemyLife = 120;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 11000;
            enemyReloadTime = 11000;
            enemyDamage = 10;

            enemyDistance1 = 10;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 12;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 7;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 13;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 2;
            oneQuarterLowerAngle = (int)exact_angle4 -2;
        }
        if (chosenlevel == 3){
            targetShip = findViewById(R.id.target_ship_3);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_3);

            currentEnemyLife = 120;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 9000;
            enemyReloadTime = 9000;
            enemyDamage = 10;

            enemyDistance1 = 5;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 7;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 13;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 10;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 2;
            oneQuarterLowerAngle = (int)exact_angle4 -2;
        }

        if (chosenlevel == 4){
            targetShip = findViewById(R.id.target_ship_4);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_4);

            currentEnemyLife = 130;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 8000;
            enemyReloadTime = 8000;
            enemyDamage = 15;

            enemyDistance1 = 9;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 11;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 7;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 10;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 5){
            targetShip = findViewById(R.id.target_ship_5);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_5);

            currentEnemyLife = 130;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 8000;
            enemyReloadTime = 8000;
            enemyDamage = 18;

            enemyDistance1 = 18;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 15;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 7;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 12;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 6){
            targetShip = findViewById(R.id.target_ship_6);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_6);

            currentEnemyLife = 140;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7500;
            enemyReloadTime = 7500;
            enemyDamage = 18;

            enemyDistance1 = 12;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 4;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 6;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 11;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 7){
            targetShip = findViewById(R.id.target_ship_7);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_7);

            currentEnemyLife = 150;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7500;
            enemyReloadTime = 7500;
            enemyDamage = 18;

            enemyDistance1 = 2;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 10;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 5;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 12;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 8){
            targetShip = findViewById(R.id.target_ship_8);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_8);

            currentEnemyLife = 170;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7500;
            enemyReloadTime = 7500;
            enemyDamage = 18;

            enemyDistance1 = 14;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 19;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 17;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 8;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 9){
            targetShip = findViewById(R.id.target_ship_9);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_9);

            currentEnemyLife = 180;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7000;
            enemyReloadTime = 7000;
            enemyDamage = 13;

            enemyDistance1 = 8;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 20;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 14;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 5;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 10){
            targetShip = findViewById(R.id.target_ship_10);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_10);

            currentEnemyLife = 180;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7000;
            enemyReloadTime = 7000;
            enemyDamage = 15;

            enemyDistance1 = 7;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 4;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 13;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 2;
            twoQuarterLowerAngle = (int)exact_angle3 -2;

            enemyDistance4 = 10;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 11){
            targetShip = findViewById(R.id.target_ship_11);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_11);

            currentEnemyLife = 190;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7000;
            enemyReloadTime = 7000;
            enemyDamage = 18;

            enemyDistance1 = 13;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 16;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 10;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 2;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 12){
            currentPlayerLife = 120;
            playerLifeBar = findViewById(R.id.player_life_bar);
            playerLifeBar.setMax(200);
            playerLifeBar.setProgress(currentPlayerLife);

            targetShip = findViewById(R.id.target_ship_12);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_12);

            currentEnemyLife = 190;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 7000;
            enemyReloadTime = 7000;
            enemyDamage = 20;

            enemyDistance1 = 2;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 10;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 16;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 13;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 13){
            targetShip = findViewById(R.id.target_ship_13);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_13);

            currentEnemyLife = 200;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 6000;
            enemyReloadTime = 6000;
            enemyDamage = 25;

            enemyDistance1 = 18;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 8;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 2;
            threeQuarterLowerAngle = (int)exact_angle2 -2;

            enemyDistance3 = 12;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 7;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 14){
            targetShip = findViewById(R.id.target_ship_14);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_14);

            currentEnemyLife = 220;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 6000;
            enemyReloadTime = 6000;
            enemyDamage = 30;

            enemyDistance1 = 19;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 2;
            lowerAngle = (int)exact_angle - 2;

            enemyDistance2 = 4;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 1;
            threeQuarterLowerAngle = (int)exact_angle2 -1;

            enemyDistance3 = 9;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 13;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 15){
            targetShip = findViewById(R.id.target_ship_15);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_15);

            currentEnemyLife = 250;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 5500;
            enemyReloadTime = 5500;
            enemyDamage = 35;

            enemyDistance1 = 2;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 1;
            lowerAngle = (int)exact_angle - 1;

            enemyDistance2 = 15;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 1;
            threeQuarterLowerAngle = (int)exact_angle2 -1;

            enemyDistance3 = 11;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 6;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        if (chosenlevel == 16){
            targetShip = findViewById(R.id.target_ship_16);
            targetShip.setVisibility(View.VISIBLE);

            attackOnEnemy = findViewById(R.id.theatrical_Anim_16);

            currentEnemyLife = 300;
            enemyLifeBar.setMax(currentEnemyLife);
            enemyLifeBar.setProgress(currentEnemyLife);

            threeQuarterLife = currentEnemyLife * 3 / 4;
            twoQuarterLife = currentEnemyLife * 1 / 2;
            oneQuarterLife = currentEnemyLife * 1 / 4;

            getEnemyReloadTimeRestart = 5000;
            enemyReloadTime = 5000;
            enemyDamage = 35;

            enemyDistance1 = 4;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance1) + " Km");
            double exact_angle = (enemyDistance1 * 45) / 20;
            upperAngle = (int)exact_angle + 1;
            lowerAngle = (int)exact_angle - 1;

            enemyDistance2 = 17;
            double exact_angle2 = (enemyDistance2 * 45) / 20;
            threeQuarterUpperAngle = (int)exact_angle2 + 1;
            threeQuarterLowerAngle = (int)exact_angle2 -1;

            enemyDistance3 = 13;
            double exact_angle3 = (enemyDistance3 * 45) / 20;
            twoQuarterUpperAngle = (int)exact_angle3 + 1;
            twoQuarterLowerAngle = (int)exact_angle3 -1;

            enemyDistance4 = 7;
            double exact_angle4 = (enemyDistance4 * 45) / 20;
            oneQuarterUpperAngle = (int)exact_angle4 + 1;
            oneQuarterLowerAngle = (int)exact_angle4 -1;
        }

        //****************************************************************** SHAREDPREFERENCES INITIALIZATION END
        //******************************************************************************************

        if (chosenlevel == 1 || chosenlevel == 5 || chosenlevel == 9 || chosenlevel == 13){
            gameplay_sound = new Intent(this, GamePlay_Music_1_Service.class);
            startService(gameplay_sound);
        }
        else if (chosenlevel == 2 || chosenlevel == 6 || chosenlevel == 10 || chosenlevel == 14){
            gameplay_sound = new Intent(this, GamePlay_Music_2_Service.class);
            startService(gameplay_sound);
        }
        else if (chosenlevel == 3 || chosenlevel == 7 || chosenlevel == 11 || chosenlevel == 15){
            gameplay_sound = new Intent(this, GamePlay_Music_3_Service.class);
            startService(gameplay_sound);
        }
        else if (chosenlevel == 4 || chosenlevel == 8 || chosenlevel == 12 || chosenlevel == 16){
            gameplay_sound = new Intent(this, GamePlay_Music_3_Service.class);
            startService(gameplay_sound);
        }

        gunTurret = findViewById(R.id.gun_turret);
        missionCompletePopUp = findViewById(R.id.success_popup);
        mission_failed_popup = findViewById(R.id.failed_popup);
        shipBody = findViewById(R.id.player_ship);

        currentEnemyLifeDisplay = findViewById(R.id.currentEnemyLifeText);
        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

        //******************************************************************************************
        //****************************************************************** MISSILE BUTTON START
        missileButton = findViewById(R.id.missile_button);
        missileReloadDisplay = findViewById(R.id.missile_reload_time);

        remainingMissile = missile;
        remainingMissilesDisplay = findViewById(R.id.remaining_missiles);
        remainingMissilesDisplay.setText(String.valueOf(remainingMissile));

        if (remainingMissile <= 0){
            missileButton.setAlpha(0.5f);
            missileButton.setEnabled(false);
        }

        missileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remainingMissile = remainingMissile - 1;
                remainingMissilesDisplay.setText(String.valueOf(remainingMissile));
                missileFire();
                missileReload();
            }
        });
        //****************************************************************** MISSILE BUTTON END
        //******************************************************************************************

        // *****************************************************************************************
        // ***************************************************************** PAUSE BUTTON START
        pauseMenu = findViewById(R.id.pause_menu);

        pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showPauseMenu();
            }
        });
        // ***************************************************************** PAUSE BUTTON END
        // *****************************************************************************************
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
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                restartStage1();
            }
        });

        mainmenuButton = findViewById(R.id.mainmenu_button);
        mainmenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
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
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
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

        levels_button = findViewById(R.id.campaignList_button);
        levels_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
                openCampaignList();
            }
        });

        tryAgainButton = findViewById(R.id.tryAgain_button);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
                restartStage1();
            }
        });

        failedCampaignListButton = findViewById(R.id.failed_campaignList_button);
        failedCampaignListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
                openCampaignList();
            }
        });

        failedToMainMenuButton = findViewById(R.id.failed_mainmenu_button);
        failedToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayerLife = 0;
                currentEnemyLife = 0;
                stopService(gameplay_sound);
                startService(svc);
                toMainMenu();
            }
        });

        failedToQuitButton = findViewById(R.id.failed_quit_button);
        failedToQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });

        // ************************************************ PAUSE MENU BUTTONS END
        // ***************************************************************************
        if (chosenlevel > 1) {enemyAutomaticAttack();}
        // ************************************************ AUTOCANNON START
        // ***************************************************************************
        if (autocannon > 0){autocannonFunction();}
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

    // *********************************************************************************************
    // ********************************************************************** MAIN GUN START
    public void fireButtonClicked() {
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.naval_gun_sfx);
        soundfx.start();

        currentAngle = firingAngle.getValue();
        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

        if (currentAngle <= upperAngle && currentAngle >= lowerAngle){
            currentEnemyLife = currentEnemyLife - 20;
            missileExplosionAnim();
        }

        //Enemy Distance Pattern START
        if (currentEnemyLife <= threeQuarterLife && currentEnemyLife > twoQuarterLife){
            upperAngle = threeQuarterUpperAngle;
            lowerAngle = threeQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance2) + " Km");
            textColorBlink();
        }
        else if (currentEnemyLife <= twoQuarterLife && currentEnemyLife > oneQuarterLife){
            upperAngle = twoQuarterUpperAngle;
            lowerAngle = twoQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance3) + " Km");
            textColorBlink();
        }
        else if (currentEnemyLife <= oneQuarterLife){
            upperAngle = oneQuarterUpperAngle;
            lowerAngle = oneQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance4) + " Km");
            textColorBlink();
        }
        //Enemy Distance Pattern END

        if (currentEnemyLife <= 0){

            if (chosenlevel <= 4){
                money = money + 80;
                displayReward.setText(String.valueOf(80));
            }
            else if (chosenlevel <= 8 && chosenlevel >= 5){
                money = money + 105;
                displayReward.setText(String.valueOf(105));
            }
            else if (chosenlevel <= 12 && chosenlevel >= 9){
                money = money + 145;
                displayReward.setText(String.valueOf(145));
            }
            else if (chosenlevel <= 16 && chosenlevel >= 13){
                money = money + 190;
                displayReward.setText(String.valueOf(190));
            }

            SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            targetShip.setVisibility(View.GONE);

            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();
            missileExplosionAnim();

            if(chosenlevel == 16){
                final Intent svc=new Intent(this, BackgroundSoundService.class);

                RelativeLayout Campaign_End_Banner;
                Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                Campaign_End_Banner.setVisibility(View.VISIBLE);

                ImageButton Campaign_Banner_MainMenu_Button;
                Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPlayerLife = 0;
                        currentEnemyLife = 0;
                        stopService(gameplay_sound);
                        startService(svc);
                        toMainMenu();
                    }
                });
            }
            else {
                missionCompletePopUp.setVisibility(View.VISIBLE);
            }
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
    // ********************************************************************** MAIN GUN END
    // *********************************************************************************************

    // *********************************************************************************************
    // ********************************************************************** MISSILE START
    public void missileFire(){
        harpoonMissile = findViewById(R.id.harpoon_missile);
        theatrical_launch.setVisibility(View.VISIBLE);
        theatricalLaunch();

        CountDownTimer missile_launch_1st = new CountDownTimer(900, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                harpoonMissile.setVisibility(View.VISIBLE);
                Animation missileAnim = AnimationUtils.loadAnimation(Stage1_a_Activity.this, R.anim.missile_launch);
                harpoonMissile.startAnimation(missileAnim);
                MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.missile_launch_sfx);
                soundfx.start();
                CountDownTimer missileGone = new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        harpoonMissile.setVisibility(View.GONE);
                    }
                }.start();
            }
        }.start();

        harpoonMissile.setVisibility(View.GONE);

        CountDownTimer missile_launch_2nd = new CountDownTimer(2800, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                theatrical_launch.setVisibility(View.GONE);
                harpoonMissile.setVisibility(View.GONE);
                missileImpactAnim();
            }
        }.start();
    }

    public void missileImpactAnim(){
        harpoonMissileImpact = findViewById(R.id.harpoon_missile_impact);

        attackOnEnemy.setVisibility(View.VISIBLE);
        theatrical_frame.setVisibility(View.VISIBLE);

        Animation missileImpactAnim = AnimationUtils.loadAnimation(this, R.anim.missile_impact);
        harpoonMissileImpact.startAnimation(missileImpactAnim);

        missileLaunchAnim = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                harpoonMissileImpact.setVisibility(View.GONE);
                theatricalExplosionAnim();
                missileImpact();
            }
        }.start();

        CountDownTimer theatricalExit = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                attackOnEnemy.setVisibility(View.GONE);
                theatrical_frame.setVisibility(View.GONE);
            }
        }.start();
    }

    public void missileImpact(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.impact_sfx);
        soundfx.start();

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(250);

        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));
        currentEnemyLife = currentEnemyLife - 30;

        //Enemy Distance Pattern START
        if (currentEnemyLife <= threeQuarterLife && currentEnemyLife > twoQuarterLife){
            upperAngle = threeQuarterUpperAngle;
            lowerAngle = threeQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance2) + " Km");
            textColorBlink();
        }
        else if (currentEnemyLife <= twoQuarterLife && currentEnemyLife > oneQuarterLife){
            upperAngle = twoQuarterUpperAngle;
            lowerAngle = twoQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance3) + " Km");
            textColorBlink();
        }
        else if (currentEnemyLife <= oneQuarterLife){
            upperAngle = oneQuarterUpperAngle;
            lowerAngle = oneQuarterLowerAngle;
            enemyDistanceDisplay.setText(String.valueOf(enemyDistance4) + " Km");
            textColorBlink();
        }
        //Enemy Distance Pattern END

        if (currentEnemyLife <= 0){

            if (chosenlevel <= 4){
                money = money + 80;
                displayReward.setText(String.valueOf(80));
            }
            else if (chosenlevel <= 8 && chosenlevel >= 5){
                money = money + 105;
                displayReward.setText(String.valueOf(105));
            }
            else if (chosenlevel <= 12 && chosenlevel >= 9){
                money = money + 145;
                displayReward.setText(String.valueOf(145));
            }
            else if (chosenlevel <= 16 && chosenlevel >= 13){
                money = money + 190;
                displayReward.setText(String.valueOf(190));
            }

            SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            targetShip.setVisibility(View.GONE);

            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();

            if(chosenlevel == 16){
                final Intent svc=new Intent(this, BackgroundSoundService.class);

                RelativeLayout Campaign_End_Banner;
                Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                Campaign_End_Banner.setVisibility(View.VISIBLE);

                ImageButton Campaign_Banner_MainMenu_Button;
                Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPlayerLife = 0;
                        currentEnemyLife = 0;
                        stopService(gameplay_sound);
                        startService(svc);
                        toMainMenu();
                    }
                });
            }
            else {
                missionCompletePopUp.setVisibility(View.VISIBLE);
            }
        }

        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));
        enemyLifeBar.setProgress(currentEnemyLife);
    }

    public void missileReload(){
        missileButton.setAlpha(0.5f);
        missileButton.setEnabled(false);
        missileReloadDisplay.setVisibility(View.VISIBLE);

        missileReloadTime = new CountDownTimer(missileReloadTimeDuration = 3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateMissileCounter();
                missileReloadTimeDuration = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                missileReloadDisplay.setVisibility(View.GONE);
                if (remainingMissile <= 0){
                    missileButton.setEnabled(false);
                    missileButton.setAlpha(0.5f);
                }else{
                    missileButton.setAlpha(1f);
                    missileButton.setEnabled(true);
                    missileReloadRestart();
                }
            }
        }.start();
    }

    public void missileReloadRestart(){
        missileReloadTimeDuration = 3000;
    }

    public void updateMissileCounter(){
        int seconds = (int) (missileReloadTimeDuration / 1000);

        String timeLeftFormatted = String.format("%02d", seconds);
        missileReloadDisplay.setText(timeLeftFormatted);
    }
    // ********************************************************************** MISSILE END
    // *********************************************************************************************

    // *********************************************************************************************
    // ********************************************************************** AUTOCANNON START
    public void autocannonFunction(){
        if (currentEnemyLife <= 0){

            if (chosenlevel <= 4){
                money = money + 80;
                displayReward.setText(String.valueOf(80));
            }
            else if (chosenlevel <= 8 && chosenlevel >= 5){
                money = money + 105;
                displayReward.setText(String.valueOf(105));
            }
            else if (chosenlevel <= 12 && chosenlevel >= 9){
                money = money + 145;
                displayReward.setText(String.valueOf(145));
            }
            else if (chosenlevel <= 16 && chosenlevel >= 13){
                money = money + 190;
                displayReward.setText(String.valueOf(190));
            }

            SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            targetShip.setVisibility(View.GONE);

            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();

            missileExplosionAnim();
            if(chosenlevel == 16){
                final Intent svc=new Intent(this, BackgroundSoundService.class);

                RelativeLayout Campaign_End_Banner;
                Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                Campaign_End_Banner.setVisibility(View.VISIBLE);

                ImageButton Campaign_Banner_MainMenu_Button;
                Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPlayerLife = 0;
                        currentEnemyLife = 0;
                        stopService(gameplay_sound);
                        startService(svc);
                        toMainMenu();
                    }
                });
            }
            else {
                missionCompletePopUp.setVisibility(View.VISIBLE);
            }
        }
        else {
            autocannonTimer = new CountDownTimer(autocannonReloadTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    autocannonReloadTime = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    if (currentPlayerLife <= 0){
                        firingAngle.setEnabled(false);
                        pauseButton.setEnabled(false);
                        fireButton.setEnabled(false);
                        fireButton.setAlpha(0.5f);
                        mission_failed_popup.setVisibility(View.VISIBLE);
                    }
                    else if(currentEnemyLife <= 0){

                        if (chosenlevel <= 4){
                            money = money + 80;
                            displayReward.setText(String.valueOf(80));
                        }
                        else if (chosenlevel <= 8 && chosenlevel >= 5){
                            money = money + 105;
                            displayReward.setText(String.valueOf(105));
                        }
                        else if (chosenlevel <= 12 && chosenlevel >= 9){
                            money = money + 145;
                            displayReward.setText(String.valueOf(145));
                        }
                        else if (chosenlevel <= 16 && chosenlevel >= 13){
                            money = money + 190;
                            displayReward.setText(String.valueOf(190));
                        }

                        SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
                        editor.putInt("money", money);
                        editor.commit();
                        editor.apply();

                        targetShip.setVisibility(View.GONE);

                        firingAngle.setEnabled(false);
                        pauseButton.setEnabled(false);
                        fireButton.setEnabled(false);
                        fireButton.setAlpha(0.5f);
                        setLevelMilestone();

                        missileExplosionAnim();
                        if(chosenlevel == 16){
                            final Intent svc=new Intent(Stage1_a_Activity.this, BackgroundSoundService.class);

                            RelativeLayout Campaign_End_Banner;
                            Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                            Campaign_End_Banner.setVisibility(View.VISIBLE);

                            ImageButton Campaign_Banner_MainMenu_Button;
                            Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                            Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPlayerLife = 0;
                                    currentEnemyLife = 0;
                                    stopService(gameplay_sound);
                                    startService(svc);
                                    toMainMenu();
                                }
                            });
                        }
                        else {
                            missionCompletePopUp.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        currentEnemyLife = currentEnemyLife - autocannon;
                        currentEnemyLifeDisplay.setText(String.valueOf(currentEnemyLife));

                        //Enemy Distance Pattern START
                        if (currentEnemyLife <= threeQuarterLife && currentEnemyLife > twoQuarterLife){
                            upperAngle = threeQuarterUpperAngle;
                            lowerAngle = threeQuarterLowerAngle;
                            enemyDistanceDisplay.setText(String.valueOf(enemyDistance2) + " Km");
                            textColorBlink();
                        }
                        else if (currentEnemyLife <= twoQuarterLife && currentEnemyLife > oneQuarterLife){
                            upperAngle = twoQuarterUpperAngle;
                            lowerAngle = twoQuarterLowerAngle;
                            enemyDistanceDisplay.setText(String.valueOf(enemyDistance3) + " Km");
                            textColorBlink();
                        }
                        else if (currentEnemyLife <= oneQuarterLife){
                            upperAngle = oneQuarterUpperAngle;
                            lowerAngle = oneQuarterLowerAngle;
                            enemyDistanceDisplay.setText(String.valueOf(enemyDistance4) + " Km");
                            textColorBlink();
                        }
                        //Enemy Distance Pattern END

                        autocannonSFX = MediaPlayer.create(Stage1_a_Activity.this, R.raw.autocannon_sfx);
                        autocannonSFX.start();

                        autocannon_fx_anim();
                        autocannon2_fx_anim();

                        enemyLifeBar.setProgress(currentEnemyLife);

                        autocannonReloadTime = 5000;
                        autocannonFunction();
                    }
                }
            }.start();
        }
    }
    // ********************************************************************** AUTOCANNON END
    // *********************************************************************************************
    // *********************************************************************************************
    // ********************************************************************** ENEMY ATTACK START
    public void enemyAutomaticAttack(){
        if (currentPlayerLife <= 0){
            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            mission_failed_popup.setVisibility(View.VISIBLE);
        }
        else if(currentEnemyLife <= 0){

            if (chosenlevel <= 4){
                money = money + 80;
                displayReward.setText(String.valueOf(80));
            }
            else if (chosenlevel <= 8 && chosenlevel >= 5){
                money = money + 105;
                displayReward.setText(String.valueOf(105));
            }
            else if (chosenlevel <= 12 && chosenlevel >= 9){
                money = money + 145;
                displayReward.setText(String.valueOf(145));
            }
            else if (chosenlevel <= 16 && chosenlevel >= 13){
                money = money + 190;
                displayReward.setText(String.valueOf(190));
            }

            SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            targetShip.setVisibility(View.GONE);

            firingAngle.setEnabled(false);
            pauseButton.setEnabled(false);
            fireButton.setEnabled(false);
            fireButton.setAlpha(0.5f);
            setLevelMilestone();

            missileExplosionAnim();
            if(chosenlevel == 16){
                final Intent svc=new Intent(this, BackgroundSoundService.class);

                RelativeLayout Campaign_End_Banner;
                Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                Campaign_End_Banner.setVisibility(View.VISIBLE);

                ImageButton Campaign_Banner_MainMenu_Button;
                Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPlayerLife = 0;
                        currentEnemyLife = 0;
                        stopService(gameplay_sound);
                        startService(svc);
                        toMainMenu();
                    }
                });
            }
            else {
                missionCompletePopUp.setVisibility(View.VISIBLE);
            }
        }
        else {
            enemyTimer = new CountDownTimer(enemyReloadTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    enemyReloadTime = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    if (currentPlayerLife <= 0){
                        firingAngle.setEnabled(false);
                        pauseButton.setEnabled(false);
                        fireButton.setEnabled(false);
                        fireButton.setAlpha(0.5f);
                        mission_failed_popup.setVisibility(View.VISIBLE);
                    }
                    else if(currentEnemyLife <= 0){

                        if (chosenlevel <= 4){
                            money = money + 80;
                            displayReward.setText(String.valueOf(80));
                        }
                        else if (chosenlevel <= 8 && chosenlevel >= 5){
                            money = money + 105;
                            displayReward.setText(String.valueOf(105));
                        }
                        else if (chosenlevel <= 12 && chosenlevel >= 9){
                            money = money + 145;
                            displayReward.setText(String.valueOf(145));
                        }
                        else if (chosenlevel <= 16 && chosenlevel >= 13){
                            money = money + 190;
                            displayReward.setText(String.valueOf(190));
                        }

                        SharedPreferences.Editor editor = getSharedPreferences(ShopActivity.moneySharedPreference, MODE_PRIVATE).edit();
                        editor.putInt("money", money);
                        editor.commit();
                        editor.apply();

                        targetShip.setVisibility(View.GONE);

                        firingAngle.setEnabled(false);
                        pauseButton.setEnabled(false);
                        fireButton.setEnabled(false);
                        fireButton.setAlpha(0.5f);
                        setLevelMilestone();

                        missileExplosionAnim();
                        if(chosenlevel == 16){
                            final Intent svc=new Intent(Stage1_a_Activity.this, BackgroundSoundService.class);

                            RelativeLayout Campaign_End_Banner;
                            Campaign_End_Banner = findViewById(R.id.campaign_end_banner);
                            Campaign_End_Banner.setVisibility(View.VISIBLE);

                            ImageButton Campaign_Banner_MainMenu_Button;
                            Campaign_Banner_MainMenu_Button = findViewById(R.id.campaign_end_mainmenu_button);
                            Campaign_Banner_MainMenu_Button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPlayerLife = 0;
                                    currentEnemyLife = 0;
                                    stopService(gameplay_sound);
                                    startService(svc);
                                    toMainMenu();
                                }
                            });
                        }
                        else {
                            missionCompletePopUp.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        Vibrator v2 = (Vibrator) Stage1_a_Activity.this.getSystemService(Context.VIBRATOR_SERVICE);
                        v2.vibrate(250);

                        currentPlayerLife = currentPlayerLife - enemyDamage;
                        playerLifeBar.setProgress(currentPlayerLife);

                        if (chosenlevel > 1) {
                            enemyAttackSFX = MediaPlayer.create(Stage1_a_Activity.this, R.raw.enemy_gun_sfx);
                            enemyAttackSFX.start();
                            playerExplosionAnim();
                        }

                        enemyReloadTime = getEnemyReloadTimeRestart;
                        enemyAutomaticAttack();
                    }
                }
            }.start();
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

    public void openCampaignList(){
        MediaPlayer soundfx = MediaPlayer.create(Stage1_a_Activity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_open_campaign = new Intent(Stage1_a_Activity.this, CampaignListActivity.class);
        startActivity(intent_open_campaign);
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

    public void textColorBlink(){
        ObjectAnimator anim = ObjectAnimator.ofInt(enemyDistanceDisplay, "textColor", Color.BLACK, Color.RED,
                Color.BLACK);
        anim.setDuration(1000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.start();
    }

    public void gunfire_effect_animation(){
        gunfire_effect = findViewById(R.id.gunfire_effect);
        gunfire_effect.setRotation(90);
        gunfire_effect.setImageResource(R.drawable.main_gun_effect);
        AnimationDrawable gun_fire_effect_animation = (AnimationDrawable) gunfire_effect.getDrawable();
        gun_fire_effect_animation.start();
    }

    public void autocannon_fx_anim(){
        secondaryGunEffect = findViewById(R.id.secondary_gunfire_effect);
        secondaryGunEffect.setRotation(90);
        secondaryGunEffect.setImageResource(R.drawable.main_gun_effect);
        AnimationDrawable gun_fire_effect_animation = (AnimationDrawable) secondaryGunEffect.getDrawable();
        gun_fire_effect_animation.start();
    }

    public void autocannon2_fx_anim(){
        secondSecondaryGunEffect = findViewById(R.id.secondary_gunfire_effect_second);
        secondSecondaryGunEffect.setRotation(90);
        secondSecondaryGunEffect.setImageResource(R.drawable.main_gun_effect);
        AnimationDrawable gun_fire_effect_animation = (AnimationDrawable) secondSecondaryGunEffect.getDrawable();
        gun_fire_effect_animation.start();
    }

    public void playerExplosionAnim(){
        playerShipExplosion = findViewById(R.id.ship_player_fx);
        playerShipExplosion.setVisibility(View.VISIBLE);
        playerShipExplosion.setImageResource(R.drawable.explosion_effect);
        AnimationDrawable playerExplosion = (AnimationDrawable) playerShipExplosion.getDrawable();
        playerExplosion.start();
    }

    public void missileExplosionAnim(){
        ImageView explosionImpact = findViewById(R.id.explosion_fx);
        explosionImpact.setVisibility(View.VISIBLE);

        explosionImpact.setImageResource(R.drawable.explosion_effect);
        AnimationDrawable explosion_fx_animation = (AnimationDrawable) explosionImpact.getDrawable();
        explosion_fx_animation.start();
    }

    public void theatricalExplosionAnim(){
        ImageView theatrical_explosion_fx = findViewById(R.id.theatrical_explosion_fx);
        theatrical_explosion_fx.setVisibility(View.VISIBLE);

        theatrical_explosion_fx.setImageResource(R.drawable.explosion_effect);
        AnimationDrawable explosion_fx_animation = (AnimationDrawable) theatrical_explosion_fx.getDrawable();
        explosion_fx_animation.start();
    }

    public void theatricalLaunch(){
        ImageView theatrical_launch_fx = findViewById(R.id.missile_launch_1st);
        theatrical_launch_fx.setVisibility(View.VISIBLE);

        theatrical_launch_fx.setImageResource(R.drawable.theatrical_launch);
        AnimationDrawable explosion_fx_animation = (AnimationDrawable) theatrical_launch_fx.getDrawable();
        explosion_fx_animation.start();
    }

    public void setLevelMilestone(){

        if (chosenlevel == 1){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_2_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 2){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_3_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 3){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_4_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 4){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_5_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 5){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_6_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 6){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_7_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 7){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_8_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 8){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_9_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 9){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_10_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 10){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_11_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 11){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_12_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 12){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_13_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 13){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_14_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 14){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_15_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
        else if (chosenlevel == 15){
            SharedPreferences.Editor LevelEditor = getSharedPreferences(CampaignListActivity.levelSharedPreferences, MODE_PRIVATE).edit();
            LevelEditor.putInt("level_16_Unlocked", 1);
            LevelEditor.commit();
            LevelEditor.apply();
        }
    }
}
