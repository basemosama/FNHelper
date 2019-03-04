package com.basemosama.fnhelper;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.adapters.ImagesAdapter;
import com.basemosama.fnhelper.appWidget.ItemShopWidget;
import com.basemosama.fnhelper.appWidget.UpdateWidgetService;
import com.basemosama.fnhelper.database.AppExcuters;
import com.basemosama.fnhelper.database.CheckCosmeticViewModel;
import com.basemosama.fnhelper.database.CheckCosmeticViewModelFactory;
import com.basemosama.fnhelper.database.CosmeticDatabase;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.CosmeticImages;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.utility.CosmeticService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CosmeticActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String cosmeticIdentifier;
    private MainItem cosmeticItem;
    private ImageView itemImage;
    private TextView itemName,itemDescription,itemType,itemCost,itemOnTheStore,itemReleaseDate,itemLastDate,itemRating,itemOccurances;
    private TextView itemDescriptionText,itemTypeText,itemCostText,itemReleaseDateText,itemLastDateText,itemRatingText,itemOccurancesText;

    private ScrollView itemScrollView;
    private String upcomingText,cost,rating,description,releaseDate,type,imageUrl,lastDate,occurances ;
    private List<String> images;
    RecyclerView imagesRecyclerView;
    ImagesAdapter imagesAdapter;
    private Toolbar toolbar;
    CosmeticDatabase cosmeticDatabase;
    LiveData<Boolean> favoriteLiveData;
    boolean isInFavourite=false;
    private MenuItem favroiteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic);

        cosmeticDatabase=CosmeticDatabase.getInstance(this);

        setUpUi();
        getMainItem();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cosmetic, menu);
         favroiteItem=menu.getItem(1);
        checkFavorites();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_favourite){


            updateFavorites();

        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getMainItem() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService = retrofit.create(CosmeticService.class);
        Call<MainItem> getItem = cosmeticService.getMainItem(cosmeticIdentifier);
        getItem.enqueue(new Callback<MainItem>() {
            @Override
            public void onResponse(Call<MainItem> call, Response<MainItem> response) {
                if (response.body() != null) {
                    cosmeticItem = response.body();
                    Log.i("myretrofit", cosmeticItem.getName() );
                    updateUi();
                }
                Log.i("myretrofit", "done" );


            }

            @Override
            public void onFailure(Call<MainItem> call, Throwable t) {
                Log.i("myretrofit", t.getLocalizedMessage());
            }
        });
    }



    private void updateUi(){

        checkItemText();
        setUpItemText();


    }


    private void checkItemText(){

         imageUrl=cosmeticItem.getImages().getBackground();

         rating="( "+cosmeticItem.getRatings().getAvgStars() + " / 5 )";
         description=cosmeticItem.getDescription();
         releaseDate=cosmeticItem.getOccurrences().getFirst();
         lastDate= String.valueOf(cosmeticItem.getOccurrences().getLast());
         occurances= String.valueOf(cosmeticItem.getOccurrences().getOccurrences());
         upcomingText="";

        //cost
        if(cosmeticItem.getObtained_type().equals("battlepass")) {
            cost=cosmeticItem.getObtained();
            itemCostText.setText(R.string.battle_pass_text);
        }else {
            cost = cosmeticItem.getObtained() + " " + cosmeticItem.getObtained_type();
        }
        if(TextUtils.isEmpty(cost) || cost.equals("???")) {
            cost="unavailable";
        }

        //upcoming
        if(cosmeticItem.getUpcoming()==1){
            upcomingText="Upcoming ";
            cost="Not available yet";
           releaseDate= lastDate = occurances="None";

         /*   itemOccurances.setVisibility(View.GONE);
            itemLastDate.setVisibility(View.GONE);
            itemReleaseDate.setVisibility(View.GONE);
            itemLastDateText.setVisibility(View.GONE);
            itemLastDateText.setVisibility(View.GONE);
            itemOccurancesText.setVisibility(View.GONE);*/

        }
        type=upcomingText +cosmeticItem.getRarity() + " "+cosmeticItem.getType();


        if(TextUtils.isEmpty(releaseDate) || releaseDate.equals("--")) {
            releaseDate="Not Available";
        }
        if(TextUtils.isEmpty(lastDate) || lastDate.equals("--")) {
            lastDate="Not Available";
        }
        if(TextUtils.isEmpty(occurances)) {
            occurances="Not Available";
        }
        if(TextUtils.isEmpty(rating)) {
            rating="Not Available";
        }
        if(TextUtils.isEmpty(description)) {
            itemDescriptionText.setVisibility(View.GONE);
            itemDescription.setVisibility(View.GONE);
        }
        if(cosmeticItem.getTodaystore()!=1)
            itemOnTheStore.setVisibility(View.GONE);




    }
    private void setUpItemText(){


        itemScrollView.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.placeholder1)
                .into(itemImage);

        //itemName.setText(cosmeticItem.getName());
        setTitle(cosmeticItem.getName());
        itemDescription.setText(description);
        itemType.setText(type);
        itemCost.setText(cost);
        itemReleaseDate.setText(releaseDate);
        itemLastDate.setText(lastDate);
        itemOccurances.setText(occurances);
        itemRating.setText(rating);
        imagesAdapter=new ImagesAdapter(this,CosmeticImages.getImages(cosmeticItem.getImages()));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        imagesRecyclerView.setLayoutManager(linearLayoutManager);
        imagesRecyclerView.setAdapter(imagesAdapter);

    }
    private void setUpUi(){
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemImage=findViewById(R.id.item_image);
        itemName=findViewById(R.id.item_name);
        itemDescription=findViewById(R.id.item_description);
        itemCost=findViewById(R.id.item_cost);
        itemType=findViewById(R.id.item_type);
        itemReleaseDate=findViewById(R.id.item_release_date);
        itemLastDate=findViewById(R.id.item_last_date);
        itemOccurances=findViewById(R.id.item_ocurrances);
        itemOnTheStore=findViewById(R.id.item_on_the_store);
        itemRating=findViewById(R.id.item_rating);
        itemDescriptionText=findViewById(R.id.item_description_text);
        itemCostText=findViewById(R.id.item_cost_text);
        itemTypeText=findViewById(R.id.item_type_text);
        itemReleaseDateText=findViewById(R.id.item_release_date_text);
        itemLastDateText=findViewById(R.id.item_last_date_text);
        itemOccurancesText=findViewById(R.id.item_ocurrances_text);
        itemRatingText=findViewById(R.id.rating_text);
        imagesRecyclerView=findViewById(R.id.images_rv);
        itemScrollView =findViewById(R.id.item_scroll_view);
        Intent intent=getIntent();

        if(intent.hasExtra(Constant.INTENT_ID_KEY)){
            cosmeticIdentifier=getIntent().getStringExtra(Constant.INTENT_ID_KEY);
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void checkFavorites(){
        CheckCosmeticViewModelFactory checkCosmeticViewModelFactory=new CheckCosmeticViewModelFactory(cosmeticDatabase,cosmeticIdentifier);
        CheckCosmeticViewModel checkCosmeticViewModel= ViewModelProviders.of(this,checkCosmeticViewModelFactory).get(CheckCosmeticViewModel.class);
        checkCosmeticViewModel.isInFavorite().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean value) {
                if(value!=null)
                    isInFavourite=value;
                Log.i("isInFavorite", String.valueOf(value));
                if(isInFavourite){
                    favroiteItem.setIcon(R.drawable.remove_from_favourite);
                }else {
                    favroiteItem.setIcon(R.drawable.add_to_favoutite);

                }

            }
        });

    }


    private void updateFavorites() {
        if(isInFavourite){

            AppExcuters.getExcuter().getDiskIo().execute(new Runnable() {
                @Override
                public void run() {
                    cosmeticDatabase.cosmeticDao().deleteItemById(cosmeticIdentifier);
                    Toast.makeText(getApplicationContext(),cosmeticItem.getName()+" has been removed from Favorites.",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            AppExcuters.getExcuter().getDiskIo().execute(new Runnable() {
                @Override
                public void run() {
                    cosmeticDatabase.cosmeticDao().insertItem(cosmeticItem);
                    Toast.makeText(getApplicationContext(),cosmeticItem.getName()+" has been added to Favorites.",Toast.LENGTH_SHORT).show();

                }
            });

        }

    }
    }
