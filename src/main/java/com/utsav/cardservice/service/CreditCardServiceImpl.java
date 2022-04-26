package com.utsav.cardservice.service;

import com.utsav.cardservice.exception.InvalidCardNumberException;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import com.utsav.cardservice.repository.CreditCardRepository;
import com.utsav.cardservice.util.CreditCardValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * Fetch all credit cards from DB
     * @param pageable provide page size and sorting order as query parameter
     * @return list of credit cards based on query parameter
     */
    @Override
    public Page<CreditCard> getAllCreditCards(Pageable pageable) {
        logger.info("Request to fetch creditcards from DB");
        return creditCardRepository.findAll(pageable);
    }

    /**
     * Save a credit card
     *
     * @param addCardDto credit card details to be stored in DB
     * @return the saved credit card details
     * @throws InvalidCardNumberException if Luhn10 check fails for given credit card number
     */
    @Override
    public CreditCard save(AddCardDto addCardDto) throws InvalidCardNumberException {
        logger.info("Request to store new credit card into DB");

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
