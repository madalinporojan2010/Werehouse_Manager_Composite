package app.bll.model;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title);
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
    }

    @Override
    public int computePrice() {
        return price;
    }

    @Override
    public double computeRating() {
        return rating;
    }

    @Override
    public int computeCalories() {
        return calories;
    }

    @Override
    public int computeProtein() {
        return protein;
    }

    @Override
    public int computeFat() {
        return fat;
    }

    @Override
    public int computeSodium() {
        return sodium;
    }
}
