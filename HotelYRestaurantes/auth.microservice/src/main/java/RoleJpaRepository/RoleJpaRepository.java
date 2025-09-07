/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RoleJpaRepository;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.infrastructure.outputadapters.persistence.entity.RoleDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleDbEntity, String> {

    Optional<RoleDbEntity> findByName(String name);
}
