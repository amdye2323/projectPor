package co.test.testpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
public class Order {

    @JsonIgnore
    @Id
    @Column(name = "orderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "productid")
    private int productId;

    @NotNull
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

    @Column(columnDefinition = "TIEMSTAMP DEFAULT CURRENT_TIMESTAMP" , name = "createdate")
    private Date createDate;

    @Column(columnDefinition = "DATETIME DEFAULT '0000-00-00 00:00:00'" , name = "deletedate")
    private Date deleteDate;

    @Column(name = "payment")
    @ColumnDefault("0")
    private int payment;

    @Column(name = "payid")
    private Integer payid;
}
