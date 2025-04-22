package src.modelos;

import src.enums.NivelUrgencia;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Recordatorio {
    private String mensaje;
    private NivelUrgencia urgencia;
    private Date fecha;
    private boolean leido;
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Recordatorio(String mensaje, NivelUrgencia urgencia, Date fecha) {
        this.mensaje = mensaje;
        this.urgencia = urgencia;
        this.fecha = fecha;
        this.leido = false;
    }

    public String getMensaje() {
        return mensaje;
    }

    public NivelUrgencia getUrgencia() {
        return urgencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isLeido() {
        return leido;
    }

    public void marcarComoLeido() {
        this.leido = true;
    }

    @Override
    public String toString() {
        String fechaFormateada = FORMATO_FECHA.format(fecha);
        String estadoLectura = leido ? "✓" : "○";
        return urgencia.formatear("[" + urgencia.getEtiqueta() + "] " + mensaje + " (" + fechaFormateada + ") " + estadoLectura);
    }
}
