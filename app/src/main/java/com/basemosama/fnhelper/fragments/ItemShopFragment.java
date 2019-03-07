package com.basemosama.fnhelper.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.Constants.Functions;
import com.basemosama.fnhelper.CosmeticActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ItemShopAdapter;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemShopFragment extends Fragment implements ItemShopAdapter.ItemShopClickListener {
    private  RecyclerView itemShopRecyclerView;
    private ItemShopAdapter itemShopAdapter;
    private ArrayList<ItemShopItems> itemShopItems =new ArrayList<>();
    private Call<ItemShop> getItemShop;
    private  File imagePath;
    public static final String TAG ="ItemShopFragment";

    public ItemShopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_shop_fragment,container,false);
        itemShopRecyclerView =view.findViewById(R.id.item_shop_rv);
        int noOfColumns=2;
        if(getContext()!=null){
            noOfColumns=getContext().getResources().getInteger(R.integer.no_of_columns);
        }

        if(savedInstanceState!=null){
            itemShopItems=savedInstanceState.getParcelableArrayList(Constant.ITEM_SHOP_BUNDLE_KEY);
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),noOfColumns);
        itemShopRecyclerView.setLayoutManager(gridLayoutManager);
        itemShopAdapter =new ItemShopAdapter(getContext(), itemShopItems,this);
        itemShopRecyclerView.setAdapter(itemShopAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Toast.makeText(getContext(), R.string.item_shop_scroll_message,Toast.LENGTH_SHORT).show();
                if(isStoragePermissionGranted())
                shareItemShopImage();
            }
        });


        if(savedInstanceState==null && getContext()!=null){
            if(Functions.isNetworkAvailable(getContext())) {
            getItemShop();
        }else{
            Toast.makeText(getContext(),getString(R.string.no_internet_message),Toast.LENGTH_SHORT).show();
        }}
        return view;
    }




    public void getItemShop(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService=retrofit.create(CosmeticService.class);

        getItemShop= cosmeticService.getItemShop();
        getItemShop.enqueue(new Callback<ItemShop>() {
            @Override
            public void onResponse(Call<ItemShop> call, Response<ItemShop> response) {
                if (response.body() != null) {
                    itemShopItems=response.body().getItems();
                    itemShopAdapter.updateAdapter(itemShopItems);
                }
            }

            @Override
            public void onFailure(Call<ItemShop> call, Throwable t) {
                Log.i(getClass().getName(), t.getLocalizedMessage());
                Toast.makeText(getContext(), R.string.retrofit_error_message,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemShopClickListener(int position) {
        Toast.makeText(getContext(), itemShopItems.get(position).getName(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY,itemShopItems.get(position).getItemid());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getItemShop!=null){
            getItemShop.cancel();
        }
        itemShopAdapter.stopLoading();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constant.ITEM_SHOP_BUNDLE_KEY,itemShopItems);
    }

    private   void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
    private void shareItemShop() {
        Uri uri= Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Today's Item Shop";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Item Shop");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void  shareItemShopImage(){
        itemShopRecyclerView.scrollToPosition(0);
        itemShopRecyclerView.measure(
                View.MeasureSpec.makeMeasureSpec(itemShopRecyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        Bitmap bm = Bitmap.createBitmap(itemShopRecyclerView.getWidth(), itemShopRecyclerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        itemShopRecyclerView.draw(new Canvas(bm));
        saveBitmap(bm);
        shareItemShop();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
            shareItemShopImage();

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(getContext()!=null){
            if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                if(getActivity()!=null)
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        return false;
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }


    }



}
