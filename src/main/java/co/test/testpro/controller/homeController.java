package co.test.testpro.controller;

import co.test.testpro.domain.Order;
import co.test.testpro.domain.Product;
import co.test.testpro.dto.DefaultResponseDto;
import co.test.testpro.repository.ProductRepository;
import co.test.testpro.service.MemberService;
import co.test.testpro.service.OrderService;
import co.test.testpro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class homeController {

    private final ProductService productService;

    private final OrderService orderService;

    @Autowired //스프링 컨테이너에 빈으로 등록되어진 객체를 넣어줌 디펜더시 인젝션 -> 의존성 관계 주입
    public homeController(OrderService orderService,ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }

    @GetMapping("/orderpage")
    public String order(Model model){
        List<Product> list = productService.getProductList();
        model.addAttribute("list",list);
        return "product/productInquiry";
    }

    @GetMapping("/getOrderSheet")
    public String getOrderSheet(String username,Model model){
        List<Order> list = orderService.orderList(username);
        int totalCount = list.stream().mapToInt(Order::getProductCost).sum();
        model.addAttribute("list",list);
        model.addAttribute("totalCost",totalCount);
        return "product/orderSheet";
    }
}
