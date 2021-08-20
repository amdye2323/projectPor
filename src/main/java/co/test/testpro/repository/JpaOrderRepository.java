package co.test.testpro.repository;

import co.test.testpro.domain.Order;
import co.test.testpro.domain.paytable;
import co.test.testpro.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaOrderRepository implements OrderRepository {

    private final EntityManager em;

    public JpaOrderRepository(EntityManager em){
        this.em = em;
    }

    private static final Logger logger = LoggerFactory.getLogger(JpaOrderRepository.class);

    @Override
    public Optional<Order> findOrder(Order order) {
        try {
            List<Order> orders = em.createQuery("select o FROM Order as o where o.username = :username and o.productId = :productId and o.payment = 0 ",Order.class)
                    .setParameter("productId",order.getProductId())
                    .setParameter("username",order.getUsername())
                    .getResultList();
            return orders.stream().findAny();
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(order.getUsername()+" 주문 리스트 에러");
        }
        return null;
    }

    @Override
    public String saveOrder(Order order) {
        try {
            em.persist(order);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(order.getUsername()+" 주문 등록을 실패하였습니다.");
        }
        return null;
    }

    @Override
    public List<Order> findOrderList(String username) {
        try {
            List<Order> result = em.createQuery("select o from Order as o where o.username = :username and o.payment = 0 ",Order.class)
                    .setParameter("username",username)
                    .getResultList();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(username+" 주문 내역서 호출 실패하였습니다");
        }
        return null;
    }

    @Override
    public String orderPayMent(Order order) {
        try {
            em.persist(order);
            em.flush();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(e+"결제 실패하였습니다.");
        }
        return null;
    }

    @Override
    public String orderDeleteSubject(String username, int orderId) {
        try {
            Order order = em.find(Order.class,orderId);
            em.remove(order);
            em.flush();
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(username+" 주문 내역 삭제 실패하였습니다.");
        }
        return null;
    }

    @Override
    public Integer savePay(paytable pay) {
        try {
            em.persist(pay);
            em.flush();
            return pay.getPayid();
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(pay.getUsername()+" 결제를 실패하였습니다.");
        }
        return null;
    }

    @Override
    public Optional<List<paytable>> findPayList(String username) {
        try {
            List<paytable> list = em.createQuery("select p from paytable as p ",paytable.class)
                    .getResultList();
            return Optional.ofNullable(list);
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(username+"결제 내역서 호출을 실패하였습니다");
        }
        return null;

    }

    @Override
    public String updateOrder(Order order,int count) {
        try {
            order.setProductCount(order.getProductCount()+count);
            em.persist(order);
            em.flush();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            logger.debug(order.getUsername()+" 주문 내역서 수정 에러 발생하였습니다.");
        }
        return null;
    }
}
