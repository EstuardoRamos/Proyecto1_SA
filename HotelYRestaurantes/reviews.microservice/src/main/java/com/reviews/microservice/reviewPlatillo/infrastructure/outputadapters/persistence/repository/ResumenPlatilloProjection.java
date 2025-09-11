// infrastructure/outputadapters/persistence/repository/ResumenPlatilloProjection.java
package com.reviews.microservice.reviewPlatillo.infrastructure.outputadapters.persistence.repository;

import java.util.UUID;

public interface ResumenPlatilloProjection {

    UUID getPlatilloId();

    Double getPromedio();

    Long getTotal();
}
