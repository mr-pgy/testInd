package com.inditex.ecommerce.application.usecases;

import com.inditex.ecommerce.domain.models.PriceDto;
import com.inditex.ecommerce.domain.ports.in.FindApplicablePriceUseCase;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindApplicablePriceUseCaseImpl implements FindApplicablePriceUseCase {

  private final PriceRepositoryPort priceRepositoryPort;

  @Override
  public Optional<PriceDto> findApplicablePrice(
          Long brandId, Long productId, LocalDateTime applicationDate) {

    if (Objects.isNull(brandId) || Objects.isNull(productId) || Objects.isNull(applicationDate)) {
      throw new IllegalArgumentException(
          "brandId, productId and applicationDate cant be null");
    }

    return priceRepositoryPort.findApplicablePrice(brandId, productId, applicationDate);
  }
}
