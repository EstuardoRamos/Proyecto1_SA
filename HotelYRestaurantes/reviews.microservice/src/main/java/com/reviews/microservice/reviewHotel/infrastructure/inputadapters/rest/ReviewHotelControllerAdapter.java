// infrastructure/inputadapters/rest/ReviewHotelControllerAdapter.java
package com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest;

import com.reviews.microservice.reviewHotel.application.inputports.*;
import com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/reviews/hotel")
@CrossOrigin(origins = "*")
public class ReviewHotelControllerAdapter {

    private final CrearReviewHotelInputPort crear;
    private final ListarReviewsHotelInputPort listar;
    private final ObtenerPromedioHotelInputPort promedio;

    public ReviewHotelControllerAdapter(CrearReviewHotelInputPort crear,
            ListarReviewsHotelInputPort listar,
            ObtenerPromedioHotelInputPort promedio) {
        this.crear = crear;
        this.listar = listar;
        this.promedio = promedio;
    }

    // Crear review (siempre devuelve el objeto creado)
    @PostMapping
    public ReviewHotelResponse crear(@Valid @RequestBody CrearReviewHotelRequest req) {
        var r = crear.crear(req.facturaHotelId(), req.estrellas(), req.comentario(), req.tags());
        return ReviewHotelResponse.from(r);
    }

    // Listar por hotel (devuelve arreglo simple)
    @GetMapping
    public List<ReviewHotelResponse> porHotel(@RequestParam UUID hotelId) {
        return listar.porHotel(hotelId).stream().map(ReviewHotelResponse::from).toList();
    }

    // Listar por factura (para ver si ya opinó)
    @GetMapping("/factura/{facturaId}")
    public List<ReviewHotelResponse> porFactura(@PathVariable UUID facturaId) {
        return listar.porFactura(facturaId).stream().map(ReviewHotelResponse::from).toList();
    }

    // Promedio y total
    @GetMapping("/promedio")
    public PromedioResponse promedio(@RequestParam UUID hotelId) {
        var r = promedio.promedio(hotelId);
        return new PromedioResponse(r.hotelId(), r.promedio(), r.total());
    }
}
