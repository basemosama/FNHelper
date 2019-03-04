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
import com.basemosama.fnhelper.CosmeticActivity;
import com.basemosama.fnhelper.R;
import com.basemosama.fnhelper.adapters.ItemShopAdapter;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShop;
import com.basemosama.fnhelper.objects.ItemShopObjects.ItemShopItems;
import com.basemosama.fnhelper.utility.CosmeticService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemShopFragment extends Fragment implements ItemShopAdapter.ItemShopClickListener {
    private RecyclerView itemShopRecyclerView;
    private ItemShopAdapter itemShopAdapter;
    private List<ItemShopItems> itemShopItems =new ArrayList<>();
    private Call<ItemShop> getItemShop;

    public ItemShopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.item_shop_fragment,container,false);
        itemShopRecyclerView =view.findViewById(R.id.item_shop_rv);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        itemShopRecyclerView.setLayoutManager(gridLayoutManager);
        itemShopAdapter =new ItemShopAdapter(getContext(), itemShopItems,this);
        itemShopRecyclerView.setAdapter(itemShopAdapter);
        getItemShop();

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
                Log.i("itemShop", t.getLocalizedMessage());

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
}
