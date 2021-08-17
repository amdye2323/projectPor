package co.test.testpro.controller;

import co.test.testpro.domain.Product;
import co.test.testpro.repository.ProductRepository;
import co.test.testpro.service.MemberService;
import co.test.testpro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class homeController {

    private final ProductService productService;

    @Autowired //스프링 컨테이너에 빈으로 등록되어진 객체를 넣어줌 디펜더시 인젝션 -> 의존성 관계 주입
    public homeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/user/login";
    }

    @GetMapping("/order")
    public String order(Model model){
        List<Product> list = productService.getProductList();
        model.addAttribute("list",list);
        return "/product/productInquiry";
    }
}
