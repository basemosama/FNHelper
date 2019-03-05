package com.basemosama.fnhelper.appWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class GridRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridViewWidgetFactory(this.getApplicationContext(),intent);
    }
}


