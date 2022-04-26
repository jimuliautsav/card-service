package com.utsav.cardservice.util;

import com.utsav.cardservice.controller.CreditCardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditCardValidator {
    private static final Logger logger = LoggerFactory.getLogger(CreditCardValidator.class);

    public static boolean isValidCreditCardNumber(String cardNumber){
        logger.info("Validate credit card number :" +cardNumber+ " for Luhn10 ");
        int[] a = {cardNumber.length() % 2 == 0 ? 1 : 2};
        boolean isLuhn = cardNumber.chars()
                .map(i -> i - '0')
                .map(n -> n * (a[0] = a[0] == 1 ? 2 : 1))
                .map(n -> n > 9 ? n - 9 : n)
                .sum() % 10 == 0;
        return isLuhn;
    }
}
