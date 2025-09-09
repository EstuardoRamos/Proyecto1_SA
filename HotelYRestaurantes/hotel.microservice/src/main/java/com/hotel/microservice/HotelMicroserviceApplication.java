package com.hotel.microservice;

import com.hotel.microservice.facturacion.application.inputports.AnularFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.EmitirFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.ListarFacturasHotelInputPort;
import com.hotel.microservice.facturacion.application.inputports.ObtenerFacturaHotelInputPort;
import com.hotel.microservice.facturacion.application.outputports.persistence.FacturaHotelRepositorioPort;
import com.hotel.microservice.facturacion.application.outputports.query.ReservaHotelQueryPort;
import com.hotel.microservice.facturacion.application.usecases.AnularFacturaHotelUseCase;
import com.hotel.microservice.facturacion.application.usecases.EmitirFacturaHotelUseCase;
import com.hotel.microservice.facturacion.application.usecases.ListarFacturasHotelUseCase;
import com.hotel.microservice.facturacion.application.usecases.ObtenerFacturaHotelUseCase;
import com.hotel.microservice.habitacion.application.inputports.ActualizarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.CambiarEstadoHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.CrearHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.EliminarHabitacionInputPort;
import com.hotel.microservice.habitacion.application.inputports.ListarHabitacionesInputPort;
import com.hotel.microservice.habitacion.application.inputports.ObtenerHabitacionInputPort;
import com.hotel.microservice.habitacion.application.outputports.HabitacionRepositorioPort;
import com.hotel.microservice.habitacion.application.usecases.ActualizarHabitacionUseCase;
import com.hotel.microservice.habitacion.application.usecases.CambiarEstadoHabitacionUseCase;
import com.hotel.microservice.habitacion.application.usecases.CrearHabitacionUseCase;
import com.hotel.microservice.habitacion.application.usecases.EliminarHabitacionUseCase;
import com.hotel.microservice.habitacion.application.usecases.ListarHabitacionesUseCase;
import com.hotel.microservice.habitacion.application.usecases.ObtenerHabitacionUseCase;
import com.hotel.microservice.hotel.application.inputports.*;
import com.hotel.microservice.hotel.application.outputports.HotelRepositorioPort;
import com.hotel.microservice.hotel.application.usecases.*;
import com.hotel.microservice.reserva.application.inputports.CancelarReservaInputPort;
import com.hotel.microservice.reserva.application.inputports.CheckInInputPort;
import com.hotel.microservice.reserva.application.inputports.CheckOutInputPort;
import com.hotel.microservice.reserva.application.inputports.CrearReservaInputPort;
import com.hotel.microservice.reserva.application.inputports.ListarReservasInputPort;
import com.hotel.microservice.reserva.application.inputports.ObtenerReservaInputPort;
import com.hotel.microservice.reserva.application.outputports.HabitacionQueryPort;
import com.hotel.microservice.reserva.application.outputports.ReservaRepositorioPort;
import com.hotel.microservice.reserva.application.usecases.CancelarReservaUseCase;
import com.hotel.microservice.reserva.application.usecases.CheckInUseCase;
import com.hotel.microservice.reserva.application.usecases.CheckOutUseCase;
import com.hotel.microservice.reserva.application.usecases.CrearReservaUseCase;
import com.hotel.microservice.reserva.application.usecases.ListarReservasUseCase;
import com.hotel.microservice.reserva.application.usecases.ObtenerReservaUseCase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HotelMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelMicroserviceApplication.class, args);
    }

    // ---- Wiring de casos de uso (public, no static, con retorno correcto) ----
    @Bean
    public CrearHotelInputPort crearHotel(HotelRepositorioPort repo) {
        return new CrearHotelUseCase(repo);
    }

    @Bean
    public ListarHotelesInputPort listarHoteles(HotelRepositorioPort repo) {
        return new ListarHotelesUseCase(repo);
    }

    @Bean
    public ObtenerHotelInputPort obtenerHotel(HotelRepositorioPort repo) {
        return new ObtenerHotelUseCase(repo);
    }

    @Bean
    public ActualizarHotelInputPort actualizarHotel(HotelRepositorioPort repo) {
        return new ActualizarHotelUseCase(repo);
    }

    @Bean
    public EliminarHotelInputPort eliminarHotel(HotelRepositorioPort repo) {
        return new EliminarHotelUseCase(repo);
    }

    @Bean
    public CrearHabitacionInputPort crearHabitacion(HabitacionRepositorioPort repo) {
        return new CrearHabitacionUseCase(repo);
    }

    @Bean
    public ListarHabitacionesInputPort listarHabitaciones(HabitacionRepositorioPort repo) {
        return new ListarHabitacionesUseCase(repo);
    }

    @Bean
    public ObtenerHabitacionInputPort obtenerHabitacion(HabitacionRepositorioPort repo) {
        return new ObtenerHabitacionUseCase(repo);
    }

    @Bean
    public ActualizarHabitacionInputPort actualizarHabitacion(HabitacionRepositorioPort repo) {
        return new ActualizarHabitacionUseCase(repo);
    }

    @Bean
    public EliminarHabitacionInputPort eliminarHabitacion(HabitacionRepositorioPort repo) {
        return new EliminarHabitacionUseCase(repo);
    }

    @Bean
    public CambiarEstadoHabitacionInputPort cambiarEstadoHabitacion(HabitacionRepositorioPort repo) {
        return new CambiarEstadoHabitacionUseCase(repo);
    }

    //Habitacion
    @Bean
    public CrearReservaInputPort crearReserva(ReservaRepositorioPort repo, HabitacionQueryPort rooms) {
        return new CrearReservaUseCase(repo, rooms);
    }

    @Bean
    public ListarReservasInputPort listarReservas(ReservaRepositorioPort repo) {
        return new ListarReservasUseCase(repo);
    }

    @Bean
    public ObtenerReservaInputPort obtenerReserva(ReservaRepositorioPort repo) {
        return new ObtenerReservaUseCase(repo);
    }

    @Bean
    public CancelarReservaInputPort cancelarReserva(ReservaRepositorioPort repo) {
        return new CancelarReservaUseCase(repo);
    }

    @Bean
    public CheckInInputPort checkInReserva(ReservaRepositorioPort repo) {
        return new CheckInUseCase(repo);
    }

    //@Bean
    //public CheckOutInputPort checkOutReserva(ReservaRepositorioPort repo) {
    //  return new CheckOutUseCase(repo);
    //}
    @Bean
    public CheckOutInputPort checkOut(ReservaRepositorioPort reservas,
            EmitirFacturaHotelInputPort emitir) {
        return new CheckOutUseCase(reservas, emitir);
    }

    //Facturas
    @Bean
    public EmitirFacturaHotelInputPort emitirFacturaHotel(FacturaHotelRepositorioPort repo,
            ReservaHotelQueryPort reservas) {
        return new EmitirFacturaHotelUseCase(repo, reservas);
    }

    @Bean
    public ObtenerFacturaHotelInputPort obtenerFacturaHotel(FacturaHotelRepositorioPort repo) {
        return new ObtenerFacturaHotelUseCase(repo);
    }

    @Bean
    public ListarFacturasHotelInputPort listarFacturasHotel(FacturaHotelRepositorioPort repo) {
        return new ListarFacturasHotelUseCase(repo);
    }

    @Bean
    public AnularFacturaHotelInputPort anularFacturaHotel(FacturaHotelRepositorioPort repo) {
        return new AnularFacturaHotelUseCase(repo);
    }
}
