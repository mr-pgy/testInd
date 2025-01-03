package com.inditex.ecommerce.domain.ports.in;

import com.inditex.ecommerce.domain.models.PriceDto;
import java.time.LocalDateTime;
import java.util.Optional;

public interface FindApplicablePriceUseCase {

  /**
   * Finds the applicable price for product, brand and date.
   *
   * @param brandId brand identifier
   * @param productId product identifier
   * @param applicationDate date of the price
   * @return {@link Optional<PriceDto>} The applicable price for the given product, if not found
   *     result, returns Optional.empty()
   */
  Optional<PriceDto> findApplicablePrice(
          Long brandId, Long productId, LocalDateTime applicationDate);
}
