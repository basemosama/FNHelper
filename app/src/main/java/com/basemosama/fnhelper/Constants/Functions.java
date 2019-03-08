package com.basemosama.fnhelper.Constants;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Functions {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if(connectivityManager != null)
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

        return false;
    }
    public static File saveAndGetScreenShot(Boolean isItemShop, RecyclerView recyclerView) {
        final String TAG ="Functions";

        recyclerView.scrollToPosition(0);
        recyclerView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        Bitmap bm = Bitmap.createBitmap(recyclerView.getWidth(), recyclerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        recyclerView.draw(new Canvas(bm));

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        String imageName="/ItemShop.png";
        if(!isItemShop){
            imageName="/UpcomingItems.png";
        }

       File imagePath = new File(Environment.getExternalStorageDirectory() + imageName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return imagePath;
    }

    public static boolean isStoragePermissionGranted(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(context!=null){
                if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v(Constant.PERMISSION_TAG,"Permission is granted");
                    return true;
                } else {

                    Log.v(Constant.PERMISSION_TAG,"Permission is revoked");
                    if(activity!=null)
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    return false;
                }
            }
            return false;
        }
        else {
            Log.v(Constant.PERMISSION_TAG,"Permission is granted");
            return true;
        }


    }


}
