package com.soyinka.soyombo.naijaplusworldnews;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView naijaImageView;
    private ImageView worldImageView;
    private int count;
    private int news;
    private InterstitialAd interstitialAd;
    private Integer images[] = {R.drawable.local5 };

    private Integer worldImages[] = {R.drawable.world1, R.drawable.world2, R.drawable.world3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544-3347511713");
        AdView mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());


        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());


        count = 5;
        news = -1;

        naijaImageView = findViewById(R.id.naija_image);
        worldImageView = findViewById(R.id.world_image);

        naijaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news = 1;
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                } else {
                    startIntent(news);
                }
            }
        });

        worldImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                news = 2;
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                } else {
                    startIntent(news);
                }
            }
        });

        interstitialAd.setAdListener( new AdListener(){
            @Override
            public void onAdClosed() {
                startIntent(news);
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    private void startIntent(int news) {
        startActivity(new Intent(MainActivity.this, NewsActivity.class )
                .putExtra("news", news));
    }

}