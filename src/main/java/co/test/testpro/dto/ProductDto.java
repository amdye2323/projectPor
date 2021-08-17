package co.test.testpro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull
    private String username;

    @NotNull
    private int productId;

    @NotNull
    private String productName;

    @NotNull
    private String productImage;

    @NotNull
    private int productCost;

    @NotNull
    private int productCount;

    @NotNull
    private int productTotalCount;
}
