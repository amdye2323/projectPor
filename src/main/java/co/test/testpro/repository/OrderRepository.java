package co.test.testpro.repository;

import co.test.testpro.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findOrder(Order order);
    String saveOrder(Order order);
    List<Order> findOrderList(String username);
    String orderPayMent(Order order);
    Optional<List<Order>> findPayList(String username);
    String updateOrder(Order order,int count);
}
