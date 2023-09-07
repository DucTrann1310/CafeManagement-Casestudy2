package service;

import com.sun.org.apache.xpath.internal.operations.Or;
import models.Orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrderService {
    List<Orders> getAll();
    void updateOrder(int id, Orders order);
    void deleteOrder(int id);
    void createOrder(Orders order);
    void updateOrderByUpadteUser(String strdUsername, String name, String strPhone);
    void deleteOrder(Orders order);
    void updateStatusOrder(long idOrder);
    void upadteUsername(String strUsername);
    void updateTotalOrder(long idOrder, long total);
    Orders findOrderById(long id);
    List<Orders> findListOrderByOrderId(long idOrder);
    List<Orders> findListOrderByCreateAt(LocalDate localDate);
    List<Orders> findListOrderByName(String strName);
    List<Orders> findListOrderByUserName(String strUsername);
    List<Orders> findListOrderByPhone(String phone);
    List<Orders> getOrdersBetweenDay(LocalDate date);
    List<Orders> getOrderBetWeenMonth(LocalDate startDate, LocalDate endDate);
    List<Orders> getOrderBetWeenYear(LocalDate startDate, LocalDate endDate);

}
