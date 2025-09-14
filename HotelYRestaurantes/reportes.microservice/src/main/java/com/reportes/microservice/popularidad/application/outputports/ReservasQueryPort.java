// application/outputports/ReservasQueryPort.java
package com.reportes.microservice.popularidad.application.outputports;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservasQueryPort {
  // Snapshot mínimo
  public static final class ReservaSnapshot {
    private final UUID id;
    private final UUID hotelId;
    private final UUID habitacionId;
    private final UUID clienteId;
    private final LocalDate entrada;
    private final LocalDate salida;

    public ReservaSnapshot(UUID id, UUID hotelId, UUID habitacionId, UUID clienteId,
                           LocalDate entrada, LocalDate salida) {
      this.id = id; this.hotelId = hotelId; this.habitacionId = habitacionId;
      this.clienteId = clienteId; this.entrada = entrada; this.salida = salida;
    }
    public UUID id() { return id; }
    public UUID hotelId() { return hotelId; }
    public UUID habitacionId() { return habitacionId; }
    public UUID clienteId() { return clienteId; }
    public LocalDate entrada() { return entrada; }
    public LocalDate salida() { return salida; }
  }

  List<ReservaSnapshot> listar(LocalDate desde, LocalDate hasta, UUID hotelId);
}