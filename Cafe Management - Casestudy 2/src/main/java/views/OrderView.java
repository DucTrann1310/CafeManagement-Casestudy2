package views;

import com.sun.org.apache.xpath.internal.operations.Or;
import models.*;
import service.*;
import utils.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OrderView {
    private IOrderService iOrderService;
    private IProductsService iProductsService;
    private IUserService iUserService;
    private Scanner scanner = new Scanner(System.in);
    LoginView loginView = new LoginView();
    public OrderView(){
        iOrderService = new OrderService();
        iProductsService = new ProductsService();
        iUserService = new UserService();
    }
    public void launcher() {
        if(AuthUtils.getUserAuthenticator() != null){
            if (AuthUtils.hasRole(ERole.ADMIN)) {
                displayAdminMenu();

            } else if (AuthUtils.hasRole(ERole.USER)) {
                displayUserMenu();
            }
        }else{
            loginView.launcher();
        }

    }

    private void displayAdminMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("-     Menu quản lý Order:                   -");
        System.out.println("-     Nhập 1. Xem danh sách Order           -");
        System.out.println("-     Nhập 2. Xem chi tiết Order            -");
        System.out.println("-     Nhập 3. Thêm Order                    -");
        System.out.println("-     Nhập 4: Sắp xếp Order                 -");
        System.out.println("-     Nhập 5: Tìm kiếm Order                -");
        System.out.println("-     Nhập 6. Chỉnh sửa trạng thái Order    -");
        System.out.println("-     Nhập 7: Quay lại                      -");
        System.out.println("-     Chọn:                                 -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1, 7);
        switch (actionMenu) {
            case 1:
                showOrder();
                launcher();
                break;
            case 2:
                String strIdOrder = checkInputValid(ValidateUtils.FIELD_IDORDER,ValidateUtils.FIELD_IDORDER_MESSAGE,ValidateUtils.REGEX_IDORDER);
                long idOrder = Long.parseLong(strIdOrder);
                showOrderById(idOrder);
                launcher();
                break;
            case 3:
                createOrder();
                break;
            case 4:
                sortOrder();
                break;
            case 5:
                findOrder();
                break;
            case 7:
                loginView.adminView();
                break;
            case 6:
                setStatusOrder();
                displayAdminMenu();
                break;
        }
    }

    private void setStatusOrder() {
        List<Orders> orderUnfinish = showOrderUnfinish();
        if(orderUnfinish.size() != 0){
            String strIdOrder = checkInputValid(ValidateUtils.FIELD_IDORDER,ValidateUtils.FIELD_IDORDER_MESSAGE,ValidateUtils.REGEX_IDORDER);
            long idOrder = Long.parseLong(strIdOrder);
            for(Orders o : orderUnfinish){
                if(o.getId() == idOrder){
                    iOrderService.updateStatusOrder(idOrder);
                    System.out.println("Cập nhật đơn hàng thành công");
                    showOrderById(idOrder);
                    break;
                }else if(orderUnfinish.size() == 0){
                    System.out.println("Id không hợp lệ");
                    setStatusOrder();
                };
            }
        }else {
            System.out.println("Tất cả đơn hàng đều đã hoàn thành");
        }
    }

    private List<Orders> showOrderUnfinish() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                "ID", "Name", "PhoneNumber","Username","CreateAt","Total","Status");

        List<Orders> orders = iOrderService.getAll();
        List<Orders> orderUnfinish = new ArrayList<>();
        for(Orders order : orders){
            if(order.getEStatus().equals(EStatus.DELIVERING)){
                orderUnfinish.add(order);
                System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                        order.getId(),order.getName(),order.getPhone(), order.getUsername(),
                        order.getCreateAt(), order.getTotal(), order.getEStatus());
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        return orderUnfinish;
    }

    private void displayUserMenu() {
        User user = AuthUtils.getUserAuthenticator();
        System.out.println("---------------------------------------------");
        System.out.println("-       Menu quản lý Order:                 -");
        System.out.println("-       Nhập 1. Xem danh sách đã Order      -");
        System.out.println("-       Nhập 2. Thêm Order                  -");
        System.out.println("-       Nhập 3: Quay lại                    -");
        System.out.println("-       Chọn:                               -");
        System.out.println("---------------------------------------------");
        int actionMenu = GetValue.getIntOfWithBounds(1, 3);
        switch (actionMenu) {
            case 3:
                loginView.userView();
                break;
            case 1:
                showPersonalListOrder(user.getUsername());
                launcher();
                break;
            case 2:
                createOrder();
                break;
        }
    }
    private void showPersonalListOrder(String strUsername) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                "ID", "Name", "PhoneNumber","Username","CreateAt","Total","Status");

        List<Orders> orders = iOrderService.getAll();

        for(Orders order : orders){
            if(order.getUsername().equals(strUsername)){
                System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                        order.getId(),order.getName(),order.getPhone(), order.getUsername(),
                        order.getCreateAt(), order.getTotal(), order.getEStatus());
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private void findOrder() {
        System.out.println("Menu tìm kiếm order:");
        System.out.println("1. Tìm kiếm theo OrderId");
        System.out.println("2. Tìm kiếm theo ngày tạo Order");
        System.out.println("3. Tìm kiếm theo người đặt");
        System.out.println("4. Tìm kiếm theo username");
        System.out.println("5. Tìm kiếm theo số điện thoại");
        System.out.println("6. Quay lại");
        System.out.println("Chọn: ");

        int actionMenu = GetValue.getIntOfWithBounds(1,6);
        switch (actionMenu){
            case 1:
                findListOrderByOrderId();
                break;
            case 2:
                findListOrderByCreateAt();
                break;
            case 3:
                findListOrderByName();
                break;
            case 4:
                findListOrderByUsername();
                break;
            case 5:
                findListOrderByPhone();
                break;
            case 6:
                launcher();
                break;
        }
    }

    private void findListOrderByUsername() {
        String strUsername = checkInputValid(ValidateUtils.FIELD_USERNAME, ValidateUtils.FIELD_USERNAME_MEESAGE, ValidateUtils.REGEX_USERNAME);

        List<Orders> foundOrder = iOrderService.findListOrderByUserName(strUsername);

        if(foundOrder.isEmpty()){
            System.out.printf("Không tìm danh sách order với username:  '%s'\n",strUsername);
            launcher();
        }
        else{
            System.out.printf("Danh sách order với username %s là: \n",strUsername);
            displayOrder(foundOrder);
            launcher();
        }
    }

    private void findListOrderByPhone() {
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE, ValidateUtils.FIELD_PHONE_MASSAGE, ValidateUtils.REGEX_PHONE);

        List<Orders> foundOrder = iOrderService.findListOrderByPhone(phone);

        if(foundOrder.isEmpty()){
            System.out.printf("Không tìm danh sách order với số điện thoại:  '%s'\n",phone);
            launcher();
        }
        else{
            System.out.printf("Danh sách order với số điện thoại %s là: \n",phone);
            displayOrder(foundOrder);
            launcher();
        }
    }

    private void findListOrderByName() {
        String  strName = checkInputValid(ValidateUtils.FIELD_NAME, ValidateUtils.FIELD_NAME_MASSAGE, ValidateUtils.REGEX_NAME);

        List<Orders> foundOrder = iOrderService.findListOrderByName(strName);

        if(foundOrder.isEmpty()){
            System.out.printf("Không tìm danh sách order với user:  '%s'\n",strName);
            launcher();
        }
        else{
            System.out.printf("Danh sách order với user %s là: \n",strName);
            displayOrder(foundOrder);
            launcher();
        }
    }

    private void findListOrderByCreateAt() {
        System.out.println("Nhập ngày cần tìm (dd-MM-yyyy): ");
        LocalDate localDate = DateUtils.getValidDate();

        List<Orders> foundOrder = iOrderService.findListOrderByCreateAt(localDate);

        if(foundOrder.isEmpty()){
            System.out.printf("Không tìm danh sách order với ngày tạo '%s'\n",localDate);
            launcher();
        }
        else{
            System.out.printf("Danh sách order với ngày tạo %s là: \n",localDate);
            displayOrder(foundOrder);
            launcher();
        }

    }

    private void findListOrderByOrderId() {
        String strIdOrder = checkInputValid(ValidateUtils.FIELD_IDORDER,ValidateUtils.FIELD_IDORDER_MESSAGE,ValidateUtils.REGEX_IDORDER);
        long idOrder = Long.parseLong(strIdOrder);

        List<Orders> foundOrder = iOrderService.findListOrderByOrderId(idOrder);

        if(foundOrder.isEmpty()){
            System.out.printf("Không tìm danh sách order với Id Order '%s'\n",idOrder);
            launcher();
        }
        else{
            System.out.printf("Danh sách order có Id Order %s là: \n",idOrder);
            displayOrder(foundOrder);
            launcher();
        }
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
    public void displayOrder(List<Orders> orders) {
        //2,23-08-2023,100000,Lê Công Vinh,0914028472
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %8s | %30s | %15s | %20s | %20s | %15s | %15s -\n",
                "ID", "Name", "Username", "PhoneNumber","CreateAt","Total", "Status");
        for (Orders order : orders) {
            System.out.printf("- %8s | %30s | %15s | %20s | %20s | %15s | %15s -\n",
                    order.getId(), order.getName(),order.getUsername(),order.getPhone(),
                    order.getCreateAt(), order.getTotal(),order.getEStatus());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    private void sortOrder() {
        System.out.println("---------------------------------------------");
        System.out.println("-          Chọn kiểu muốn sắp xếp:          -");
        System.out.println("-          Nhập 1. Tăng dần                 -");
        System.out.println("-          Nhập 2. Giảm dần                 -");
        System.out.println("-          Nhập 3. Quay lại                 -");
        System.out.println("---------------------------------------------");
        List<Orders> orders = iOrderService.getAll();
        Comparator<Orders> comparator = null;

        int actionMenu = GetValue.getIntOfWithBounds(1,3);
        switch (actionMenu){
            case 1:
                orders.sort(softOrderAscending(comparator));
                break;
            case 2:
                orders.sort(softOrderDescending(comparator));
                break;
            case 3:
                launcher();
                break;
        }
        displayOrder(orders);
        launcher();
    }

    private Comparator<Orders> softOrderDescending(Comparator<Orders> comparator) {
        return softOrderComparator(comparator).reversed();
    }

    private Comparator<Orders> softOrderAscending(Comparator<Orders> comparator) {
        return softOrderComparator(comparator);
    }

    private Comparator<Orders> softOrderComparator(Comparator<Orders> comparator) {
        System.out.println("Bạn muốn sắp xếp theo:");
        System.out.println("Nhập 1. ID Order");
        System.out.println("Nhập 2. Ngày Order");
        System.out.println("Nhập 3. Tổng tiền");
        System.out.println("Nhập 4. Người order");
        System.out.println("Nhập 5. Username");
        System.out.println("Nhập 6. Số điện thoại");
        System.out.println("Nhập 7. Quay lại");
        System.out.println("Chọn: ");

        int actionMenu = GetValue.getIntOfWithBounds(1, 7);

        switch (actionMenu) {
            case 1:
                comparator = Comparator.comparing(Orders::getId);
                break;
            case 2:
                comparator = Comparator.comparing(Orders::getCreateAt);
                break;
            case 3:
                comparator = Comparator.comparing(Orders::getTotal);
                break;
            case 4:
                comparator = Comparator.comparing(Orders::getName);
                break;
            case 5:
                comparator = Comparator.comparing(Orders::getUsername);
                break;
            case 6:
                comparator = Comparator.comparing(Orders::getPhone);
                break;
            case 7:
                sortOrder();
                break;
        }
        return comparator;
    }
    public void createOrder() {
        Orders orders = new Orders();
        List<Products> products = iProductsService.getAll();
        orders.setId(System.currentTimeMillis() % 100000);

        boolean checkContinueOrderItem = false;
        do {
            ProductView productView = new ProductView();
            productView.showProduct();
            System.out.println("Nhập id sản phẩm: ");
            int idProduct = GetValue.getIntOfWithBounds(1,products.size());

            Products p = iProductsService.findProductById(idProduct);

            System.out.println("Nhập số lượng: ");
            int quantity = GetValue.getIntOfWithBounds(1);
//            int quantity = Integer.parseInt(scanner.nextLine());

            processOrderItem(orders, idProduct, quantity, p);
            System.out.println("---------------------------------------------");
            System.out.println("-   Bạn có muốn thêm tiếp sản phẩm không?   -");
            System.out.println("-   Nhập 1: Tiếp tục                        -");
            System.out.println("-   Nhập 2: Không                           -");
            System.out.println("---------------------------------------------");
            int actionContinueOrderItem = GetValue.getIntOfWithBounds(1,2);
            switch (actionContinueOrderItem){
                case 1:
                    checkContinueOrderItem = true;
                    break;
                case 2:
                    processPayment(orders);
                    break;
            }
        }while (checkContinueOrderItem);
    }

    private void processPayment(Orders orders) {
        if(AuthUtils.getUserAuthenticator() != null){
            if (AuthUtils.hasRole(ERole.ADMIN)) {
                System.out.println("---------------------------------------------");
                System.out.println("-          Thanh toán cho:                  -");
                System.out.println("-          Nhập 1: Thành viên               -");
                System.out.println("-          Nhập 2: Không thành viên         -");
                System.out.println("-          Nhập 3: Hủy order                -");
                System.out.println("---------------------------------------------");
                int actionPay = GetValue.getIntOfWithBounds(1, 3);
                switch (actionPay) {
                    case 1:
                        handleMemberPayment(orders);
                        break;
                    case 2:
                        handleNonMemberPayment(orders);
                        break;
                    case 3:
                        System.out.println("Hủy thành công");
                        launcher();
                        break;
                }
            }else if(AuthUtils.hasRole(ERole.USER)){
                handleUserPayment(orders);
            }
        }else if(AuthUtils.getUserAuthenticator() == null){
            handleNonMemberPayment(orders);
        }
    }

    private void handleUserPayment(Orders orders) {
        User user = AuthUtils.getUserAuthenticator();
        System.out.println("---------------------------------------------");
        System.out.println("-            Nhập 1. Thanh toán             -");
        System.out.println("-            Nhập 2. Hủy order              -");
        System.out.println("---------------------------------------------");
        int actionPay = GetValue.getIntOfWithBounds(1, 2);
        switch (actionPay) {
            case 1:
                orders.setName(user.getName());
                orders.setUsername(user.getUsername());
                orders.setPhone(user.getPhone());
                orders.setTotalPrice();
                orders.setEStatus(EStatus.DELIVERING);
                orders.setCreateAt(LocalDate.now());
                iOrderService.createOrder(orders);

                paymentConfirm(orders);
                break;
            case 2:
                System.out.println("Hủy thành công");
                displayUserMenu();
                break;
        }
    }

    private void handleNonMemberPayment(Orders orders) {
        String strFullName = checkInputValid(ValidateUtils.FIELD_NAME,ValidateUtils.FIELD_NAME_MASSAGE,ValidateUtils.REGEX_NAME);
        String phone = checkInputValid(ValidateUtils.FIELD_PHONE,ValidateUtils.FIELD_PHONE_MASSAGE,ValidateUtils.REGEX_PHONE);

        orders.setPhone(phone);
        orders.setName(strFullName);
        orders.setTotalPrice();
        if(AuthUtils.getUserAuthenticator() != null){
            orders.setEStatus(EStatus.DONE);
        }else if(AuthUtils.getUserAuthenticator() == null){
            orders.setEStatus(EStatus.DELIVERING);
        }

        orders.setCreateAt(LocalDate.now());

        iOrderService.createOrder(orders);

        paymentConfirm(orders);
    }

    private void cancelOrder(Orders order) {
        System.out.println("Hủy thanh toán thành công");
        iOrderService.deleteOrder(order);
        launcher();
    }

    private void handleMemberPayment(Orders orders) {
        UserView userView = new UserView();
        userView.showUser();
        System.out.println("Nhập id thành viên: ");
        int idMember = GetValue.getIntOfWithBounds(1,iUserService.getAll().size());
        User user = iUserService.findUserById(idMember);

        orders.setPhone(user.getPhone());
        orders.setName(user.getName());
        orders.setUsername(user.getUsername());
        orders.setTotalPrice();
        orders.setEStatus(EStatus.DONE);
        orders.setCreateAt(LocalDate.now());
        iOrderService.createOrder(orders);

        paymentConfirm(orders);

    }
    private void paymentConfirm(Orders order){
        System.out.println("Thông tin đơn hàng:");
        showOrderBeforPayment(order.getId());
        System.out.println("---------------------------------------------");
        System.out.println("-     Bạn có muốn hoàn thành thanh toán:    -");
        System.out.println("-     Nhập 1. Có                            -");
        System.out.println("-     Nhập 2. Không                         -");
        System.out.println("---------------------------------------------");
        int action = GetValue.getIntOfWithBounds(1, 2);
        switch (action){
            case 1:
                System.out.println("Thanh toán thành công");
                launcher();
                break;
            case 2:
                cancelOrder(order);
                break;
        }
    }

    private void processOrderItem(Orders orders, int idProduct, int quantity, Products p) {
        if (orders.getOrderItems() == null) {
            List<OrderItem> orderItems = new ArrayList<>();

            OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000, orders.getId(), idProduct, quantity, p.getPrice());

            orderItems.add(orderItem);

            orders.setOrderItems(orderItems);
        } else {
            if (checkProductExistInOrder(idProduct, orders)) {
                for (OrderItem orderItem : orders.getOrderItems()) {
                    if (orderItem.getIdProduct() == idProduct) {
                        orderItem.setQuantityItem(quantity);
                    }
                }
            } else {
                OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 100000, orders.getId(), idProduct, quantity, p.getPrice());
                orders.getOrderItems().add(orderItem);
            }
        }
    }

    public boolean checkProductExistInOrder(int idProduct, Orders orders){
        if(orders.getOrderItems() == null){
            return false;
        }else {
            for(OrderItem ot : orders.getOrderItems()){
                if(ot.getIdProduct() == idProduct){
                    return true;
                }
            }
        }
        return false;
    }
    private void showOrderBeforPayment(Long idOrder){
        Orders order = iOrderService.findOrderById(idOrder);
        System.out.println("-------------------------------------------------------");
        System.out.printf("- %-25s: %-24d -\n","Mã hóa đơn",order.getId());
        System.out.printf("- %-25s: %-24s -\n","FullName", order.getName());
        System.out.printf("- %-25s: %-24s -\n","Username", order.getUsername());
        System.out.printf("- %-25s: %-24s -\n","PhoneNumber",order.getPhone());
        System.out.printf("- %-25s: %-24s -\n","Discount", checkdiscount(order));


        if(checkdiscount(order).equals("10%")){
            order.setTotal((long) (order.getTotal()*0.9));
        }else if(checkdiscount(order).equals("20%")){
            order.setTotal((long) (order.getTotal()*0.8));
        }

        System.out.println("-------------------------------------------------------");
        System.out.printf("- %20s | %10s | %15s -\n", "Product", "Quantity", "Price");
        for(OrderItem ot : order.getOrderItems()){
            Products product = iProductsService.findProductById(ot.getIdProduct());
            System.out.printf("- %20s | %10s | %15s -\n",
                    product.getName(), ot.getQuantityItem(), ot.getPrice()*ot.getQuantityItem());
        }
        System.out.printf("- %20s   %10s   %15s -\n","Tổng tiền","",order.getTotal());
        System.out.println("-------------------------------------------------------");
        iOrderService.updateTotalOrder(idOrder,order.getTotal());
    }

    private String checkdiscount(Orders order) {
        long total = 0;
        List<Orders> orders = iOrderService.getAll();
        if(order.getUsername().equals("null")){
            total = 0;
        }else {
            for(Orders o : orders){
                if(o.getUsername().equals(order.getUsername()) && o.getId() != order.getId()){
                    total += o.getTotal();
                }
            }
        }

        if(total > 1000000 && total < 2000000){
            return "10%";
        }else if(total > 2000000){
            return "20%";
        }
        return "0%";
    }

    private void showOrderById(Long idOrder) {
        Orders orders = iOrderService.findOrderById(idOrder);
        if(orders != null){
            System.out.println("--------------------------------------------------------");
            System.out.printf("%-25s: %-25d\n","Mã hóa đơn",orders.getId());
            System.out.printf("%-25s: %-25s\n", "FullName", orders.getName());
            System.out.printf("%-25s: %-25s\n","Username", orders.getUsername());
            System.out.printf("%-25s: %-25s\n","PhoneNumber",orders.getPhone());
            System.out.printf("%-25s: %-25s\n","Trạng thái đơn hàng", orders.getEStatus());
            System.out.println("-------------------------------------------------------");
            System.out.printf("- %20s | %10s | %15s -\n", "Product", "Quantity", "Price");
            for(OrderItem ot : orders.getOrderItems()){
                Products product = iProductsService.findProductById(ot.getIdProduct());
                System.out.printf("- %20s | %10s | %15s -\n",
                        product.getName(), ot.getQuantityItem(), ot.getPrice()*ot.getQuantityItem());
            }
            System.out.printf("- %20s   %10s   %15s -\n","Tổng tiền","",orders.getTotal());
            System.out.println("-------------------------------------------------------");
        }else {
            System.out.println("Không tim order có id: " + idOrder);
        }

    }
    private void showOrder() {
        //1,23-08-2023,144000,Lê Công Vinh,01242257854,username1,hoàn thành
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                            "ID", "Name", "PhoneNumber","Username","CreateAt","Total","Status");

        List<Orders> orders = iOrderService.getAll();

        for(Orders order : orders){
            System.out.printf("- %8s | %30s | %20s | %20s | %20s | %15s | %15s -\n",
                    order.getId(),order.getName(),order.getPhone(), order.getUsername(),
                    order.getCreateAt(), order.getTotal(), order.getEStatus());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }


    public static void main(String[] args) {
        OrderView orderView = new OrderView();
        orderView.launcher();
    }
}
