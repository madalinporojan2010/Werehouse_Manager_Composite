package app.bll.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompositeProduct extends MenuItem implements Serializable {
    private List<BaseProduct> compositeProduct;

    private boolean customPrice;

    public CompositeProduct(String title) {
        super(title);
        compositeProduct = new ArrayList<>();
    }

    public CompositeProduct() {
        this("");
    }

    @Override
    public int computePrice() {
        if(!customPrice)
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

    public void setCustomPrice(boolean customPrice) {
        this.customPrice = customPrice;
    }

    public boolean isCustomPrice() {
        return customPrice;
    }

    public List<BaseProduct> getCompositeProduct() {
        return compositeProduct;
    }

    @Override
    public String toString() {
        String string = "<html><font face=\"sansserif\" color=\"black\">" + super.toString() + "<br>";
        for(BaseProduct baseProduct : compositeProduct) {
            string += baseProduct.getTitle() + ",<br>";
        }
        return string + "</font></html>";
    }
}
