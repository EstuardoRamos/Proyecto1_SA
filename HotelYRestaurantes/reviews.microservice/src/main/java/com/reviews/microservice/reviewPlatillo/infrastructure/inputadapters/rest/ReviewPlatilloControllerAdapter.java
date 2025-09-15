// infrastructure/inputadapters/rest/ReviewPlatilloControllerAdapter.java
package com.reviews.microservice.reviewPlatillo.infrastructure.inputadapters.rest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviews.microservice.common.AggResumen;
import com.reviews.microservice.reviewPlatillo.application.inputports.CrearReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ListarReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ObtenerReviewPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.application.inputports.ResumenReviewsPlatilloInputPort;
import com.reviews.microservice.reviewPlatillo.infrastructure.inputadapters.rest.dto.CrearReviewPlatilloRequest;
import com.reviews.microservice.reviewPlatillo.infrastructure.inputadapters.rest.dto.ReviewPlatilloResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/reviews/platillos")
@RequiredArgsConstructor
public class ReviewPlatilloControllerAdapter {

  private final CrearReviewPlatilloInputPort crear;
  private final ObtenerReviewPlatilloInputPort obtener;
  private final ListarReviewsPlatilloInputPort listar;
  private final ResumenReviewsPlatilloInputPort resumen;


  
  @PostMapping
  public ReviewPlatilloResponse crear(@Valid @RequestBody CrearReviewPlatilloRequest req){
    var r = crear.crear(req.cuentaId(), req.platilloId(), req.estrellas(), req.comentario());
    return ReviewPlatilloResponse.from(r);
  }

  @GetMapping("/{id}")
  public ReviewPlatilloResponse porId(@PathVariable UUID id){
    var r = obtener.porId(id).orElseThrow(() -> new RuntimeException("Review no encontrada"));
    return ReviewPlatilloResponse.from(r);
  }

  // Solo arreglo (consistente con tus otros endpoints)
  @GetMapping
  public List<ReviewPlatilloResponse> listar(@RequestParam UUID platilloId,
                                             @ParameterObject Pageable pageable){
    return listar.porPlatillo(platilloId, pageable).map(ReviewPlatilloResponse::from).getContent();
  }

  @GetMapping("/summary")
  public Map<String,Object> summary(@RequestParam UUID platilloId){
    AggResumen a = resumen.resumen(platilloId);
    return Map.of("platilloId", platilloId,
                  "promedioEstrellas", a.promedioEstrellas(),
                  "totalReviews", a.totalReviews());
  }
}