package service;

import models.EStatus;
import models.OrderItem;
import models.Orders;
import utils.DateUtils;
import utils.FileUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService{
    private final String fileOrder = "./Data/order.txt";
    private IOrderItemService iOrderItemService;
    public OrderService(){
        iOrderItemService = new OrderItemService();
    }

    @Override
    public List<Orders> getAll() {
        List<Orders> orders = FileUtils.readData(fileOrder,Orders.class);

        orders.stream().forEach(order -> {
            List<OrderItem> orderItems = iOrderItemService.getAllOrderItemByOrderId(order.getId());
            order.setOrderItems(orderItems);
                });
        return orders;
    }

    @Override
    public void updateOrder(int id, Orders order) {

    }

    @Override
    public void deleteOrder(int id) {

    }

    @Override
    public void createOrder(Orders order) {
        List<Orders> orders = getAll();
        orders.add(order);

        FileUtils.writeData(fileOrder,orders);

        //Lưu thêm orderItem
        for(OrderItem ot : order.getOrderItems()){
            iOrderItemService.createOrderItem(ot);
        }
    }

    @Override
    public void updateOrderByUpadteUser(String strUsername, String name, String strPhone) {
        List<Orders> orders = getAll();
        for(Orders order : orders){
            if(order.getUsername().equals(strUsername)){
                order.setName(name);
                order.setPhone(strPhone);
            }
        }
        FileUtils.writeData(fileOrder,orders);
    }

    @Override
    public void deleteOrder(Orders order) {
        List<Orders> orders = getAll();
        orders.removeIf(o -> o.getId() == order.getId());
        FileUtils.writeData(fileOrder,orders);
    }


    @Override
    public void updateStatusOrder(long idOrder) {
        List<Orders> orders = getAll();
        orders.stream().filter(o ->o.getId() == idOrder).findFirst().orElse(null).setEStatus(EStatus.DONE);
        FileUtils.writeData(fileOrder,orders);
    }

    @Override
    public void upadteUsername(String strUsername) {
        List<Orders> orders = getAll();
        orders.stream()
                .filter(o -> o.getUsername().equals(strUsername))
                .forEach(o -> o.setUsername(null));
        FileUtils.writeData(fileOrder,orders);
    }

    @Override
    public void updateTotalOrder(long idOrder, long total) {
        List<Orders> orders =getAll();
        orders.stream()
                .filter(o -> o.getId() == idOrder)
                .forEach(o -> o.setTotal(total));
        FileUtils.writeData(fileOrder,orders);
    }

    @Override
    public Orders findOrderById(long id) {
        List<Orders> orders = getAll();
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Orders> findListOrderByOrderId(long idOrder) {
        List<Orders> orders = getAll();
        return orders.stream().filter(order -> order.getId() == idOrder).collect(Collectors.toList());
    }

    @Override
    public List<Orders> findListOrderByCreateAt(LocalDate localDate) {
        List<Orders> orders = getAll();
        return orders.stream()
                .filter(order -> order.getCreateAt().equals(localDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> findListOrderByName(String strName) {
        List<Orders> orders = getAll();
        return  orders.stream()
                .filter(order -> order.getName().equals(strName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> findListOrderByUserName(String strUsername) {
        List<Orders> orders = getAll();
        return  orders.stream()
                .filter(order -> order.getUsername().equals(strUsername))
                .collect(Collectors.toList());
    }

    @Override
    public List<Orders> findListOrderByPhone(String phone) {
        List<Orders> orders = getAll();
        return  orders.stream()
                .filter(order -> order.getPhone().equals(phone))
                .collect(Collectors.toList());
    }


    @Override
    public List<Orders> getOrdersBetweenDay(LocalDate date) {
        List<Orders> orders = getAll();
        List<Orders> orderBetween = new ArrayList<>();
        for(Orders order : orders){

            if(order.getCreateAt().equals(date)){
                orderBetween.add(order);
            }
        }
        return orderBetween;
    }

    @Override
    public List<Orders> getOrderBetWeenMonth(LocalDate startDate, LocalDate endDate) {
        List<Orders> orders = getAll();
        List<Orders> orderBetween = new ArrayList<>();
        for(Orders order : orders){
            LocalDate orderDate = order.getCreateAt();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                orderBetween.add(order);
            }
        }
        return orderBetween;
    }

    @Override
    public List<Orders> getOrderBetWeenYear(LocalDate startDate, LocalDate endDate) {
        List<Orders> orders = getAll();
        List<Orders> orderBetween = new ArrayList<>();
        for(Orders order : orders){
            LocalDate orderDate = order.getCreateAt();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                orderBetween.add(order);
            }
        }
        return orderBetween;
    }

}
