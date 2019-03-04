package com.basemosama.fnhelper.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

import java.util.List;

@Dao
public interface CosmeticDao  {


    @Query("SELECT * FROM favorites ORDER BY id DESC")
    LiveData<List<MainItem>> getFavourites();

    @Insert
    void insertItem (MainItem item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem (MainItem item);

    @Delete
    void deleteItem (MainItem item);


    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE cosmetic_identifier = :cosmeticId)")
    LiveData<Boolean> isInFavourite(String cosmeticId);

    @Query("DELETE FROM favorites WHERE cosmetic_identifier = :id")
    void deleteItemById (String id);



}
