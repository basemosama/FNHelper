package com.basemosama.fnhelper.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.basemosama.fnhelper.adapters.CosmeticAdapter;
import com.basemosama.fnhelper.database.MainViewModel;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListFragment extends Fragment implements CosmeticAdapter.CosmeticItemClickListener {
    private RecyclerView favoritesRecyclerView;
    private CosmeticAdapter cosmeticAdapter;
    private List<MainItem> favoriteList =new ArrayList<>();

    public FavoriteListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.favorites_fragment,container,false);
        favoritesRecyclerView =view.findViewById(R.id.favorite_list_rv);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        favoritesRecyclerView.setLayoutManager(gridLayoutManager);
        cosmeticAdapter=new CosmeticAdapter(getContext(), favoriteList,this);
        favoritesRecyclerView.setAdapter(cosmeticAdapter);
        getFavorites();

        return view;
    }


    public void getFavorites(){
        MainViewModel mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getFavorites().observe(this, new Observer<List<MainItem>>() {
            @Override
            public void onChanged(@Nullable List<MainItem> mainItems) {
                Log.i("favoritelist","changing");
                Log.i("favoritelist", String.valueOf(mainItems.size()));

                cosmeticAdapter.updateAdapter(mainItems);

            }
        });

        }



    @Override
    public void onCosmeticItemClickListener(int position) {

        Intent intent=new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY, favoriteList.get(position).getIdentifier());
        //intent.putExtra(Constant.INTENT_FAVORITE_KEY,favoriteList);
        Toast.makeText(getContext(), favoriteList.get(position).getName(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cosmeticAdapter.stopLoading();
        favoritesRecyclerView.setAdapter(null);
    }
}
