
// application/inputports/ResumenReviewsPlatilloInputPort.java
package com.reviews.microservice.reviewPlatillo.application.inputports;
import java.util.UUID;

import com.reviews.microservice.common.AggResumen;

public interface ResumenReviewsPlatilloInputPort {
  AggResumen resumen(UUID platilloId);
}