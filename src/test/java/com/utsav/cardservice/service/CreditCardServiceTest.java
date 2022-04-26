package com.utsav.cardservice.service;

import com.utsav.cardservice.exception.InvalidCardNumberException;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import com.utsav.cardservice.repository.CreditCardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepositoryMock;

    private CreditCardService creditCardService;

    CreditCard creditCard1 = new CreditCard("Test","4541380540412446",BigDecimal.ZERO,3000);

    @BeforeEach
    void setUp() {
        creditCardService = new CreditCardServiceImpl(creditCardRepositoryMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCreditCards() {
        List<CreditCard> records = new ArrayList<>(Arrays.asList(createCreditCard1(), createCreditCard2()));

        Pageable pagingSort = PageRequest.of(0, 20, Sort.Direction.fromString("ASC"), "name");
        Page<CreditCard> pageResponse = new PageImpl<>(records, pagingSort, records.size());

        when(creditCardRepositoryMock.findAll(Mockito.any(Pageable.class))).thenReturn(pageResponse);
        Page<CreditCard> creditCardPageResponse = creditCardService.getAllCreditCards(pagingSort);

        assertEquals(creditCardPageResponse.getTotalElements(),pageResponse.getTotalElements());
        assertEquals(creditCardPageResponse.getContent().size(),pageResponse.getContent().size());
    }

    @Test
    void saveCreditCard_success() throws InvalidCardNumberException {
        AddCardDto addCardDto = new AddCardDto("Test","4541380540412446",3000);

        when(creditCardRepositoryMock.save(Mockito.any(CreditCard.class))).thenReturn(createCreditCard1());
        CreditCard savedCreditCard = creditCardService.save(addCardDto);

        assertEquals(savedCreditCard.getCardNumber(), addCardDto.getCardNumber());
        assertEquals(savedCreditCard.getName(), addCardDto.getName());
        assertEquals(savedCreditCard.getLimit(), addCardDto.getLimit());
        assertEquals(savedCreditCard.getBalance(), BigDecimal.ZERO);
    }

    @Test
    void saveCreditCardInvalidCardNumber_exception() throws InvalidCardNumberException {
        //Given
        AddCardDto addCardDto = new AddCardDto("Test","4541380540412449",3000);

        //When
        InvalidCardNumberException invalidCardNumberException = assertThrows(InvalidCardNumberException.class,
                () -> creditCardService.save(addCardDto));
        //Then
        assertEquals(addCardDto.getCardNumber()+" is not a valid card number", invalidCardNumberException.getMessage());
    }

    private CreditCard createCreditCard1(){
        return new CreditCard("Test","4541380540412446", BigDecimal.ZERO,3000);
    }
    private CreditCard createCreditCard2(){
        return new  CreditCard("Test1","4541380540412489",BigDecimal.ZERO,3000);
    }
}