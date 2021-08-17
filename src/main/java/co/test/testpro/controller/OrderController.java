package co.test.testpro.controller;

import co.test.testpro.domain.Order;
import co.test.testpro.dto.DefaultResponseDto;
import co.test.testpro.dto.ProductDto;
import co.test.testpro.service.OrderService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@Valid @RequestBody List<ProductDto> productDtoList){
        orderService.addOrder(productDtoList);
        return new ResponseEntity<>(new DefaultResponseDto(200,"주문등록"), HttpStatus.OK);
    }

}
