// infrastructure/inputadapters/rest/ReviewHotelControllerAdapter.java
package com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviews.microservice.common.errors.NotFoundException;
import com.reviews.microservice.reviewHotel.application.inputports.CrearReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.DeshabilitarReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.HabilitarReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.ListarReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.ObtenerPromedioHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.ObtenerReviewHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.ResumenReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.application.inputports.TopReviewsHotelInputPort;
import com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto.CrearReviewHotelRequest;
import com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto.PromedioResponse;
import com.reviews.microservice.reviewHotel.infrastructure.inputadapters.rest.dto.ReviewHotelResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

//@CrossOrigin(origins = "http://frontend-comerdormir.s3-website.us-east-2.amazonaws.com", allowCredentials = "true")
@RestController
@RequestMapping("/v1/reviews/hotel")
//@CrossOrigin(origins = "*")
public class ReviewHotelControllerAdapter {

    private final CrearReviewHotelInputPort crear;
     private final ObtenerReviewHotelInputPort obtener;
    private final ListarReviewsHotelInputPort listar;
    private final ObtenerPromedioHotelInputPort promedio;
    private final ResumenReviewsHotelInputPort resumen;
    private final TopReviewsHotelInputPort top;
    private final HabilitarReviewHotelInputPort habilitar;
    private final DeshabilitarReviewHotelInputPort deshabilitar;

    public ReviewHotelControllerAdapter(CrearReviewHotelInputPort crear, ObtenerReviewHotelInputPort obtener,
            ListarReviewsHotelInputPort listar,
            ObtenerPromedioHotelInputPort promedio, ResumenReviewsHotelInputPort resumen,
            TopReviewsHotelInputPort top,
            HabilitarReviewHotelInputPort habilitar,
            DeshabilitarReviewHotelInputPort deshabilitar) {
        this.obtener = obtener;
        this.crear = crear;
        this.listar = listar;
        this.promedio = promedio;
        this.resumen = resumen;
        this.top = top;
        this.habilitar = habilitar;
        this.deshabilitar = deshabilitar;
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

    @Operation(summary = "Obtener review por id")
    @GetMapping("/{id}")
    public ReviewHotelResponse porId(@PathVariable UUID id) {
        var r = obtener.porId(id).orElseThrow(() -> new NotFoundException("Review no encontrada"));
        return ReviewHotelResponse.from(r);
    }

    @Operation(summary = "Resumen (promedio y total) de un hotel")
    @GetMapping("/summary")
    public Map<String, Object> resumen(@RequestParam UUID hotelId) {
        var s = this.resumen.resumen(hotelId);
        return Map.of("total", s.total(), "promedio", s.promedio());
    }

    @Operation(summary = "Top reviews por hotel")
    @GetMapping("/top")
    public List<ReviewHotelResponse> top(@RequestParam UUID hotelId,
            @RequestParam(defaultValue = "5") int limit) {
        return top.top(hotelId, limit).stream().map(ReviewHotelResponse::from).toList();
    }

    @Operation(summary = "Habilitar review")
    @PatchMapping("/{id}/enable")
    public void enable(@PathVariable UUID id) {
        habilitar.habilitar(id);
    }

    @Operation(summary = "Deshabilitar review")
    @PatchMapping("/{id}/disable")
    public void disable(@PathVariable UUID id) {
        deshabilitar.deshabilitar(id);
    }
}
