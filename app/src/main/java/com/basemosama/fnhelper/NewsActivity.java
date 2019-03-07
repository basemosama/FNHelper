package com.basemosama.fnhelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.objects.NewsObjects.NewsEntries;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NewsEntries news;
    private ImageView newsImage;
    TextView newsDescription,newsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setUpUi();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent intent =new Intent(this,MainActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void setUpUi(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        newsImage=findViewById(R.id.news_activity_image);
        newsDescription =findViewById(R.id.news_description);
        newsDate =findViewById(R.id.news_date);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        news=getIntent().getParcelableExtra(Constant.INTENT_news_KEY);
        setTitle(news.getTitle());
        Picasso.get()
                .load(news.getImage())
                .into(newsImage);
        newsDescription.setText(news.getBody());
        Date date=new Date(Long.valueOf(news.getTime())*1000);
        Format format = new SimpleDateFormat("HH:mm a - dd/MM/yyyy", Locale.US);
        String time= format.format(date);
        newsDate.setText(time);

        final NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
