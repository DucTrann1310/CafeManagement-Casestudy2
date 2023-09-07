package service;

import models.RateProduct;
import models.User;

import java.util.List;

public interface IRateProductService {
    List<RateProduct> getAll();
    void createRateProduct(RateProduct rateProduct);
    void updateRateProduct(RateProduct rateProduct);
    void updateRateProductUsername(String strUsername);
    double rateAverage(int idProduct);

}
