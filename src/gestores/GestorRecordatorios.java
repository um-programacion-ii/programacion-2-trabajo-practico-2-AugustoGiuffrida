package src.gestores;

import src.modelos.Recordatorio;
import src.enums.NivelUrgencia;

import java.util.*;

public class GestorRecordatorios {
    private List<Recordatorio> historial;
    private Map<NivelUrgencia, Boolean> preferenciasNotificacion;

    public GestorRecordatorios() {
        this.historial = new ArrayList<>();
        this.preferenciasNotificacion = new EnumMap<>(NivelUrgencia.class);
        for (NivelUrgencia nivel : NivelUrgencia.values()) {
            preferenciasNotificacion.put(nivel, true); // todos habilitados por defecto
        }
    }

    public void generarRecordatorio(String mensaje, NivelUrgencia urgencia) {
        Recordatorio r = new Recordatorio(mensaje, urgencia, new Date());
        historial.add(r);

        if (preferenciasNotificacion.getOrDefault(urgencia, true)) {
            mostrarRecordatorio(r);
        }
    }

    public void mostrarRecordatorio(Recordatorio r) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║              RECORDATORIO                ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ " + r);
        System.out.println("╚══════════════════════════════════════════╝");
        r.marcarComoLeido();
    }

    public void mostrarHistorial() {
        mostrarHistorialFiltrado(null);
    }

    public void mostrarHistorialFiltrado(NivelUrgencia filtroUrgencia) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║             HISTORIAL DE RECORDATORIOS                ║");
        System.out.println("╠═══════════════════════════════════════════════════════╣");

        if (historial.isEmpty()) {
            System.out.println("║ No hay recordatorios registrados                      ║");
        } else {
            int contador = 0;
            for (Recordatorio r : historial) {
                if (filtroUrgencia == null || r.getUrgencia() == filtroUrgencia) {
                    System.out.println("║ " + (++contador) + ". " + r);
                }
            }
        }

        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }

    public void configurarPreferencia(NivelUrgencia nivel, boolean habilitado) {
        preferenciasNotificacion.put(nivel, habilitado);
        System.out.println("Preferencia actualizada: " + nivel.getEtiqueta() +
                (habilitado ? " HABILITADO" : " DESHABILITADO"));
    }

    public void mostrarEstadisticas() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          ESTADÍSTICAS                 ║");
        System.out.println("╠═══════════════════════════════════════╣");

        Map<NivelUrgencia, Integer> conteo = new EnumMap<>(NivelUrgencia.class);
        for (NivelUrgencia nivel : NivelUrgencia.values()) {
            conteo.put(nivel, 0);
        }

        for (Recordatorio r : historial) {
            conteo.put(r.getUrgencia(), conteo.get(r.getUrgencia()) + 1);
        }

        int total = historial.size();
        for (NivelUrgencia nivel : NivelUrgencia.values()) {
            int cantidad = conteo.get(nivel);
            double porcentaje = total > 0 ? (cantidad * 100.0 / total) : 0;
            System.out.printf("║ %s: %d (%.1f%%)%n", nivel.getEtiqueta(), cantidad, porcentaje);
        }

        System.out.println("║ Total de recordatorios: " + total);
        System.out.println("╚═══════════════════════════════════════╝");
    }
}
