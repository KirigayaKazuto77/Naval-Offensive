package com.test.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    private ImageButton backButton, missile1, missile2, missile4, gun1, gun2, gun3, autocannon1, autocannon2;
    private TextView displayMoney;
    private String displayMoneyShop;
    private int money, missile, gun, autocannon;

    public static String moneySharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        hideNavBar();

        displayMoney = findViewById(R.id.money);

        //******************************************************************************************
        //****************************************************************** SHAREDPREFERENCE INITIALIZATION START
        SharedPreferences sharedPreferences = getSharedPreferences(moneySharedPreference, MODE_PRIVATE);
        money = sharedPreferences.getInt("money", 10000);
        missile = sharedPreferences.getInt("missile", 0);
        gun = sharedPreferences.getInt("gun", 10000);
        autocannon = sharedPreferences.getInt("autocannon", 0);

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
        if(missile >= 1){
            missile1.setVisibility(View.GONE);
        }
        missile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile1();
            }
        });


        missile2 = findViewById(R.id.shop_buy_missile_twin);
        if(missile >= 2){
            missile2.setVisibility(View.GONE);
        }
        missile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile2();
            }
        });

        missile4 = findViewById(R.id.shop_buy_missile_quad);
        if(missile >= 4){
            missile4.setAlpha(0.5f);
            missile4.setEnabled(false);
        }
        missile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyMissile4();
            }
        });


        gun1 = findViewById(R.id.shop_install_gun_otobreda);
        if (gun <= 7000){
            gun1.setVisibility(View.GONE);
        }
        gun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun1();
            }
        });

        gun2 = findViewById(R.id.shop_install_gun_otomelara_mk1);
        if (gun <= 5000){
            gun2.setVisibility(View.GONE);
        }
        gun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun2();
            }
        });

        gun3 = findViewById(R.id.shop_install_gun_otomelara_mk3);
        if(gun <= 3000){
            gun3.setAlpha(0.5f);
            gun3.setEnabled(false);
        }
        gun3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyGun3();
            }
        });


        autocannon1 = findViewById(R.id.shop_buy_autocannon_single);
        if (autocannon >= 5){
            autocannon1.setVisibility(View.GONE);
        }
        autocannon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAutocannon1();
            }
        });

        autocannon2 = findViewById(R.id.shop_buy_autocannon_double);
        if(autocannon >= 10){
            autocannon2.setAlpha(0.5f);
            autocannon2.setEnabled(false);
        }
        autocannon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyAutocannon2();
            }
        });
        //****************************************************************** IMAGE BUTTON INITIALIZATION END
        //******************************************************************************************
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

    public void buyMissile1(){
        if(money >= 100) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            missile1.setVisibility(View.GONE);
            money = money - 100;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("money", money);
            editor.putInt("missile", 1);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Missiles: " + 1, Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyMissile2(){
        if(money >= 200) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            missile2.setVisibility(View.GONE);
            money = money - 200;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("missile", 2);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Missiles: " + 2, Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyMissile4(){
        if(money >= 400) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            missile4.setAlpha(0.5f);
            missile4.setEnabled(false);
            money = money - 400;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("missile", 4);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Missiles: " + 4, Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyGun1(){
        if(money >= 100) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            gun1.setVisibility(View.GONE);
            gun = gun - 3000;
            money = money - 100;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("gun", gun);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Gun Reload Time: " + gun/1000 + "s", Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyGun2(){
        if(money >= 200) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            gun2.setVisibility(View.GONE);
            gun = gun - 2000;
            money = money - 200;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("gun", gun);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Gun Reload Time: " + gun/1000 + "s", Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyGun3(){
        if(money >= 300) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            gun3.setAlpha(0.5f);
            gun3.setEnabled(false);
            gun = gun - 2000;
            money = money - 300;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("gun", gun);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Gun Reload Time: " + gun/1000 + "s", Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyAutocannon1(){
        if(money >= 200) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            autocannon1.setVisibility(View.GONE);
            autocannon = autocannon + 5;
            money = money - 200;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("autocannon", autocannon);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Autocannon Damage: " + autocannon, Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyAutocannon2(){
        if(money >= 200) {
            MediaPlayer soundfx = MediaPlayer.create(ShopActivity.this, R.raw.gear_sfx);
            soundfx.start();

            autocannon2.setAlpha(0.5f);
            autocannon2.setEnabled(false);
            autocannon = autocannon + 5;
            money = money - 200;
            SharedPreferences.Editor editor = getSharedPreferences(moneySharedPreference, MODE_PRIVATE).edit();
            editor.putInt("autocannon", autocannon);
            editor.putInt("money", money);
            editor.commit();
            editor.apply();

            Toast.makeText(this, "Autocannon Damage: " + autocannon, Toast.LENGTH_LONG).show();

            displayMoneyShop = String.valueOf(money);
            displayMoney.setText(displayMoneyShop);
        }
        else {
            Toast.makeText(this, "Not Enough Money!", Toast.LENGTH_LONG).show();
        }
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
