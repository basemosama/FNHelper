package com.basemosama.fnhelper.database;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class CheckCosmeticViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final CosmeticDatabase cosmeticDatabase;
    private final String cosmeticId;


    public CheckCosmeticViewModelFactory(CosmeticDatabase cosmeticDatabase, String cosmeticId) {
        this.cosmeticDatabase = cosmeticDatabase;
        this.cosmeticId = cosmeticId;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CheckCosmeticViewModel(cosmeticDatabase,cosmeticId);
    }

}
