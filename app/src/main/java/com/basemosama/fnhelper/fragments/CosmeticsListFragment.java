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
import com.basemosama.fnhelper.adapters.CosmeticAdapter;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.utility.CosmeticService;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CosmeticsListFragment extends Fragment implements CosmeticAdapter.CosmeticItemClickListener {
    private RecyclerView cosmeticsRecyclerView;
    private CosmeticAdapter cosmeticAdapter;
    private ArrayList<MainItem> mainItems =new ArrayList<>();
    private Call<ArrayList<MainItem>> getCosmetics;

    public CosmeticsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cosmetic_list_fragment,container,false);
        cosmeticsRecyclerView=view.findViewById(R.id.cosmetic_list_rv);
        int noOfColumns=2;
        if(getContext()!=null){
            noOfColumns=getContext().getResources().getInteger(R.integer.no_of_columns);
        }
        if(savedInstanceState!=null){
            mainItems=savedInstanceState.getParcelableArrayList(Constant.COSMETIC_ITEMS_BUNDLE_KEY);
        }

        cosmeticsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),noOfColumns));
        cosmeticAdapter=new CosmeticAdapter(getContext(), mainItems,this);
        cosmeticsRecyclerView.setAdapter(cosmeticAdapter);

        if(savedInstanceState ==null && getContext()!=null){
        if(Functions.isNetworkAvailable(getContext())) {
            getCosmeticItems();
        }else{
            Toast.makeText(getContext(),getString(R.string.no_internet_message),Toast.LENGTH_SHORT).show();
        }
        }

        return view;
    }

    public void getCosmeticItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService=retrofit.create(CosmeticService.class);
        getCosmetics= cosmeticService.getCosmeticItems();
        getCosmetics.enqueue(new Callback<ArrayList<MainItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MainItem>> call, Response<ArrayList<MainItem>> response) {
                if(response.body()!=null) {
                    mainItems = response.body();
                    cosmeticAdapter.updateAdapter(mainItems);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MainItem>> call, Throwable t) {
                Log.i(getClass().getName(),t.getLocalizedMessage());
                if(getContext()!=null)
                Toast.makeText(getContext(), R.string.retrofit_error_message,Toast.LENGTH_SHORT).show();


            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getCosmetics!=null){
            getCosmetics.cancel();
        }
        cosmeticAdapter.stopLoading();
        cosmeticsRecyclerView.setAdapter(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(Constant.COSMETIC_ITEMS_BUNDLE_KEY,mainItems);
    }


    @Override
    public void onCosmeticItemClickListener(int position) {
        Intent intent=new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY, mainItems.get(position).getIdentifier());
        startActivity(intent);
    }
}
