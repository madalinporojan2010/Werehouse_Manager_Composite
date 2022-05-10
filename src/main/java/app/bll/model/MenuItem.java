package app.bll.model;


import java.awt.*;
import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    protected String title;
    protected double rating;
    protected int calories;
    protected int protein;
    protected int fat;
    protected int sodium;
    protected int price;

    public MenuItem(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.price = price;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
    }

    public MenuItem(String title) {
        this.title = title;
    }

    public abstract int computePrice();

    public abstract double computeRating();

    public abstract int computeCalories();

    public abstract int computeProtein();

    public abstract int computeFat();

    public abstract int computeSodium();

    public double getRating() {
        return rating;
    }

    public int getCalories() {
        return calories;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + " MADE FROM: ";
    }

}
