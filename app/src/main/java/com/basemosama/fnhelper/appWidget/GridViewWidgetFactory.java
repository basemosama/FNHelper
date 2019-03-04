package com.basemosama.fnhelper.appWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.database.CosmeticDatabase;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridViewWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<MainItem> items=new ArrayList<>();
    private List<ItemShopItems> itemShopItems=new ArrayList<>();

    private int mAppWidgetId;

    public GridViewWidgetFactory(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        items=UpdateWidgetService.getItems();
        itemShopItems=UpdateWidgetService.getItemShopItems();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        if(itemShopItems== null){
            Log.i("widget13", String.valueOf(items.size()));
            return 0;}

        Log.i("widget12", String.valueOf(items.size()));
        return itemShopItems.size();

    }

    @Override
    public RemoteViews getViewAt(int i) {
       // Log.i("widget14", String.valueOf(items.size()));

        RemoteViews remoteViews= new RemoteViews(context.getPackageName(), R.layout.cosmetic_grid_widget_item);
       // Picasso.get().load(items.get(i).getImages().getInformation()).into(remoteViews,R.id.cosmetic_grid_widget_image,new int[mAppWidgetId]);
        try {
            Bitmap bitmap= Picasso
                    .get()
                    .load(itemShopItems.get(i).getItem().getImages().getInformation())
                    .placeholder(R.drawable.placeholder1)
                    .error(R.drawable.placeholder1)
                    .get();
            remoteViews.setImageViewBitmap(R.id.cosmetic_grid_widget_image,bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
