package com.ssowens.android.baking.models;

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
        //  return new DecimalFormat("0.####").format(Double.parseDouble(measure));
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


}
