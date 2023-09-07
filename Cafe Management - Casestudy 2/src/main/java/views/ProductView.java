package views;

import models.*;
import service.IProductsService;
import service.IRateProductService;
import service.ProductsService;
import service.RateProductService;
import utils.GetValue;
import utils.ValidateUtils;

import java.util.*;

public class ProductView {
    private Scanner scanner = new Scanner(System.in);
    private IProductsService iProductsService;
    private IRateProductService irateProductService;
    public ProductView(){
        iProductsService = new ProductsService();
        irateProductService = new RateProductService();
    }
    LoginView loginView = new LoginView();
    public void launcher(){
        System.out.println("---------------------------------------------");
        System.out.println("-       Menu quản lý product:               -");
        System.out.println("-       Nhập 1. Xem danh sách product       -");
        System.out.println("-       Nhập 2. Thêm product                -");
        System.out.println("-       Nhập 3. Sửa product                 -");
//        System.out.println("-       Nhập 4. Xóa product theo ID         -");
        System.out.println("-       Nhập 4: Sắp xếp product             -");
        System.out.println("-       Nhập 5: Tìm kiếm product            -");
        System.out.println("-       Nhập 6: Quay lại                    -");
        System.out.println("-       Chọn:                               -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,6);
        switch (actionMenu){
            case 1:
                showProduct();
                launcher();
                break;
            case 2:
                addProduct();
                launcher();
                break;
            case 3:
                upadteProduct();
                launcher();
                break;
//            case 4:
//                deleteProduct();
//                launcher();
//                break;
            case 4:
                sortProduct();
                launcher();
                break;
            case 5:
                findProduct();
                break;
            case 6:
                loginView.adminView();
                break;
        }
    }

    private void findProduct() {
        System.out.println("---------------------------------------------");
        System.out.println("-           Menu tìm kếm product:           -");
        System.out.println("-           Nhập 1. Theo id                 -");
        System.out.println("-           Nhập 2. Theo tên                -");
        System.out.println("-           Nhập 3. Theo giá                -");
        System.out.println("-           Nhập 4. Theo loại               -");
        System.out.println("-           Nhập 5. Quay lại                -");
        System.out.println("-           Chọn:                           -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,5);
        switch (actionMenu){
            case 1:
                findProductById();
                launcher();
                break;
            case 2:
                findProductByName();
                launcher();
                break;
            case 3:
                findProductByPrice();
                launcher();
                break;
            case 4:
                findProductByCategory();
                break;
            case 5:
                launcher();
                break;
        }
    }

    private void findProductByCategory() {
        System.out.println("Nhập loại: ");
        for(ECategory eCategory : ECategory.values()){
            System.out.println(eCategory.getName() + ": " + eCategory.getId());
        }
        int idCategory = GetValue.getIntOfWithBounds(1,3);
        ECategory category = ECategory.findById(idCategory);

        List<Products> foundProduct = iProductsService.findProductByCategory(category);

        if(foundProduct.isEmpty()){
            System.out.printf("Không tìm thấy product có loại %s\n",category);
            launcher();
        }
        else{
            System.out.printf("Danh sách product có loại %s là: \n",category);
            displayProduct(foundProduct);
            launcher();
        }
    }


    private void findProductByPrice() {
        String strPrice = checkInputValid(ValidateUtils.FIELD_PRICE,ValidateUtils.FIELD_PRICE_MESSAGE,ValidateUtils.REGEX_PRICE);
        int price = Integer.parseInt(strPrice);
        List<Products> foundProduct = iProductsService.findProductByPrice(price);

        if(foundProduct.isEmpty()){
            System.out.printf("Không tìm thấy product có giá %s\n",price);
        }
        else{
            System.out.printf("Danh sách product có giá %s là: \n",price);
            displayProduct(foundProduct);
        }
    }


    private void findProductByName() {
        String  strName = checkInputValid(ValidateUtils.FIELD_NAME, ValidateUtils.FIELD_NAME_MASSAGE, ValidateUtils.REGEX_NAME);
        Products foundProduct = iProductsService.findProductByName(strName);

        if(foundProduct == null){
            System.out.printf("Không tìm thấy product có tên %s\n",strName);
        }
        else{
            System.out.printf("Thông tin product có tên %s là: \n",strName);
            List<Products> products1 = new ArrayList<>();
            products1.add(foundProduct);

            displayProduct(products1);
        }
    }


    private void findProductById() {
        List<Products> products = iProductsService.getAll();
        int idFind = GetValue.getIntOfWithBounds("Nhập id product muốn tìm kiếm: ",1,products.size());

        Products foundProduct = iProductsService.findProductById(idFind);

        if (foundProduct == null) {
            System.out.printf("Không tìm thấy product có id %s\n", idFind);
        } else {
            System.out.printf("Thông tin product có id %s là: \n", idFind);
            List<Products> products1 = new ArrayList<>();
            products1.add(foundProduct);
            displayProduct(products1);
        }
    }

    private void sortProduct() {
        System.out.println("---------------------------------------------");
        System.out.println("-          Chọn kiểu muốn sắp xếp:          -");
        System.out.println("-          Nhập 1. Tăng dần                 -");
        System.out.println("-          Nhập 2. Giảm dần                 -");
        System.out.println("-          Nhập 3. Quay lại                 -");
        System.out.println("---------------------------------------------");
        Comparator<Products> comparator = null;
        List<Products> products = iProductsService.getAll();

        int actionMenu = GetValue.getIntOfWithBounds(1,3);
        switch (actionMenu){
            case 1:
                products.sort(sortProductAscending(comparator));
                break;
            case 2:
                products.sort(sortProductDescending(comparator));
                break;
            case 3:
                launcher();
                break;
        }
        displayProduct(products);
    }

    private Comparator<Products> sortProductDescending(Comparator<Products> comparator) {
        System.out.println("---------------------------------------------");
        System.out.println("-          Bạn muốn sắp xếp theo:           -");
        System.out.println("-          Nhập 1. Theo tên                 -");
        System.out.println("-          Nhập 2. Theo giá                 -");
        System.out.println("-          Nhập 3. Theo loại                -");
        System.out.println("-          Nhập 4. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,4);
        switch (actionMenu){
            case 1:
                comparator = Comparator.comparing(Products::getName).reversed();
                break;
            case 2:
                comparator = Comparator.comparing(Products::getPrice).reversed();
                break;
            case 3:
                comparator = Comparator.comparing(Products::getCategory).reversed();
                break;
            case 4:
                sortProduct();
                break;
        }
        return comparator;
    }

    private Comparator<Products> sortProductAscending(Comparator<Products> comparator) {
        System.out.println("---------------------------------------------");
        System.out.println("-          Bạn muốn sắp xếp theo:           -");
        System.out.println("-          Nhập 1. Theo tên                 -");
        System.out.println("-          Nhập 2. Theo giá                 -");
        System.out.println("-          Nhập 3. Theo loại                -");
        System.out.println("-          Nhập 4. Quay lại                 -");
        System.out.println("-          Chọn:                            -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,4);
        switch (actionMenu){
            case 1:
                comparator = Comparator.comparing(Products::getName);
                break;
            case 2:
                comparator = Comparator.comparing(Products::getPrice);
                break;
            case 3:
                comparator = Comparator.comparing(Products::getCategory);
                break;
            case 4:
                sortProduct();
                break;
        }
        return comparator;
    }

    private void displayProduct(List<Products> products) {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %15s | %15s | %6s -\n",
                "ID", "Name", "Price", "Category", "Rating");
        for (Products p : products) {
            System.out.printf("- %5s | %25s | %15s | %15s | %6s -\n",
                    p.getId(),p.getName(),p.getPrice(),p.getCategory(),irateProductService.rateAverage(p.getId()));
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

//    private void deleteProduct() {
//        List<Products> products = iProductsService.getAll();
//        showProduct();
//        int idDelete = GetValue.getIntOfWithBounds("Nhập id product muốn xóa: ",0,products.size());
//        iProductsService.deleteProduct(idDelete);
//        System.out.printf("Xóa thành công product có id %s\n", idDelete);
//        showProduct();
//
//    }

    private void upadteProduct() {
        List<Products> products = iProductsService.getAll();
        showProduct();

        int idUpdate = GetValue.getIntOfWithBounds("Nhập id muốn sửa",0, products.size());

        Products product = getProductBasicInfo();

        iProductsService.updateProduct(idUpdate, product);
        System.out.printf("Cập nhật thành công product có id %s\n", idUpdate);

        showProduct();
    }

    private void addProduct() {
        Products product = getProductBasicInfo();
        iProductsService.createProduct(product);
        System.out.println("Thêm product thành công");
        showProduct();
    }

    private Products getProductBasicInfo() {
        String  strName = checkInputValid(ValidateUtils.FIELD_NAME, ValidateUtils.FIELD_NAME_MASSAGE, ValidateUtils.REGEX_NAME);

        String strPrice = checkInputValid(ValidateUtils.FIELD_PRICE,ValidateUtils.FIELD_PRICE_MESSAGE,ValidateUtils.REGEX_PRICE);
        int price = Integer.parseInt(strPrice);

        System.out.println("Nhập loại: ");
        for(ECategory eCategory : ECategory.values()){
            System.out.println(eCategory.getName() + ": " + eCategory.getId());
        }
        int idCategory = GetValue.getIntOfWithBounds(1,3);
        ECategory category = ECategory.findById(idCategory);

        List<Products> products = iProductsService.getAll();
        Products product = new Products(products.size()+1,strName,price,category);

        return product;
    }

    public String  checkInputValid(String fieldName, String  fieldMessage, String fieldPattern){
        String input = null;
        boolean validateInput = false;
        do{
            System.out.printf("Nhập %s: \n", fieldName);
            input = scanner.nextLine();
            if(!ValidateUtils.isValid(fieldPattern,input)){
                System.out.println(fieldMessage);
                validateInput = true;
            }else {
                validateInput = false;
            }
        }while (validateInput);
        return input;
    }

    protected void showProduct() {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %15s | %15s | %6s -\n",
                "ID", "Name", "Price", "Category", "Rating");
        List<Products> products = iProductsService.getAll();
        for (Products p : products) {
            System.out.printf("- %5s | %25s | %15s | %15s | %6s -\n",
                    p.getId(),p.getName(),p.getPrice(),p.getCategory(),irateProductService.rateAverage(p.getId()));
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        ProductView productView = new ProductView();
        productView.launcher();
    }
}
