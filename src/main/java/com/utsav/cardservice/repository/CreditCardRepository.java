package com.utsav.cardservice.repository;

import com.utsav.cardservice.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to handle DB operations for credit card
 *
 * @author UtsavJ
 */

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
