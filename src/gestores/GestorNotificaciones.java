package src.gestores;

import src.servicios.ServicioNotificaciones;

import java.util.ArrayList;
import java.util.List;

public class GestorNotificaciones {
    private List<ServicioNotificaciones> servicios;

    public GestorNotificaciones(){
        this.servicios = new ArrayList<>();
    }

    public void agregarServicio(ServicioNotificaciones servicio){
        servicios.add(servicio);
    }

    public void notificar(String contacto ,String mensaje){
        for (ServicioNotificaciones servicio : servicios){
            servicio.establecerDestinatario(contacto);
            String resultado = servicio.enviarNotificacion(mensaje);
            System.out.println(resultado);
        }
    }
}
