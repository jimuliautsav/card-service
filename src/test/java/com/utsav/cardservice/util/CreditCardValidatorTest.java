package com.utsav.cardservice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CreditCardValidatorTest {

    @Test
    void isValidCreditCardNumber_success() {
        String validCreditCardNUmber = "4541380540412446";
        assertTrue(CreditCardValidator.isValidCreditCardNumber(validCreditCardNUmber));
    }

    @Test
    void isValidCreditCardNumber_failure() {
        String validCreditCardNUmber = "4541380540412447";
        assertTrue(!CreditCardValidator.isValidCreditCardNumber(validCreditCardNUmber));
    }
}