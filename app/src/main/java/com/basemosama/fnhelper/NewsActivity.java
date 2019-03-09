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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.basemosama.fnhelper.constants.Constant;
import com.basemosama.fnhelper.objects.NewsObjects.NewsEntries;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NewsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NewsEntries news;
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
        intent.putExtra(Constant.INTENT_MAIN_NAV_ID_KEY,id);
        startActivity(intent);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    private void setUpUi(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageView newsImage = findViewById(R.id.news_activity_image);
        newsDescription =findViewById(R.id.news_description);
        newsDate =findViewById(R.id.news_date);
        TextView newsName = findViewById(R.id.news_name);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        supportPostponeEnterTransition();
        news=getIntent().getParcelableExtra(Constant.INTENT_news_KEY);
        Picasso.get()
                .load(news.getImage())
                .into(newsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();

                    }
                });

        newsName.setText(news.getTitle());
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
