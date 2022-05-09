package app.bll;

import app.bll.model.BaseProduct;
import app.bll.model.CompositeProduct;
import app.bll.model.MenuItem;
import app.dao.LoginInfo;
import app.dao.ProductsDataSerializator;

import java.io.File;

public interface IDeliveryServiceProcessing {
    void importBaseProductsDataFromCSV(File importFile);

    boolean addProduct(MenuItem baseProduct);
    boolean removeProduct(String baseProduct);
    boolean modifyProduct(MenuItem baseProduct);
    boolean addCompositeProduct(CompositeProduct compositeProduct, int customPrice);

}
