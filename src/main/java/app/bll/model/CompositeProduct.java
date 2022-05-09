package app.bll.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem implements Serializable {
    private List<BaseProduct> compositeProduct;
    private double rating;
    private int calories;
    private int protein;
    private int fat;
    private int sodium;
    private int price;

    public CompositeProduct(String title) {
        super(title);
        compositeProduct = new ArrayList<>();
    }

    @Override
    public int computePrice() {
        price = compositeProduct.stream().mapToInt(BaseProduct::computePrice).sum();
        return price;
    }

    @Override
    public double computeRating() {
        rating = compositeProduct.stream().mapToDouble(BaseProduct::computeRating).sum();
        rating /= compositeProduct.size();
        return rating;
    }

    @Override
    public int computeCalories() {
        calories = compositeProduct.stream().mapToInt(BaseProduct::computeCalories).sum();
        return calories;
    }

    @Override
    public int computeProtein() {
        protein = compositeProduct.stream().mapToInt(BaseProduct::computeProtein).sum();
        return protein;
    }

    @Override
    public int computeFat() {
        fat = compositeProduct.stream().mapToInt(BaseProduct::computeFat).sum();
        return fat;
    }

    @Override
    public int computeSodium() {
        sodium = compositeProduct.stream().mapToInt(BaseProduct::computeSodium).sum();
        return sodium;
    }

    public void addBaseProduct(BaseProduct baseProduct) {
        compositeProduct.add(baseProduct);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<BaseProduct> getCompositeProduct() {
        return compositeProduct;
    }

    @Override
    public String toString() {
        String string = super.toString();
        for(BaseProduct baseProduct : compositeProduct) {
            string += baseProduct.getTitle() + ", ";
        }
        return string.substring(0, string.length() - 2) + ".";
    }
}
