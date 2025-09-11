// application/usecases/CrearReviewPlatilloUseCase.java
package com.reviews.microservice.reviewPlatillo.application.usecases;

import java.util.UUID;

import com.reviews.microservice.reviewPlatillo.application.inputports.CrearReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.persistence.ReviewPlatilloRepositorioPort;
import com.reviews.microservice.reviewPlatillo.application.outputports.query.RestauranteCuentaQueryPort;
import com.reviews.microservice.reviewPlatillo.domain.ReviewPlatillo;

public class CrearReviewPlatilloUseCase implements CrearReviewPlatilloInputPort {

    private final ReviewPlatilloRepositorioPort repo;
    private final RestauranteCuentaQueryPort cuentas;

    public CrearReviewPlatilloUseCase(ReviewPlatilloRepositorioPort repo, RestauranteCuentaQueryPort cuentas) {
        this.repo = repo;
        this.cuentas = cuentas;
    }

    @Override
    public ReviewPlatillo crear(UUID cuentaId, UUID platilloId, int estrellas, String comentario) {
        var snap = cuentas.cuenta(cuentaId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        if (!cuentas.cuentaContienePlatillo(cuentaId, platilloId)) {
            throw new IllegalStateException("El platillo no pertenece a la cuenta");
        }
        if (repo.existsByCuentaAndPlatillo(cuentaId, platilloId)) {
            throw new IllegalStateException("Ya existe una review para este platillo en esta cuenta");
        }
        var r = ReviewPlatillo.nueva(cuentaId, snap.restauranteId(), platilloId, snap.clienteId(), estrellas, comentario);
        return repo.guardar(r);
    }
}
