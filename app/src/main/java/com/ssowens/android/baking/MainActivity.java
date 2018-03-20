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

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net";

    RecyclerView recyclerView;
    List<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<List<Recipe>> call = service.getRecipeDetails();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                try {
                    List<Recipe> recipeList = response.body();
                    recipes = new ArrayList<>();
                    Log.e("onResponse", "Recipe List size = " + response);

                    for (Recipe recipe : recipeList) {
                        Recipe eachRecipe = new Recipe();
                        eachRecipe.setName(recipe.getName());
                        eachRecipe.setId(recipe.getId());
                        recipes.add(eachRecipe);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
                RecipeRecyclerAdapter recyclerAdapter = new RecipeRecyclerAdapter(recipes);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });

    }
}
