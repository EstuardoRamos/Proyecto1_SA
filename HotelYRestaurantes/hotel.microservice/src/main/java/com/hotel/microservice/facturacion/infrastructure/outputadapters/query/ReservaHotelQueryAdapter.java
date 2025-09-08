// hotel.microservice/src/main/java/com/hotel/microservice/facturacion/infrastructure/outputadapters/query/ReservaHotelQueryAdapter.java
package com.hotel.microservice.facturacion.infrastructure.outputadapters.query;

import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort.Item;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort.ReservaFacturaSnapshot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Ajusta estos imports a tu paquete real
import com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.repository.ReservaJpaRepository;
import com.hotel.microservice.reserva.infrastructure.outputadapters.persistence.entity.ReservaDbEntity;

@Component
@RequiredArgsConstructor
public class ReservaHotelQueryAdapter implements ReservaHotelQueryPort {

    private final ReservaJpaRepository reservas;

    @Override
    public Optional<ReservaFacturaSnapshot> snapshotFactura(UUID reservaId) {
        return reservas.findById(reservaId).map(this::toSnapshot);
    }

    private ReservaFacturaSnapshot toSnapshot(ReservaDbEntity r) {
        var hotelId = r.getHotelId();
        var reservaId = r.getId();
        var entrada = r.getEntrada();
        var salida = r.getSalida();

        long noches = Math.max(1, ChronoUnit.DAYS.between(entrada, salida));
        BigDecimal total = nz(r.getTotal());
        BigDecimal precioNoche = total.divide(BigDecimal.valueOf(noches), 2, RoundingMode.HALF_UP);

        var item = new Item(
                "Estadía " + noches + " noche(s) (" + entrada + " a " + salida + ")",
                precioNoche,
                (int) noches,
                total // sin desglose: usamos total como subtotal del ítem
        );

        return new ReservaFacturaSnapshot(
                hotelId,
                reservaId,
                "GTQ", // default
                total, // subtotal
                BigDecimal.ZERO, // impuesto
                BigDecimal.ZERO, // propina
                total, // total
                List.of(item),
                r.getEstado() // String en tu entidad
        );
    }

    private static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
