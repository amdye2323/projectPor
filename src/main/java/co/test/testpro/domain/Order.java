package co.test.testpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "ordertable")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @JsonIgnore
    @Id
    @Column(name = "orderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "productid")
    private int productId;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "productname")
    private String productName;

    @NotNull
    @Column(name = "productstandard")
    private int productStandard;

    @NotNull
    @Column(name = "productcount")
    private int productCount;

    @NotNull
    @Column(name = "productcost")
    private int productCost;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" , name = "createdate")
    private Date createDate;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" , name = "updatedate")
    private Date updateDate;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" , name = "deletedate")
    private Date deleteDate;

    @Column(name = "payment")
    @ColumnDefault("0")
    private int payMent;
}
