package com.utsav.cardservice.controller;


import com.utsav.cardservice.exception.InvalidCardNumberException;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import com.utsav.cardservice.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class to create REST API endpoints for CreditCards
 *
 * @author UtsavJ
 */

@RestController
@RequestMapping(path = {"/api/v1/creditcards"}, produces = APPLICATION_JSON_VALUE)
public class CreditCardController {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardController.class);

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Operation(summary = "Returns a list of credit cards sorted/filtered based on the query parameters")
    @ApiResponse(responseCode = "200", description = "List of credit cards", content = {@Content(mediaType = APPLICATION_JSON_VALUE)})
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCreditCards(
            @RequestParam(required = false, name = "page",
                    defaultValue = "0") int page,
            @RequestParam(required = false, name = "size",
                    defaultValue = "20") int size,
            @RequestParam(required = false, name = "sortField",
                    defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, name = "direction",
                    defaultValue = "DESC") String direction
    ) {
        logger.info("Processing request to fetch credit cards!");

        //Build Pageable object for Pagination & Sorting based on request parameters
        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortField);

        Page<CreditCard> creditCards = creditCardService.getAllCreditCards(pagingSort);

        return ResponseEntity.ok(prepareResponse(creditCards));
    }

    @Operation(summary = "Add a new CreditCard")
    @ApiResponse(responseCode = "201", description = "CreditCard is added", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AddCardDto.class))})
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreditCard> addCreditCard(@Valid @RequestBody AddCardDto addCardDto) throws InvalidCardNumberException {
        logger.info("Processing request to add new credit card!");

        CreditCard creditCardResponse = creditCardService.save(addCardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creditCardResponse);
    }

    private Map<String, Object> prepareResponse(Page<CreditCard> pageResponse) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("creditCards", pageResponse.getContent());
        responseMap.put("currentPage", pageResponse.getNumber());
        responseMap.put("totalItems", pageResponse.getTotalElements());
        responseMap.put("totalPages", pageResponse.getTotalPages());
        return responseMap;
    }

}
