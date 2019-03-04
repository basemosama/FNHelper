package com.basemosama.fnhelper.appWidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

import java.util.ArrayList;


public class GridRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridViewWidgetFactory(this.getApplicationContext(),intent);
    }
}


