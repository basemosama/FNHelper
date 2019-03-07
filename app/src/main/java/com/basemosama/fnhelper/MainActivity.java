package com.basemosama.fnhelper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.fragments.ChallengesFragment;
import com.basemosama.fnhelper.fragments.CosmeticsListFragment;
import com.basemosama.fnhelper.fragments.FavoriteListFragment;
import com.basemosama.fnhelper.fragments.ItemShopFragment;
import com.basemosama.fnhelper.fragments.NewsFragment;
import com.basemosama.fnhelper.fragments.UpcomingItemsFragment;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private AdView mAdView;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawer;
    int currentFragment=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null)
            currentFragment=savedInstanceState.getInt(Constant.CURRENT_FRAGMENT_BUNDLE_KEY);
        setUpUi();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        handleNavigationClick(id);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void replaceFragment(int whichFragment){

        if(currentFragment!=whichFragment) {


            switch (whichFragment) {
                case 1:
                    CosmeticsListFragment cosmeticsListFragment = new CosmeticsListFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, cosmeticsListFragment)
                            .commit();
                    break;
                case 2:
                    ItemShopFragment itemShopFragment = new ItemShopFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, itemShopFragment)
                            .commit();

                    break;
                case 3:
                    UpcomingItemsFragment upcomingItemsFragment = new UpcomingItemsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, upcomingItemsFragment)
                            .commit();

                    break;
                case 4:
                    NewsFragment newsFragment = new NewsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, newsFragment)
                            .commit();
                    break;
                case 5:
                    ChallengesFragment challengesFragment = new ChallengesFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, challengesFragment)
                            .commit();

                    break;
                case 6:
                    FavoriteListFragment favoriteListFragment = new FavoriteListFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, favoriteListFragment)
                            .commit();
                    break;
            }
        currentFragment=whichFragment;


        }
    }

    private void setUpUi(){
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        fragmentManager=getSupportFragmentManager();
        if(fragmentManager.getFragments().isEmpty()) {
            CosmeticsListFragment cosmeticsListFragment = new CosmeticsListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, cosmeticsListFragment)
                    .commit();
        }

        setUpNavigationViews();

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent=getIntent();
        if(intent.hasExtra("id")){
            int id=intent.getIntExtra("id",R.id.nav_main);
            handleNavigationClick(id);
        }




    }
    private void setUpNavigationViews(){
        final NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.bottom_nav_cosmetics) {
                    // Handle the camera action
                    replaceFragment(1);
                    navigationView.setCheckedItem(R.id.nav_main);

                } else if (id == R.id.bottom_nav_item_shop) {
                    replaceFragment(2);
                    navigationView.setCheckedItem(R.id.nav_item_shop);

                } else if (id == R.id.bottom_nav_upcoming) {
                    replaceFragment(3);
                    navigationView.setCheckedItem(R.id.nav_upcoming);

                } else if (id == R.id.bottom_nav_news) {
                    replaceFragment(4);
                    navigationView.setCheckedItem(R.id.nav_news);

                } else if (id == R.id.bottom_nav_challenges) {
                    replaceFragment(5);
                    navigationView.setCheckedItem(R.id.nav_challenges);

                }
                return true;
            }
        });

    }
    public void  handleNavigationClick(int id){
        if (id == R.id.nav_main) {
            replaceFragment(1);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_cosmetics);

        } else if (id == R.id.nav_item_shop) {
            replaceFragment(2);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_item_shop);

        } else if (id == R.id.nav_upcoming) {
            replaceFragment(3);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_upcoming);

        } else if (id == R.id.nav_favorites) {
            replaceFragment(6);
            bottomNavigationView.setSelected(false);

        } else if (id == R.id.nav_news) {
            replaceFragment(4);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_news);

        } else if (id == R.id.nav_challenges) {
            replaceFragment(5);
            bottomNavigationView.setSelectedItemId(R.id.bottom_nav_challenges);

        }else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!");
            startActivity(shareIntent);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.CURRENT_FRAGMENT_BUNDLE_KEY,currentFragment
        );
    }



}
