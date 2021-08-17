package co.test.testpro.repository;

import co.test.testpro.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Repository
public class JapOrderRepository implements OrderRepository {

    private final EntityManager em;

    public JapOrderRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public Optional<Order> findOrder(Order order) {
        List<Order> orders = em.createQuery("select o FROM Order as o where o.username = :username and o.productId = :productId ",Order.class)
                .setParameter("productId",order.getProductId())
                .setParameter("username",order.getUsername())
                .getResultList();
        return orders.stream().findAny();
    }

    @Override
    public String saveOrder(Order order) {
        em.persist(order);
        return "success";
    }

    @Override
    public List<Order> findOrderList(String username) {
        List<Order> result = em.createQuery("select o from Order as o",Order.class)
                .getResultList();
        return result;
    }

    @Override
    public String orderPayMent(Order order) {
        return null;
    }

    @Override
    public Optional<List<Order>> findPayList(String username) {
        return Optional.empty();
    }

    @Override
    public String updateOrder(Order order,int count) {
        order.setProductCount(order.getProductCount()+count);
        em.persist(order);
        em.flush();
        return "success";
    }
}
