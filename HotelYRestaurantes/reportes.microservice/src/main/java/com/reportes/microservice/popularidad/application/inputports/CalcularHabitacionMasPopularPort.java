// application/inputports/CalcularHabitacionMasPopularPort.java
package com.reportes.microservice.popularidad.application.inputports;

import java.time.LocalDate;
import java.util.UUID;

import com.reportes.microservice.popularidad.application.dto.HabitacionPopularDTO;

public interface CalcularHabitacionMasPopularPort {

    HabitacionPopularDTO ejecutar(LocalDate desde, LocalDate hasta, UUID hotelId);
}
