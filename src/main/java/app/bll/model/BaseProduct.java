package app.bll.model;

import java.io.Serializable;

public class BaseProduct extends MenuItem implements Serializable {

    public BaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        super(title, rating, calories, protein, fat, sodium, price);
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
