package com.inditex.ecommerce.infrastructure.adapters;

import static org.junit.jupiter.api.Assertions.*;

import com.inditex.ecommerce.domain.models.PriceDto;
import com.inditex.ecommerce.infrastructure.mappers.PriceMapper;
import com.inditex.ecommerce.infrastructure.repositories.PriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PriceRepositoryAdapterTest {

  @Autowired private PriceRepositoryAdapter priceRepositoryAdapter;

  @Autowired private PriceRepository priceRepository;

  @Autowired private PriceMapper priceMapper;

  @Test
  @DisplayName("Find applicable price with valid inputs")
  void findApplicablePriceWithValidInputs() {

    Optional<PriceDto> result =
        priceRepositoryAdapter.findApplicablePrice(1L, 35455L,
            LocalDateTime.of(2020, 6, 14, 10, 0, 0));

    assertTrue(result.isPresent());
    assertEquals(35455L, result.get().getProductId());
    assertEquals(1L, result.get().getBrandId());
  }

  @Test
  @DisplayName("Find applicable price with null application date")
  void findApplicablePriceWithNullApplicationDate() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> priceRepositoryAdapter.findApplicablePrice(1L, 35455L, null));

    assertEquals(
        "brandId, productId and applicationDate cant be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price with null product ID")
  void findApplicablePriceWithNullProductId() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                priceRepositoryAdapter.findApplicablePrice(1L, null,
                    LocalDateTime.of(2020, 6, 14, 10, 0, 0)));

    assertEquals(
        "brandId, productId and applicationDate cant be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price with null brand ID")
  void findApplicablePriceWithNullBrandId() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () ->
                priceRepositoryAdapter.findApplicablePrice(null, 35455L,
                    LocalDateTime.of(2020, 6, 14, 10, 0, 0)));

    assertEquals(
        "brandId, productId and applicationDate cant be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price when no price found")
  void findApplicablePriceWhenNoPriceFound() {
    Optional<PriceDto> result =
        priceRepositoryAdapter.findApplicablePrice(1L, 354555L,
            LocalDateTime.of(2022, 11, 15, 15, 0, 0));

    assertTrue(result.isEmpty());
  }
}
