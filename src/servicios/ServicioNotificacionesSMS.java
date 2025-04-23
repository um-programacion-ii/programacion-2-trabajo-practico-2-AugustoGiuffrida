package src.servicios;

public class ServicioNotificacionesSMS implements ServicioNotificaciones{
    private String numDestino;

    public ServicioNotificacionesSMS(){
        this.numDestino = null;
    }

    @Override
    public  void establecerDestinatario(String contacto){
        this.numDestino = contacto;
    }

    @Override
    public String getTipo() {
        return "sms";
    }

    @Override
    public String enviarNotificacion(String mensaje){
        if (numDestino == null || numDestino.isBlank()) {
            return "Error: destinatario de mensaje no definido.";
        }
        return  "Enviando mensaje a " + numDestino + ": " + mensaje;
    }
}
