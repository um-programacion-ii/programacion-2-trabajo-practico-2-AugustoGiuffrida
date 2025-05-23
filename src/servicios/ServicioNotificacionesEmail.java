package src.servicios;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {
    private String emailDestino;

    public ServicioNotificacionesEmail(){
        this.emailDestino = null;
    }

    @Override
    public  void establecerDestinatario(String contacto){
        this.emailDestino = contacto;
    }

    @Override
    public String getTipo() {
        return "email";
    }


    @Override
    public String enviarNotificacion(String mensaje){
        if (emailDestino == null || emailDestino.isBlank()) {
            return "Error: destinatario de email no definido.";
        }
        return  "Enviando email a " + emailDestino + ": " + mensaje;
    }
}
