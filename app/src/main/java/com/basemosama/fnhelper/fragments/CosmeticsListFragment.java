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
import com.basemosama.fnhelper.adapters.CosmeticAdapter;
import com.basemosama.fnhelper.objects.CosmeticItemsObjects.MainItem;
import com.basemosama.fnhelper.utility.CosmeticService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CosmeticsListFragment extends Fragment implements CosmeticAdapter.CosmeticItemClickListener {
    private RecyclerView cosmeticsRecyclerView;
    private CosmeticAdapter cosmeticAdapter;
    private List<MainItem> mainItems =new ArrayList<>();
    private Call<List<MainItem>> getCosmetics;

    public CosmeticsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.cosmetic_list_fragment,container,false);
        cosmeticsRecyclerView=view.findViewById(R.id.cosmetis_list_rv);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        cosmeticsRecyclerView.setLayoutManager(gridLayoutManager);
        cosmeticAdapter=new CosmeticAdapter(getContext(), mainItems,this);
        cosmeticsRecyclerView.setAdapter(cosmeticAdapter);

        getCosmeticItems();

        return view;
    }

    public void getCosmeticItems(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CosmeticService cosmeticService=retrofit.create(CosmeticService.class);
        getCosmetics= cosmeticService.getCosmeticItems();
        getCosmetics.enqueue(new Callback<List<MainItem>>() {
            @Override
            public void onResponse(Call<List<MainItem>> call, Response<List<MainItem>> response) {
                if(response.body()!=null) {
                    mainItems = response.body();
                    cosmeticAdapter.updateAdapter(mainItems);
                }

            }

            @Override
            public void onFailure(Call<List<MainItem>> call, Throwable t) {
                Log.i("myretrofit",t.getLocalizedMessage());


            }
        });


    }

    @Override
    public void onCosmeticItemClickListener(int position) {

        Intent intent=new Intent(getContext(), CosmeticActivity.class);
        intent.putExtra(Constant.INTENT_ID_KEY, mainItems.get(position).getIdentifier());
        Toast.makeText(getContext(), mainItems.get(position).getName(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getCosmetics!=null){
            getCosmetics.cancel();
        }
        Toast.makeText(getContext(),"destroying",Toast.LENGTH_SHORT).show();
        cosmeticAdapter.stopLoading();
        cosmeticsRecyclerView.setAdapter(null);
    }
}
