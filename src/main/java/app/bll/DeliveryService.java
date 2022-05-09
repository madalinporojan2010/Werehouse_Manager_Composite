package app.bll;

import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.bll.model.MenuItem;
import app.dao.ProductsDataSerializator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    private final ProductsDataSerializator productsDataSerializator;

    public DeliveryService(ProductsDataSerializator productsDataSerializator) {
        this.productsDataSerializator = productsDataSerializator;
    }

    @Override
    public void importBaseProductsDataFromCSV(File importFile) {
        try {
            FileReader inputFile = new FileReader(importFile);
            CSVReader csvReader = new CSVReader(inputFile);
            csvReader.readNext();
            List<BaseProduct> baseProducts = new ArrayList<>();

            csvReader.readAll().stream().forEach(baseProductData -> baseProducts.add(new BaseProduct(baseProductData[0].stripLeading().stripTrailing(), Double.parseDouble(baseProductData[1]), Integer.parseInt(baseProductData[2]), Integer.parseInt(baseProductData[3]),
                    Integer.parseInt(baseProductData[4]), Integer.parseInt(baseProductData[5]), Integer.parseInt(baseProductData[6]))));
            productsDataSerializator.getProductsData().clear();
            baseProducts.stream().forEach(baseProduct -> productsDataSerializator.getProductsData().put(baseProduct.getTitle().toLowerCase().stripLeading().stripTrailing(), baseProduct));
            productsDataSerializator.serialize();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addProduct(MenuItem baseProduct) {
        if (!productsDataSerializator.getProductsData().containsKey(baseProduct.getTitle().toLowerCase())) {
            productsDataSerializator.getProductsData().put(baseProduct.getTitle().toLowerCase(), baseProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProduct(String baseProduct) {
        if (productsDataSerializator.getProductsData().containsKey(baseProduct.toLowerCase().stripTrailing().stripLeading())) {
            productsDataSerializator.getProductsData().remove(baseProduct.toLowerCase());
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyProduct(MenuItem baseProduct) {
        if (removeProduct(baseProduct.getTitle().toLowerCase())) {
            addProduct(baseProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }

    @Override
    public boolean addCompositeProduct(CompositeProduct compositeProduct, int customPrice) {
        if (!productsDataSerializator.getProductsData().containsKey(compositeProduct.getTitle().toLowerCase())) {
            compositeProduct.setPrice(compositeProduct.computePrice());
            if (customPrice != -1) {
                compositeProduct.setPrice(customPrice);
            }

            productsDataSerializator.getProductsData().put(compositeProduct.getTitle().toLowerCase(), compositeProduct);
            productsDataSerializator.serialize();
            return true;
        }
        return false;
    }
}
