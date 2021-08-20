package co.test.testpro.service;

import co.test.testpro.domain.Order;
import co.test.testpro.domain.paytable;
import co.test.testpro.dto.ProductDto;
import co.test.testpro.repository.JpaOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final JpaOrderRepository jpaOrderRepository;

    @Autowired
    public OrderService(JpaOrderRepository jpaOrderRepository){
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Transactional
    public String addOrder(List<ProductDto> list){
        list.forEach(
                productDto -> {
                    Date date = new Date();
                    Order order = Order.builder()
                            .username(productDto.getUsername())
                            .productId(productDto.getProductId())
                            .productStandard(productDto.getProductCost())
                            .productCount(productDto.getProductCount())
                            .productCost(productDto.getProductTotalCount())
                            .productName(productDto.getProductName())
                            .payment(0)
                            .build();

                    if(jpaOrderRepository.findOrder(order).orElse(null) != null){ //username과 재품으로 오더에 유무확인
                        //update
                        Order or = jpaOrderRepository.findOrder(order).orElse(new Order());
                        jpaOrderRepository.updateOrder(or,productDto.getProductCount());
                    }else{
                        //insert
                        jpaOrderRepository.saveOrder(order);
                    }
                }
        );
        return "success";
    }

    public List<Order> orderList(String username){
        return jpaOrderRepository.findOrderList(username);
    }

    @Transactional
    public String orderPayMent(String username,String payGubun){
        List<Order> list = jpaOrderRepository.findOrderList(username);
        int totalCount = list.stream().mapToInt(Order::getProductCost).sum();

        paytable pay = paytable.builder()
                .cost(totalCount)
                .paygubun(payGubun)
                .username(username)
                .build();

        Integer payId = jpaOrderRepository.savePay(pay);

        list.forEach(
                productDto -> {
                    productDto.setPayment(1);
                    productDto.setPayid(payId);
                    String result = jpaOrderRepository.orderPayMent(productDto);
                    if(!(result =="success")){
                        throw new RuntimeException(username + " 님의 결제 전환이 실패하였습니다.");
                    }
                }
        );
        return "success";
    }

    public String orderDeleteSubject(String username,int orderId){
        return jpaOrderRepository.orderDeleteSubject(username, orderId);
    }

    public Optional<List<paytable>> getPayList(String username){
        return jpaOrderRepository.findPayList(username);
    }
}
