package src.gestores;

import src.servicios.ServicioNotificaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GestorNotificaciones {
    private List<ServicioNotificaciones> servicios;
    private ExecutorService executor;

    public GestorNotificaciones(){
        this.servicios = new ArrayList<>();
        this.executor = Executors.newFixedThreadPool(5);
    }

    public void agregarServicio(ServicioNotificaciones servicio){
        servicios.add(servicio);
    }

    public void notificar(String contacto, String mensaje) {
        for (ServicioNotificaciones servicio : servicios) {
            System.out.println("Enviando " + servicio.getTipo() + " a " + contacto + ": " + mensaje);
            executor.submit(() -> {
                servicio.establecerDestinatario(contacto);
                servicio.enviarNotificacion(mensaje);
            });
        }
    }

    public void cerrar(){
        executor.shutdown();
    }

}
