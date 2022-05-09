package app.bll.model;


import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    protected String title;

    public MenuItem(String title) {
        this.title = title;
    }

    public abstract int computePrice();
    public abstract double computeRating();
    public abstract int computeCalories();
    public abstract int computeProtein();
    public abstract int computeFat();
    public abstract int computeSodium();

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title + " made of: ";
    }

}
