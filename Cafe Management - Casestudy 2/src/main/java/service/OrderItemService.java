package service;

import models.OrderItem;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderItemService implements IOrderItemService{
    private final String fileOrderItem = "./Data/order_details.txt";

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> orderItems = FileUtils.readData(fileOrderItem,OrderItem.class);
        return orderItems;
    }

    @Override
    public List<OrderItem> getAllOrderItemByOrderId(long idOrder) {
        List<OrderItem> orderItems = getAll();

//        List<OrderItem> result = new ArrayList<>();
//        for(OrderItem item : orderItems){
//            if(item.getIdOrder() == idOrder){
//                result.add(item);
//            }
//        }

        List<OrderItem> results =  orderItems.stream()
                .filter(orderItem -> orderItem.getIdOrder() == idOrder)
                .collect(Collectors.toList());

        return results;
    }

    @Override
    public void updateOrderItem(int id, OrderItem orderItem) {

    }

    @Override
    public void deleteOrderItem(int id) {

    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        List<OrderItem> orderItems = getAll();
        orderItems.add(orderItem);
        FileUtils.writeData(fileOrderItem,orderItems);
    }

}
