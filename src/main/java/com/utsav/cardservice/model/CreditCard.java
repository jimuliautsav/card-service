package com.utsav.cardservice.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * Entity class for credit card
 *
 * @author UtsavJ
 */

@Entity
@Table(name = "credit_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCard extends BaseEntity{

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "card_number", nullable = false, length = 19)
    private String cardNumber;

    @Column(name = "balance", nullable = false, length = 50)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "card_limit", nullable = false, length = 50)
    private Integer limit;
}
