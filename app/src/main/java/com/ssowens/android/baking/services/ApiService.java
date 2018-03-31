package com.ssowens.android.baking.services;

import com.ssowens.android.baking.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public interface ApiService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipeDetails();
}
