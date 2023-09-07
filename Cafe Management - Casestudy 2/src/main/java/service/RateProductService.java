package service;

import models.Products;
import models.RateProduct;
import models.User;
import utils.FileUtils;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class RateProductService implements IRateProductService{
    private final String fileRateProduct = "./Data/rate_product.txt";

    @Override
    public List<RateProduct> getAll() {
        return FileUtils.readData(fileRateProduct,RateProduct.class);
    }

    @Override
    public void createRateProduct(RateProduct rateProduct) {
        List<RateProduct> rateProducts = getAll();
        rateProducts.add(rateProduct);
        FileUtils.writeData(fileRateProduct,rateProducts);

    }

    @Override
    public void updateRateProduct(RateProduct rateProduct) {
        List<RateProduct> rateProducts = getAll();
        rateProducts.stream()
                .filter(rp -> rp.getIdProduct() == rateProduct.getIdProduct() && rp.getUsername().equals(rateProduct.getUsername()))
                .forEach(rp -> rp.setRate(rateProduct.getRate()));
        FileUtils.writeData(fileRateProduct,rateProducts);
    }

    @Override
    public void updateRateProductUsername(String strUsername) {
        List<RateProduct> rateProducts = getAll();
        rateProducts.stream()
                .filter(rp ->rp.getUsername().equals(strUsername))
                .forEach(rp -> rp.setUsername(null));
        FileUtils.writeData(fileRateProduct,rateProducts);
    }

    @Override
    public double rateAverage(int idProduct) {
        int total = 0;
        int count = 0;
        List<RateProduct> rateProducts = getAll();
        List<RateProduct> rateProduct = rateProducts.stream()
                                            .filter(rp -> rp.getIdProduct() == idProduct)
                                            .collect(Collectors.toList());
        for(RateProduct rp : rateProduct){
            total += rp.getRate();
            count++;
        }
        if(count == 0){
            return 0.0;
        }
        double average = (double) total/count;
        return formatDecimal(average);
//        return (double) total/count;
    }
    private double formatDecimal(double number){
        double roundedNumber = Math.round(number * 10) / 10.0;
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(roundedNumber));
    }
}
