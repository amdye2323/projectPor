package co.test.testpro.service;

import co.test.testpro.domain.Product;
import co.test.testpro.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProductList(){
        return productRepository.findAll();
    }

}
