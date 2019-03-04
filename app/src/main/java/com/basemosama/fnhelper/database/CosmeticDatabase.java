package com.basemosama.fnhelper.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

@Database(entities = MainItem.class ,version = 1,exportSchema = false)
public abstract class CosmeticDatabase extends RoomDatabase {
    public static final String LOG_TAG = CosmeticDatabase.class.getSimpleName();
    public static final Object LOCK =new Object();
    private static final String DATABASE_NAME="favoriteItems";
    public static CosmeticDatabase sInstance;

    public static CosmeticDatabase getInstance (Context context){
        synchronized (LOCK){
            if(sInstance==null){
                Log.i(LOG_TAG,"CreatingDatabase");
                sInstance= Room.databaseBuilder(context.getApplicationContext(),
                        CosmeticDatabase.class, DATABASE_NAME)
                        .build();

            }
        }
        return sInstance;
    }

    public abstract CosmeticDao cosmeticDao();

}
