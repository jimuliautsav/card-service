package com.utsav.cardservice.service;

import com.utsav.cardservice.exception.BadRequestException;
import com.utsav.cardservice.exception.InvalidCardNumberException;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {

    /**
     * Returns a list of credit cards sorted/filtered based on the query parameters
     * @return list of creditCards
     */
    Page<CreditCard> getAllCreditCards(Pageable pageable);
    /**
     * Save a Credit card
     * @param creditCard object to be stored in DB
     * @return saved creditCard object
     */
    CreditCard save(AddCardDto addCardDto) throws InvalidCardNumberException;
}
