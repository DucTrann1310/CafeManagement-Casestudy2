package views;

import models.*;
import service.IUserService;
import service.UserService;
import utils.AuthUtils;
import utils.GetValue;
import utils.UsernameUtils;

import java.sql.SQLOutput;
import java.util.Scanner;

public class LoginView {
    private Scanner scanner = new Scanner(System.in);
    private IUserService iUserService;
    public LoginView(){
        iUserService = new UserService();

    }

    public void launcher(){
        AuthUtils.setUserAuthenticator(null);
        System.out.println("---------------------------------------------");
        System.out.println("-             Tùy chọn:                     -");
        System.out.println("-             Nhập 1. Đăng nhập             -");
        System.out.println("-             Nhập 2. Đăng ký               -");
        System.out.println("-             Nhập 3. Order                 -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,3);
        switch (actionMenu){
            case 1:
                loginView();
                break;
            case 2:
                register();
                launcher();
                break;
            case 3:
                order();
                launcher();
                break;
        }
    }

    private void order() {
        OrderView orderView = new OrderView();
        orderView.createOrder();
    }

    private void register() {
        UserView userView = new UserView();
        userView.addUserByUser();
    }

    private void loginView() {
        System.out.println("Nhập username: ");
        String strUsername = scanner.nextLine();
        System.out.println("Nhập password: ");
        String strPassword = scanner.nextLine();

        if(iUserService.checkUsernamePassword(strUsername, strPassword)){
            User user = iUserService.findUserByUsername(strUsername);
            AuthUtils.setUserAuthenticator(user);

            if(user.getRole().equals(ERole.ADMIN)){
                adminView();
            }else if(user.getRole().equals(ERole.USER)){
                userView();
            }
        }else if(!iUserService.checkUsernamePassword(strUsername, strPassword)){
            System.out.println("Username/Password không đúng. Vui lòng nhập lại");
            loginView();
        }
    }

    public void userView() {
        System.out.println("---------------------------------------------");
        System.out.println("-      Tùy chọn:                            -");
        System.out.println("-      Nhập 1. Quản lý thông tin cá nhân    -");
        System.out.println("-      Nhập 2. Order                        -");
        System.out.println("-      Nhập 3. Đánh giá sản phẩm            -");
        System.out.println("-      Nhập 4. Đăng xuất                    -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,4);
        switch (actionMenu){
            case 1:
                UserView userView = new UserView();
                userView.launcher();
                break;
            case 4:
                launcher();
                break;
            case 2:
                OrderView orderView = new OrderView();
                orderView.launcher();
                break;
            case 3:
                RateProductView rateProductView = new RateProductView();
                rateProductView.launcher();

        }
    }

    public void adminView() {
        System.out.println("---------------------------------------------");
        System.out.println("-            Tùy chọn:                      -");
        System.out.println("-            Nhập 1. Quản lý User           -");
        System.out.println("-            Nhập 2. Quản lý Product        -");
        System.out.println("-            Nhập 3. Quản lý Order          -");
        System.out.println("-            Nhập 4. Xem doanh thu          -");
        System.out.println("-            Nhập 5. Đăng xuất              -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1,5);
        switch (actionMenu){
            case 1:
                UserView userView = new UserView();
                userView.launcher();
                break;
            case 2:
                ProductView productView = new ProductView();
                productView.launcher();
                break;
            case 3:
                OrderView orderView = new OrderView();
                orderView.launcher();
                break;
            case 5:
                launcher();
                break;
            case 4:
                RevenueView revenueView = new RevenueView();
                revenueView.launcher();
                break;
        }
    }

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        loginView.launcher();
    }
}
