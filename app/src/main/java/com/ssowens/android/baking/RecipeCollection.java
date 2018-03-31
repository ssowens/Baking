package com.ssowens.android.baking;

import android.content.Context;

import com.ssowens.android.baking.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheila Owens on 3/24/18.
 */

public class RecipeCollection {
    private static RecipeCollection sRecipeCollection;
    private List<Recipe> recipes;

    public static RecipeCollection get(Context context) {
        if (sRecipeCollection == null) {
            sRecipeCollection = new RecipeCollection(context);
        }
        return sRecipeCollection;
    }

    private RecipeCollection(Context context) {
        recipes = new ArrayList<>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipeCollection(Recipe recipe) {
        recipes.add(recipe);
    }

    public void addListRecipeCollection(List<Recipe> recipeList) {
        recipes.addAll(recipeList);
    }

    public Recipe getRecipe(int id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }

}
