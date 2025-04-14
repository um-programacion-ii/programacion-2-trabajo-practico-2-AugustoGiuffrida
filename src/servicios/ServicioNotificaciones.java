package src.servicios;

public interface ServicioNotificaciones {
    String enviarNotificacion(String mensaje);
    void establecerDestinatario(String contacto);
}
