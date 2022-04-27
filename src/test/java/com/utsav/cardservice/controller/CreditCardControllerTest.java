package com.utsav.cardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utsav.cardservice.model.CreditCard;
import com.utsav.cardservice.model.dto.AddCardDto;
import com.utsav.cardservice.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CreditCardService creditCardService;

    @Test
    public void getAllCreditCards_success() throws Exception {
        List<CreditCard> records = new ArrayList<>(Arrays.asList(createCreditCard1(), createCreditCard2()));

        Pageable pagingSort = PageRequest.of(0, 20, Sort.Direction.fromString("ASC"), "name");
        Page<CreditCard> pageResponse = new PageImpl<>(records, pagingSort, records.size());

        Mockito.when(creditCardService.getAllCreditCards(Mockito.any(PageRequest.class))).thenReturn(pageResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/creditcards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems", org.hamcrest.Matchers.is(2)))
                .andExpect(jsonPath("$.creditCards[0].name", org.hamcrest.Matchers.is("Test")));
    }

    @Test
    public void addCreditCard_success() throws Exception {
        AddCardDto addCardDto = new AddCardDto("Test","4541380540412446",3000);

        Mockito.when(creditCardService.save(Mockito.any(AddCardDto.class))).thenReturn(createCreditCard1());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/creditcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(addCardDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber", org.hamcrest.Matchers.is(addCardDto.getCardNumber())))
                .andExpect(jsonPath("$.limit", org.hamcrest.Matchers.is(addCardDto.getLimit())))
                .andExpect(jsonPath("$.balance", org.hamcrest.Matchers.is(0)))
                .andExpect(jsonPath("$.name", org.hamcrest.Matchers.is(addCardDto.getName())));
    }

    private CreditCard createCreditCard1(){
        return new CreditCard("Test","4541380540412446", BigDecimal.ZERO,3000);
    }

    private CreditCard createCreditCard2(){
        return new  CreditCard("Test1","4541380540412489",BigDecimal.ZERO,3000);
    }
}