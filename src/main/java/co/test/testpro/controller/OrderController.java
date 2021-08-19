package co.test.testpro.controller;

import co.test.testpro.domain.Order;
import co.test.testpro.dto.DefaultResponseDto;
import co.test.testpro.dto.ProductDto;
import co.test.testpro.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.lettuce.core.dynamic.annotation.Param;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping("/payToOrder")
    public ResponseEntity<?> payToOrder(String username,String payGubun){
        orderService.orderPayMent(username,payGubun);
        return new ResponseEntity<>(new DefaultResponseDto(200,"결제완료"), HttpStatus.OK);
    }

    @PostMapping("/orderList")
    public ResponseEntity<?> orderList(String username){
        List<Order> list = orderService.orderList(username);
        int totalCount = list.stream().mapToInt(Order::getProductCost).sum();
        JSONObject obj = new JSONObject();
        obj.put("list",list);
        obj.put("totalCount",totalCount);
        obj.put("msg",new DefaultResponseDto(200,"결제완료"));

        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping("/removeOrder")
    public ResponseEntity<?> removeOrder(String username,int orderId){
        orderService.orderDeleteSubject(username,orderId);
        return new ResponseEntity<>(new DefaultResponseDto(200,"주문삭제"), HttpStatus.OK);
    }
}
