package com.basemosama.fnhelper.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basemosama.fnhelper.Constants.Constant;
import com.basemosama.fnhelper.Constants.Functions;
import com.basemosama.fnhelper.CosmeticActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ItemShopAdapter;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.objects.ItemShopObjects.UpcomingItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingItemsFragment extends Fragment implements ItemShopAdapter.ItemShopClickListener {
    private RecyclerView upcomingItemsRecyclerView;
    private ItemShopAdapter upcomingItemsAdapter;
    private ArrayList<ItemShopItems> upcomingItems =new ArrayList<>();
    private Call<UpcomingItems> getUpcomingItems;

    public UpcomingItemsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_shop_fragment,container,false);
        upcomingItemsRecyclerView =view.findViewById(R.id.item_shop_rv);
        int noOfColumns=2;
        if(getContext()!=null){
            noOfColumns=getContext().getResources().getInteger(R.integer.no_of_columns);
        }

        if(savedInstanceState!=null){
            upcomingItems=savedInstanceState.getParcelableArrayList(Constant.UPCOMING_ITEMS_BUNDLE_KEY);
        }

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),noOfColumns);
        upcomingItemsRecyclerView.setLayoutManager(gridLayoutManager);
        upcomingItemsAdapter =new ItemShopAdapter(getContext(), upcomingItems,this);
        upcomingItemsRecyclerView.setAdapter(upcomingItemsAdapter);
        if(savedInstanceState ==null && getContext()!=null){
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
        getUpcomingItems= cosmeticService.getUpcomingItems();
        getUpcomingItems.enqueue(new Callback<UpcomingItems>() {
            @Override
            public void onResponse(Call<UpcomingItems> call, Response<UpcomingItems> response) {
                if (response.body() != null) {
                    upcomingItems =response.body().getItems();
                    upcomingItemsAdapter.updateAdapter(upcomingItems);
                }
            }

            @Override
            public void onFailure(Call<UpcomingItems> call, Throwable t) {
                Log.i(getClass().getName(), t.getLocalizedMessage());
                Toast.makeText(getContext(), R.string.retrofit_error_message,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemShopClickListener(int position) {
        Toast.makeText(getContext(), upcomingItems.get(position).getName(),Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY,upcomingItems.get(position).getItemid());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getUpcomingItems!=null){
            getUpcomingItems.cancel();
        }
        upcomingItemsAdapter.stopLoading();
        upcomingItemsRecyclerView.setAdapter(null);


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constant.UPCOMING_ITEMS_BUNDLE_KEY,upcomingItems);
    }
}


