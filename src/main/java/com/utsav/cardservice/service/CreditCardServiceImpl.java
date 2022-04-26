package com.utsav.cardservice.service;

import com.utsav.cardservice.controller.CreditCardController;
import com.utsav.cardservice.exception.InvalidCardNumberException;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import com.utsav.cardservice.repository.CreditCardRepository;
import com.utsav.cardservice.util.CreditCardValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    private final CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Page<CreditCard> getAllCreditCards(Pageable pageable) {
        logger.info("");
        return creditCardRepository.findAll(pageable);
    }

    @Override
    public CreditCard save(AddCardDto addCardDto) throws InvalidCardNumberException {

        //Validate Luhn10 for credit card number
        if (!CreditCardValidator.isValidCreditCardNumber(addCardDto.getCardNumber())) {
            throw new InvalidCardNumberException(addCardDto.getCardNumber() + " is not a valid card number");
        }
        return creditCardRepository.save(convertToEntity(addCardDto));
    }

    private CreditCard convertToEntity(AddCardDto addCardDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(AddCardDto.class, CreditCard.class).addMappings(mapper -> mapper.skip(CreditCard::setCreatedAt));

        return modelMapper.map(addCardDto, CreditCard.class);
    }
}
