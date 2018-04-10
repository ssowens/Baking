package com.ssowens.android.baking.models;

import java.text.DecimalFormat;

/**
 * Created by Sheila Owens on 3/18/18.
 */

public class Ingredient {

    private int id;

    private double quantity;

    private String measure;

    private String ingredient;

    public int getId() {
        return id;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        String value =  new DecimalFormat("0.##").format(quantity);
        return value;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
