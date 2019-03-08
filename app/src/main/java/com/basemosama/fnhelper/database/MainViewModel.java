package com.basemosama.fnhelper.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private LiveData<List<MainItem>> favoritesListLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        CosmeticDatabase cosmeticDatabase=CosmeticDatabase.getInstance(this.getApplication());
       favoritesListLiveData =cosmeticDatabase.cosmeticDao().getFavourites();
    }
    public LiveData<List<MainItem>> getFavorites() {
        return favoritesListLiveData;
    }
}
