package co.test.testpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonIgnore
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name", length = 50, unique = true)
    private String productName;

    @Column(name = "product_cost", length = 50)
    private String productCost;

    @JsonIgnore
    @Column(name = "product_image", length = 200)
    private String productImage;

}
