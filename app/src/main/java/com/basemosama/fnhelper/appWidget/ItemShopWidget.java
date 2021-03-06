package com.basemosama.fnhelper.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.basemosama.fnhelper.CosmeticActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.database.CosmeticDatabase;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class ItemShopWidget extends AppWidgetProvider {


    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_shop_widget);
        UpdateWidgetService.startActionUpdateWidget(context);
        Intent intent=new Intent(context,GridRemoteService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
       intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.appwidget_grid_view,intent);



       Intent appIntent=new Intent(context, CosmeticActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_grid_view,pendingIntent);

        views.setEmptyView(R.id.appwidget_grid_view,R.id.empty_view);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

