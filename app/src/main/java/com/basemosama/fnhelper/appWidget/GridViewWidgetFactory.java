package com.basemosama.fnhelper.appWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.basemosama.fnhelper.constants.Constant;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridViewWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
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
        itemShopItems=UpdateWidgetService.getItemShopItems();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        if(itemShopItems== null){
            return 0;}

        return itemShopItems.size();

    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews remoteViews= new RemoteViews(context.getPackageName(), R.layout.cosmetic_grid_widget_item);
        try {
            Bitmap bitmap= Picasso
                    .get()
                    .load(itemShopItems.get(i).getItem().getImages().getInformation())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .get();
            remoteViews.setImageViewBitmap(R.id.cosmetic_grid_widget_image,bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle bundle=new Bundle();
        bundle.putString(Constant.INTENT_ID_KEY,itemShopItems.get(i).getItemid());
        Intent intent=new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.cosmetic_grid_widget_image,intent);
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
