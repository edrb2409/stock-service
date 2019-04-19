package io.edrb.stockservice.controller;

import io.edrb.stockservice.controller.mapper.UpdateStockMapper;
import io.edrb.stockservice.model.Stock;
import io.edrb.stockservice.model.dto.UpdateStockDTO;
import io.edrb.stockservice.service.StockService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;

@WebFluxTest
@ExtendWith(SpringExtension.class)
public class UpdateStockControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StockService stockService;

    static ZonedDateTime clock;

    @BeforeAll static void init(){
        clock = ZonedDateTime.now();
    }

    void shouldRespondOKWhenNewStockWasCreated() {
        UpdateStockDTO updateStockDTO = UpdateStockDTO.builder()
                .productId("vegetables")
                .quantity(100)
                .timestamp(clock)
                .build();

        verify(stockService).updateStock(Stock.builder()
                .productId("vegetables")
                .quantity(100)
                .timestamp(clock)
                .build());

        webTestClient.post().uri("/updateStock")
                .body(BodyInserters.fromObject(updateStockDTO))
                .exchange()
                .expectStatus().isCreated();

    }

}
