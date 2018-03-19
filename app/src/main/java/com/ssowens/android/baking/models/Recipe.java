package com.ssowens.android.baking.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class Recipe {

    private int id;
    private String name;

    @SerializedName("ingredients")
    public List<Ingredient> ingredients;

    @SerializedName("steps")
    public List<Step> steps;

    private int servings;
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
