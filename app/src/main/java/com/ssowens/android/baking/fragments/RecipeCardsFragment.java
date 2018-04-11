package com.ssowens.android.baking.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ssowens.android.baking.R;
import com.ssowens.android.baking.RecipeCollection;
import com.ssowens.android.baking.adapters.RecipeCardsAdapter;
import com.ssowens.android.baking.models.Recipe;
import com.ssowens.android.baking.services.ApiService;

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
    public static final int DEVICE_TABLET_THRESHOLD = 2;

    List<Recipe> recipeList = new ArrayList<>();
    RecipeCardsAdapter recyclerAdapter;
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
        setHasOptionsMenu(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<Recipe>> call = service.getRecipeDetails();
        recyclerAdapter = new RecipeCardsAdapter();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                try {
                    recipeList = response.body();
                    RecipeCollection.get(getActivity()).addListRecipeCollection(recipeList);
                    recyclerAdapter.setRecipeList(recipeList);
                    recyclerAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse" + getString(R.string.error_occurred));
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

        if (getContext().getResources().getInteger(R.integer.device_layout_type) > DEVICE_TABLET_THRESHOLD) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        recyclerView.setAdapter(recyclerAdapter);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Toast.makeText(getContext(), getString(R.string.favorite_selected), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_recipe_cards_menu, menu);
    }

}
