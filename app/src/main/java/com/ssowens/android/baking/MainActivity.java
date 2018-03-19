package com.ssowens.android.baking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ssowens.android.baking.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView recyclerView;
    List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<Recipe>> call = service.getRecipeDetails();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(!response.isSuccessful()) {
                    Log.i(TAG, "Error Loading from API");
                    return;
                }
                List<Recipe> list = response.body();
                Log.i(TAG, "Sheila This is the response => " + response.body());
                Recipe recipe = null;
                for (int i = 0; i<list.size(); i++){
                    recipe = new Recipe();
                    int id = list.get(i).getId();
                    String name = list.get(i).getName();
                    recipe.setId(id);
                    recipe.setName(name);
                    recipeList.add(recipe);
                }

                RecipeRecyclerAdapter recyclerAdapter = new RecipeRecyclerAdapter(recipeList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity
                        .this, 1);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "Unexpected Error occurred when loading from API");
            }
        });

    }
}
