package com.basemosama.fnhelper.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class CheckCosmeticViewModel extends ViewModel {

    private LiveData<Boolean> isInFavorite;
    public CheckCosmeticViewModel(CosmeticDatabase cosmeticDatabase, String cosmeticId) {
        isInFavorite=cosmeticDatabase.cosmeticDao().isInFavourite(cosmeticId);
    }

    public LiveData<Boolean> isInFavorite() {
        return isInFavorite;
    }

}
