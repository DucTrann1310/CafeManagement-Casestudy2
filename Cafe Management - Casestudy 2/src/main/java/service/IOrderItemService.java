package service;

import models.ECategory;
import models.OrderItem;

import java.time.LocalDate;
import java.util.List;

public interface IOrderItemService {
    List<OrderItem> getAll();
    List<OrderItem> getAllOrderItemByOrderId(long idOrder);
    void updateOrderItem(int id, OrderItem orderItem);
    void deleteOrderItem(int id);
    void createOrderItem(OrderItem orderItem);

}
