package com.basemosama.fnhelper.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basemosama.fnhelper.constants.Constant;
import com.basemosama.fnhelper.constants.Functions;
import com.basemosama.fnhelper.CosmeticActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ItemShopAdapter;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemShopFragment extends Fragment implements ItemShopAdapter.ItemShopClickListener {
    private RecyclerView itemShopRecyclerView;
    private ItemShopAdapter itemShopAdapter;
    private ArrayList<ItemShopItems> itemShopItems = new ArrayList<>();
    private Call<ItemShop> getItemShop;
    private File imagePath;
    public static final String TAG = "ItemShopFragment";

    public ItemShopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_shop_fragment, container, false);
        itemShopRecyclerView = view.findViewById(R.id.item_shop_rv);
        int noOfColumns = 2;
        if (getContext() != null) {
            noOfColumns = getContext().getResources().getInteger(R.integer.no_of_columns);
        }

        if (savedInstanceState != null) {
            itemShopItems = savedInstanceState.getParcelableArrayList(Constant.ITEM_SHOP_BUNDLE_KEY);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), noOfColumns);
        itemShopRecyclerView.setLayoutManager(gridLayoutManager);
        itemShopAdapter = new ItemShopAdapter(getContext(), itemShopItems, this);
        itemShopRecyclerView.setAdapter(itemShopAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), R.string.item_shop_scroll_message, Toast.LENGTH_SHORT).show();
                if (Functions.isStoragePermissionGranted(getContext(), getActivity()))
                    shareItemShopImage();
            }
        });


        if (savedInstanceState == null && getContext() != null) {
            if (Functions.isNetworkAvailable(getContext())) {
                getItemShop();
            } else {
                Toast.makeText(getContext(), getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        }
        return view;
    }


    public void getItemShop() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService = retrofit.create(CosmeticService.class);

        getItemShop = cosmeticService.getItemShop();
        getItemShop.enqueue(new Callback<ItemShop>() {
            @Override
            public void onResponse(Call<ItemShop> call, Response<ItemShop> response) {
                if (response.body() != null) {
                    itemShopItems = response.body().getItems();
                    itemShopAdapter.updateAdapter(itemShopItems);
                }
            }

            @Override
            public void onFailure(Call<ItemShop> call, Throwable t) {
                Log.i(getClass().getName(), t.getLocalizedMessage());
                if (getContext() != null)
                    Toast.makeText(getContext(), R.string.retrofit_error_message, Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getItemShop != null) {
            getItemShop.cancel();
        }
        itemShopAdapter.stopLoading();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constant.ITEM_SHOP_BUNDLE_KEY, itemShopItems);
    }


    private void shareItemShopImage() {
        File imagePath = Functions.saveAndGetScreenShot(true, itemShopRecyclerView);
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = getString(R.string.today_item_shop);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.item_shop));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));

    }


    @Override
    public void onItemShopClickListener(int position) {
        Toast.makeText(getContext(), itemShopItems.get(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY, itemShopItems.get(position).getItemid());

        startActivity(intent);
    }
}
