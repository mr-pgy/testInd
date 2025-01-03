package com.inditex.ecommerce.infrastructure.adapters;

import com.inditex.ecommerce.domain.models.PriceDto;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import com.inditex.ecommerce.infrastructure.mappers.PriceMapper;
import com.inditex.ecommerce.infrastructure.repositories.PriceRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final PriceRepository priceRepository;
  private final PriceMapper priceMapper;

  public Optional<PriceDto> findApplicablePrice(
          Long brandId, Long productId, LocalDateTime applicationDate) {

    if (Objects.isNull(brandId) || Objects.isNull(productId) || Objects.isNull(applicationDate)) {
      throw new IllegalArgumentException(
          "brandId, productId and applicationDate cant be null");
    }

    return priceRepository
        .findApplicablePrice(brandId, productId, applicationDate)
        .map(priceMapper::toPriceDto);
  }
}
