package com.test.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    private ImageButton backButton, missile1, missile2, missile4, gun1, gun2, gun3, autocannon1, autocannon2;
    private TextView displayMoney;
    private String displayMoneyShop;
    private int money;

    public static String moneySharedPreference = "moneyVariable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        displayMoney = findViewById(R.id.money);

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCE INITIALIZATION START
        SharedPreferences sharedPreferences = getSharedPreferences(moneySharedPreference, MODE_PRIVATE);
        money = sharedPreferences.getInt("money", 0);
        displayMoneyShop = String.valueOf(money);
        displayMoney.setText(displayMoneyShop);
        //****************************************************************** SHAREDPREFERENCE INITIALIZATION END
        //******************************************************************************************

        //******************************************************************************************
        //****************************************************************** IMAGE BUTTON INITIALIZATION START
        backButton = findViewById(R.id.shop_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoPlayMenuActivity();
            }
        });

        missile1 = findViewById(R.id.shop_buy_missile_single);
        missile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile1();
            }
        });

        missile2 = findViewById(R.id.shop_buy_missile_twin);
        missile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile2();
            }
        });

        missile4 = findViewById(R.id.shop_buy_missile_quad);
        missile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile4();
            }
        });


        gun1 = findViewById(R.id.shop_install_gun_otobreda);
        gun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun1();
            }
        });

        gun2 = findViewById(R.id.shop_install_gun_otomelara_mk1);
        gun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun2();
            }
        });

        gun3 = findViewById(R.id.shop_install_gun_otomelara_mk3);
        gun3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun3();
            }
        });


        autocannon1 = findViewById(R.id.shop_buy_autocannon_single);
        autocannon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAutocannon1();
            }
        });

        autocannon2 = findViewById(R.id.shop_buy_autocannon_double);
        autocannon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAutocannon2();
            }
        });
        //****************************************************************** IMAGE BUTTON INITIALIZATION END
        //******************************************************************************************
    }

    public void buyMissile1(){
        missile1.setVisibility(View.GONE);
    }

    public void buyMissile2(){
        missile2.setVisibility(View.GONE);
    }

    public void buyMissile4(){
        missile4.setAlpha(0.5f);
        missile4.setEnabled(false);
    }

    public void buyGun1(){
        gun1.setVisibility(View.GONE);
    }

    public void buyGun2(){
        gun2.setVisibility(View.GONE);
    }

    public void buyGun3(){
        gun3.setAlpha(0.5f);
        gun3.setEnabled(false);
    }

    public void buyAutocannon1(){
        autocannon1.setVisibility(View.GONE);
    }

    public void buyAutocannon2(){
        autocannon2.setAlpha(0.5f);
        autocannon2.setEnabled(false);
    }

    public void backtoPlayMenuActivity(){
        MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        Intent intent_backto_playmenu = new Intent(this, PlayMenuActivity.class);
        startActivity(intent_backto_playmenu);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed(){
        MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.button_click_sfx);
        soundfx.start();

        this.startActivity(new Intent(ShopActivity.this, PlayMenuActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return;
    }
}
