package views;

import models.OrderItem;
import models.Orders;
import service.IOrderService;
import service.OrderService;
import utils.DateUtils;
import utils.GetValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RevenueView {
    private IOrderService iOrderService;
    private Scanner scanner = new Scanner(System.in);
    LoginView loginView = new LoginView();
    OrderView orderView = new OrderView();
    public RevenueView(){
        this.iOrderService = new OrderService();
    }
    public void launcher(){
        System.out.println("---------------------------------------------");
        System.out.println("-             Xem doanh thu:                -");
        System.out.println("-             Nhập 1. Theo ngày             -");
        System.out.println("-             Nhập 2. Theo tháng            -");
        System.out.println("-             Nhập 3. Theo năm              -");
        System.out.println("-             Nhập 4. Quay lại              -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1, 4);
        switch (actionMenu){
            case 4:
                loginView.adminView();
                break;
            case 1:
                calculateRevenueByDay();
                launcher();
                break;
            case 2:
                calculateRevenueByMonth();
                launcher();
                break;
            case 3:
                calculateRevenueByYear();
                launcher();
                break;
        }
    }
    private void calculateRevenueByYear() {
        Year year = DateUtils.getValidYear();

        LocalDate startOfYear = year.atDay(1);
        LocalDate endOfYear = year.atDay(year.length());

        List<Orders> orders = iOrderService.getOrderBetWeenYear(startOfYear,endOfYear);
        showRevenueByYear(year,calculateTotalRevenue(orders));
    }

    private void calculateRevenueByMonth() {
        YearMonth month = DateUtils.getValidMonth();

        LocalDate startOfMonth = month.atDay(1);
        LocalDate endOfMonth = month.atEndOfMonth();

        List<Orders> orders = iOrderService.getOrderBetWeenMonth(startOfMonth,endOfMonth);
        showRevenueByMonth(month, calculateTotalRevenue(orders));
    }

    private void calculateRevenueByDay() {
        LocalDate date = DateUtils.getValidDate();

        List<Orders> orders = iOrderService.getOrdersBetweenDay(date);
        if(orders.size() != 0){
            showRevenueByDate(date, calculateTotalRevenue(orders));

            System.out.println("---------------------------------------------");
            System.out.println("-          Bạn có muốn xem chi tiết:        -");
            System.out.println("-          Nhập 1. Có                       -");
            System.out.println("-          Nhập 2. Không                    -");
            System.out.println("---------------------------------------------");
            int actionMenu = GetValue.getIntOfWithBounds(1, 2);
            switch (actionMenu){
                case 2:
                    launcher();
                    break;
                case 1:
                    showDetailRevenueByDate(date);
                    break;
            }
        }else if(orders.size() == 0){
            System.out.println("Không có doanh thu của ngày vừa nhập");
        }

    }

    private void showDetailRevenueByDate(LocalDate localDate) {
        List<Orders> orders = iOrderService.findListOrderByCreateAt(localDate);
        orderView.displayOrder(orders);
    }

    private Long calculateTotalRevenue(List<Orders> orders) {
        Long totalRevenue = 0L;
        for(Orders o : orders){
            totalRevenue += o.getTotal();
        }
        return totalRevenue;
    }
    private void showRevenueByDate(LocalDate date, long revenue){
        System.out.println("------------------------------------------");
        System.out.printf("- %-20s | %-15s -\n", "CreateAt","Total");
        System.out.printf("- %-20s | %-15s -\n", date, revenue);
        System.out.println("------------------------------------------");
    }
    private void showRevenueByMonth(YearMonth month, long revenue){
        System.out.println("------------------------------------------");
        System.out.printf("- %-20s | %-15s -\n", "CreateAt","Total");
        System.out.printf("- %-20s | %-15s -\n", month, revenue);
        System.out.println("------------------------------------------");
    }
    private void showRevenueByYear(Year year, long revenue){
        System.out.println("------------------------------------------");
        System.out.printf("- %-20s | %-15s -\n", "CreateAt","Total");
        System.out.printf("- %-20s | %-15s -\n", year, revenue);
        System.out.println("------------------------------------------");
    }
}
