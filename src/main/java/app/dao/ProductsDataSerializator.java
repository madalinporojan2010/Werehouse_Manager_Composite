package app.dao;

import app.bll.model.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class ProductsDataSerializator extends Serializator {
    private Map<String, MenuItem> productsData;

    public ProductsDataSerializator() {
        productsData = new HashMap<>();
    }

    public void serialize() {
        super.serialize(productsData, "productsData.ser");
    }

    public Map<String, MenuItem> deserialize() {
        if (super.deserialize("productsData.ser") != null) {
            productsData = (Map<String, MenuItem>) super.deserialize("productsData.ser");
            return (Map<String, MenuItem>) super.deserialize("productsData.ser");
        }
        return null;
    }

    public Map<String, MenuItem> getProductsData() {
        return productsData;
    }
}
