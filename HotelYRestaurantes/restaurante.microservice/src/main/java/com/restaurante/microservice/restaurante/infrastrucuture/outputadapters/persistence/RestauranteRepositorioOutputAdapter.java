package com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence;

import com.restaurante.microservice.restaurante.application.outputports.RestauranteRepositorioPort;
import com.restaurante.microservice.restaurante.domain.Restaurante;
import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.entity.RestauranteDbEntity;
//import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.repository.RestauranteJpaRepository;
import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.respository.RestauranteJpaRepository;
//import com.restaurante.microservice.restaurante.infrastrucuture.outputadapters.persistence.respository.RestauranteJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RestauranteRepositorioOutputAdapter implements RestauranteRepositorioPort {

    private final RestauranteJpaRepository jpa;

    public RestauranteRepositorioOutputAdapter(RestauranteJpaRepository jpa) {
        this.jpa = jpa;
    }

    private static Restaurante toDomain(RestauranteDbEntity e) {
        return Restaurante.rehidratar(
                e.getId(),
                e.getHotelId(),
                e.getNombre(),
                e.getDireccion(),
                e.getImpuestoPorc(),
                e.getPropinaPorcDefault(),
                e.isEnabled(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }

    private static RestauranteDbEntity toEntity(Restaurante r) {
        var e = new RestauranteDbEntity();
        e.setId(r.getId());
        e.setHotelId(r.getHotelId());
        e.setNombre(r.getNombre());
        e.setDireccion(r.getDireccion());
        e.setImpuestoPorc(r.getImpuestoPorc());
        e.setPropinaPorcDefault(r.getPropinaPorcDefault());
        e.setEnabled(r.isEnabled());
        e.setCreatedAt(r.getCreatedAt());
        e.setUpdatedAt(r.getUpdatedAt());
        return e;
    }

    @Override
    public Restaurante guardar(Restaurante r) {
        return toDomain(jpa.save(toEntity(r)));
    }

    @Override
    public Optional<Restaurante> porId(UUID id) {
        return jpa.findById(id).map(RestauranteRepositorioOutputAdapter::toDomain);
    }

    @Override
    public Page<Restaurante> buscar(String q, UUID hotelId, Boolean enabled, Pageable p) {
        // Si no hay texto libre, usa derivados (más simples y legibles)
        if (q == null || q.isBlank()) {
            if (hotelId == null && enabled == null) {
                return jpa.findAll(p).map(RestauranteRepositorioOutputAdapter::toDomain);
            }
            if (hotelId != null && enabled == null) {
                return jpa.findByHotelId(hotelId, p).map(RestauranteRepositorioOutputAdapter::toDomain);
            }
            if (hotelId == null) {
                return jpa.findByEnabled(enabled, p).map(RestauranteRepositorioOutputAdapter::toDomain);
            }
            return jpa.findByHotelIdAndEnabled(hotelId, enabled, p)
                    .map(RestauranteRepositorioOutputAdapter::toDomain);
        }

        // Con 'q' usa la consulta flexible
        var qq = q.trim();
        return jpa.search(qq, hotelId, enabled, p)
                .map(RestauranteRepositorioOutputAdapter::toDomain);
    }

    @Override
    public boolean existsNombreInHotel(String nombre, UUID hotelId, UUID excludeId) {
        var opt = (hotelId == null)
                ? jpa.findFirstByHotelIdIsNullAndNombreIgnoreCase(nombre)
                : jpa.findFirstByHotelIdAndNombreIgnoreCase(hotelId, nombre);

        // true si existe OTRO registro con ese nombre (no el mismo al actualizar)
        return opt.filter(e -> !e.getId().equals(excludeId)).isPresent();
    }

// (este método adicional no es necesario si ya usas 'buscar', pero si lo quieres mantener:)
    public Page<Restaurante> listar(String q, UUID hotelId, Boolean enabled, Pageable pageable) {
        var qq = (q != null && !q.isBlank()) ? q.trim() : null;
        return jpa.search(qq, hotelId, enabled, pageable)
                .map(RestauranteRepositorioOutputAdapter::toDomain); // <-- clase::
    }
}
