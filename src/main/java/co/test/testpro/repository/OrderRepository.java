package co.test.testpro.repository;

import co.test.testpro.domain.Order;
import co.test.testpro.domain.paytable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findOrder(Order order);
    String saveOrder(Order order);
    List<Order> findOrderList(String username);
    String orderPayMent(Order order);
    String orderDeleteSubject(String username,int orderId);
    Integer savePay(paytable pay);
    Optional<List<paytable>> findPayList(String username);
    String updateOrder(Order order,int count);
}
