package co.test.testpro.service;

import co.test.testpro.domain.Order;
import co.test.testpro.dto.ProductDto;
import co.test.testpro.repository.JapOrderRepository;
import co.test.testpro.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final JapOrderRepository jpaOrderRepository;

    @Autowired
    public OrderService(JapOrderRepository jpaOrderRepository){
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Transactional
    public String addOrder(List<ProductDto> list){
        list.forEach(
                productDto -> {
                    Order order = Order.builder()
                            .username(productDto.getUsername())
                            .productId(productDto.getProductId())
                            .productStandard(productDto.getProductCost())
                            .productCount(productDto.getProductCount())
                            .productCost(productDto.getProductTotalCount())
                            .productName(productDto.getProductName())
                            .payMent(0)
                            .build();
                    if(jpaOrderRepository.findOrder(order).orElse(null) != null){
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
}
