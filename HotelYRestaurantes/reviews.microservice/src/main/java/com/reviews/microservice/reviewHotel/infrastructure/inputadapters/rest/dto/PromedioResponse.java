// infrastructure/inputadapters/rest/dto/PromedioResponse.java
package com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto;

import java.util.UUID;

public record PromedioResponse(UUID hotelId, double promedio, long total) {

}
