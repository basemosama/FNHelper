package com.basemosama.fnhelper.appWidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.database.CosmeticDatabase;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateWidgetService extends IntentService {
    public static final String ACTION_UPDATE_WIDGET =
            "com.basemosama.fnhelper.appWidget.update_widget";
    public static List<MainItem> items=new ArrayList<>();
    public static List<ItemShopItems> itemShopList=new ArrayList<>();


    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
             //   getWidgetFavoriteItems();
                getWidgetItemShopItems();
            }
        }
    }

    private void getWidgetFavoriteItems() {
        CosmeticDatabase cosmeticDatabase = CosmeticDatabase.getInstance(getApplicationContext());
        LiveData<List<MainItem>> itemsLiveData = cosmeticDatabase.cosmeticDao().getFavourites();

        itemsLiveData.observeForever(new Observer<List<MainItem>>() {

            @Override
            public void onChanged(@Nullable List<MainItem> mainItems) {
                items=mainItems;
                Log.i("widget2", "changed");
                Log.i("widget2", String.valueOf(items.size()));
                updateMyWidgets(getApplicationContext());
            }
        });

    }

    private void getWidgetItemShopItems(){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CosmeticService cosmeticService=retrofit.create(CosmeticService.class);
        Call<ItemShop> getItemShop = cosmeticService.getItemShop();
        try {
            Response<ItemShop> response=getItemShop.execute();
            if (response.body() != null)
            itemShopList=response.body().getItems();

            updateMyWidgets(getApplicationContext());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static List<MainItem> getItems(){
        return items;
    }
    public static List<ItemShopItems> getItemShopItems(){
        return itemShopList;
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    public static void updateMyWidgets(Context context){
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
        int[]appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,ItemShopWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_grid_view);

    }
}
