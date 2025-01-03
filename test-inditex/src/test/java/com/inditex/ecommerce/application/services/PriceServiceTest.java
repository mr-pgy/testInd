package com.inditex.ecommerce.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.inditex.ecommerce.domain.models.PriceDto;
import com.inditex.ecommerce.domain.ports.in.FindApplicablePriceUseCase;
import java.time.LocalDateTime;
import java.util.Optional;

import com.inditex.ecommerce.domain.services.PriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

  @Mock private FindApplicablePriceUseCase findApplicablePriceUseCase;

  @InjectMocks private PriceService priceService;

  @Test
  @DisplayName("Find applicable price with valid inputs")
  void findApplicablePriceWithValidInputs() {
    PriceDto priceDto = PriceDto.builder().build();
    when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
        .thenReturn(Optional.of(priceDto));

    Optional<PriceDto> result =
        priceService.findApplicablePrice(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0, 0));

    assertTrue(result.isPresent());
    assertEquals(priceDto, result.get());
  }

  @Test
  @DisplayName("Find applicable price with null application date")
  void findApplicablePriceWithNullApplicationDate() {
    when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
        .thenThrow(
            new IllegalArgumentException(
                "Application date, product ID, and brand ID must not be null"));

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              priceService.findApplicablePrice(1L, 35455L, null);
            });

    assertEquals(
        "Application date, product ID, and brand ID must not be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price with null product ID")
  void findApplicablePriceWithNullProductId() {

    when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
        .thenThrow(
            new IllegalArgumentException(
                "Application date, product ID, and brand ID must not be null"));

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              priceService.findApplicablePrice(1L, null, LocalDateTime.of(2020, 6, 14, 10, 0, 0));
            });

    assertEquals(
        "Application date, product ID, and brand ID must not be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price with null brand ID")
  void findApplicablePriceWithNullBrandId() {

    when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
        .thenThrow(
            new IllegalArgumentException(
                "Application date, product ID, and brand ID must not be null"));
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              priceService.findApplicablePrice(null, 35455L,
                  LocalDateTime.of(2020, 6, 14, 10, 0, 0));
            });

    assertEquals(
        "Application date, product ID, and brand ID must not be null", exception.getMessage());
  }

  @Test
  @DisplayName("Find applicable price when no price found")
  void findApplicablePriceWhenNoPriceFound() {
    when(findApplicablePriceUseCase.findApplicablePrice(any(), any(), any()))
        .thenReturn(Optional.empty());

    Optional<PriceDto> result =
        priceService.findApplicablePrice(1L, 354555L, LocalDateTime.of(2022, 11, 15, 15, 0, 0));

    assertTrue(result.isEmpty());
  }
}
