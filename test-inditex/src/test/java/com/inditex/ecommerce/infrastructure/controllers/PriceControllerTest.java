package com.inditex.ecommerce.infrastructure.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.inditex.ecommerce.domain.services.PriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private PriceService priceService;

  @Test
  @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  void test1() throws Exception {
    performTest("2020-06-14-10.00.00", "35455", "1", 200);
  }

  @Test
  @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  void test2() throws Exception {
    performTest("2020-06-14-16.00.00", "35455", "1", 200);
  }

  @Test
  @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
  void test3() throws Exception {
    performTest("2020-06-14-21.00.00", "35455", "1", 200);
  }

  @Test
  @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
  void test4() throws Exception {
    performTest("2020-06-15-10.00.00", "35455", "1", 200);
  }

  @Test
  @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
  void test5() throws Exception {
    performTest("2020-06-16-21.00.00", "35455", "1", 200);
  }

  @Test
  void findApplicablePriceWhenNoPriceFound() throws Exception {
    mockMvc
        .perform(
            get("/price")
                .param("date", "2022-11-15-15.00.00")
                .param("productId", "354555")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void findApplicablePriceWhenBadRequest() throws Exception {
    mockMvc
        .perform(
            get("/price")
                .param("productId", "35455")
                .param("brandId", "1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private void performTest(String date, String productId, String brandId, int expectedStatus)
      throws Exception {
    mockMvc
        .perform(
            get("/price")
                .param("date", date)
                .param("productId", productId)
                .param("brandId", brandId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(expectedStatus))
        .andExpect(jsonPath("$.productId").value(productId));
  }
}
