package views;

import models.Products;
import models.RateProduct;
import models.User;
import service.*;
import utils.AuthUtils;
import utils.GetValue;

import java.util.List;
import java.util.Scanner;

public class RateProductView {
    private Scanner scanner = new Scanner(System.in);
    private IRateProductService iRateProductService;
    private IUserService iUserService;
    private IProductsService iProductsService;
    public RateProductView(){
        iRateProductService = new RateProductService();
        iUserService = new UserService();
        iProductsService = new ProductsService();
    }
    LoginView loginView = new LoginView();
    public void launcher(){
        System.out.println("----------------------------------------------");
        System.out.println("-  Tùy chọn:                                 -");
        System.out.println("-  Nhập 1: Xem danh sách đánh giá sản phẩm   -");
        System.out.println("-  Nhập 2: Đánh giá sản phẩm                 -");
        System.out.println("-  Nhập 3: Quay lại                          -");
        System.out.println("----------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,3);
        switch (actionMenu){
            case 3:
                loginView.userView();
                break;
            case 1:
                showRateProduct();
                launcher();
                break;
            case 2:
                rateProduct();
                break;
        }
    }

    private void showRateProduct() {
//        1,1,username1,4 id, idProduct, username, rating
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("- %5s | %25s | %20s | %5s -\n",
                "ID", "Name", "Username", "Rating");
        List<RateProduct> rateProducts = iRateProductService.getAll();
        for(RateProduct rp : rateProducts){
            System.out.printf("- %5s | %25s | %20s | %5s -\n",
                    rp.getId(), returnProductName(rp.getIdProduct()), rp.getUsername() , rp.getRate());
        }
        System.out.println("--------------------------------------------------------------------");
    }
    private String returnProductName(int idProduct){
        List<Products> products = iProductsService.getAll();
        return products.stream()
                .filter(p -> p.getId() == idProduct)
                .findFirst().orElse(null)
                .getName();
    }
    private void rateProduct(){
        User user = AuthUtils.getUserAuthenticator();
        List<Products> products = iProductsService.getAll();

        showProduct();

        System.out.println("Nhập id sản phẩm muốn đánh giá: ");
        int idProduct = GetValue.getIntOfWithBounds(1, products.size());
        if(checkRateProduct(idProduct, user.getUsername())) {
            System.out.println("----------------------------------------------");
            System.out.println("-        Bạn đã đánh giá sản phẩm này        -");
            System.out.println("-        Bạn có muốn đánh giá lại không?     -");
            System.out.println("-        Nhập 1: Có                          -");
            System.out.println("-        Nhập 2: Không                       -");
            System.out.println("----------------------------------------------");
            int action = GetValue.getIntOfWithBounds(1, 2);
            switch (action) {
                case 2:
                    launcher();
                    break;
                case 1:
                    rateAgain(idProduct, user.getUsername());
                    launcher();
                    break;
            }
        }else {
            rateNew(idProduct, user.getUsername());
            launcher();
        }

    }

    private void rateNew(int idProduct, String username) {
        System.out.println("Nhập rate:");
        int action = GetValue.getIntOfWithBounds(1,5);
        RateProduct rateProduct = new RateProduct(System.currentTimeMillis() % 100000,idProduct,username,action);
        iRateProductService.createRateProduct(rateProduct);
        System.out.println("Đánh giá sản phẩm thành công");
    }

    private void rateAgain(int idProduct, String username) {
        System.out.println("Nhập rate:");
        int action = GetValue.getIntOfWithBounds(1,5);
        RateProduct rateProduct = new RateProduct(System.currentTimeMillis() % 100000,idProduct,username,action);
        iRateProductService.updateRateProduct(rateProduct);
        System.out.println("Cập nhật đánh giá thành công");
    }

    private boolean checkRateProduct(int idProduct, String username){
        List<RateProduct> rateProducts = iRateProductService.getAll();

        boolean check = false;
        for(int i = 0; i < rateProducts.size(); i++){
            if(rateProducts.get(i).getIdProduct() == idProduct
            && rateProducts.get(i).getUsername().equals(username)){
                check = true;
            }
        }
        return check;
    }
    private void showProduct(){
        ProductView productView = new ProductView();
        productView.showProduct();
    }
}
