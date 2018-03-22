package com.ssowens.android.baking.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssowens.android.baking.services.ApiService;
import com.ssowens.android.baking.R;
import com.ssowens.android.baking.adapters.RecipeRecyclerAdapter;
import com.ssowens.android.baking.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeCardsFragment extends Fragment {

    private static final String TAG = RecipeCardsFragment.class.getSimpleName();
    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";

    List<Recipe> recipeList = new ArrayList<>();
    RecipeRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;

    public RecipeCardsFragment() {
        // Required empty public constructor
    }

    public static RecipeCardsFragment newInstance() {
        RecipeCardsFragment fragment = new RecipeCardsFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<Recipe>> call = service.getRecipeDetails();
        recyclerAdapter = new RecipeRecyclerAdapter();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                try {
                    recipeList = response.body();
                    Log.e(TAG, "onResponse  " + "Recipe List size = " + response.body().size());
                    //   Log.e(TAG,"onResponse"+ "Response Body " + response.body());
                    Log.i(TAG, "Sheila *** recipeList " + recipeList.size());
                    recyclerAdapter.setRecipeList(recipeList);
                    recyclerAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse" + "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "onFailure" + t.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView()");
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(recyclerAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }
}
