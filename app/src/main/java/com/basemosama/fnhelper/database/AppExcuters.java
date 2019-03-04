package com.basemosama.fnhelper.database;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExcuters {
        private static final Object LOCK = new Object();
        private static AppExcuters sInstance;
        private final Executor diskIo;


        public Executor getDiskIo() {
            return diskIo;
        }


        private AppExcuters(Executor diskIo) {
            this.diskIo = diskIo;
        }

        public static AppExcuters getExcuter() {
            if (sInstance == null) {
                synchronized (LOCK) {
                    sInstance = new AppExcuters(
                            Executors.newSingleThreadExecutor());
                }
            }

            return sInstance;
        }



    }
