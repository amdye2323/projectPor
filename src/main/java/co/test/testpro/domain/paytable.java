package co.test.testpro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "paytable")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class paytable {
    @JsonIgnore
    @Id
    @Column(name = "payid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payid;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "cost")
    private int cost;

    @NotNull
    @Column(name = "paygubun")
    private String paygubun;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP" , name = "createdate")
    private Date createDate;

    @Column(columnDefinition = "DATETIME DEFAULT '0000-00-00 00:00:00'" , name = "deletedate")
    private Date deleteDate;

}
