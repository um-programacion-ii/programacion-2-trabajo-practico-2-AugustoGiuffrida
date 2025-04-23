package src.alertas;

import src.enums.estadoPrestamo;
import src.interfaces.Renovable;
import src.modelos.Prestamo;
import src.gestores.GestorPrestamos;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AlertaVencimiento {
    private List<Prestamo> prestamos;
    private GestorPrestamos gestorPrestamos;

    public AlertaVencimiento(List<Prestamo> prestamos, GestorPrestamos gestorPrestamos) {
        this.prestamos = prestamos;
        this.gestorPrestamos = gestorPrestamos;
    }

    public void verificarAlertas(Scanner scanner) {
        Date hoy = new Date();

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getEstado() != estadoPrestamo.DEVUELTO) {
                long diasRestantes = calcularDiferenciaEnDias(hoy, prestamo.getFechaVencimiento());

                if (diasRestantes <= 1) {
                    System.out.println("\n⚠️ ALERTA DE VENCIMIENTO ⚠️");
                    System.out.println("Recurso: '" + prestamo.getRecurso().getTitulo() + "'");
                    System.out.println("Vence el: " + prestamo.getFechaVencimiento());

                    if (prestamo.getRecurso() instanceof Renovable) {
                        System.out.print("¿Desea renovar el préstamo? (s/n): ");
                        String opcion = scanner.nextLine();

                        if (opcion.equalsIgnoreCase("s")) {
                            if (gestorPrestamos.puedeRenovarse(prestamo)) {
                                gestorPrestamos.realizarRenovacion(prestamo);
                            }
                        }
                    }
                }
            }
        }
    }

    private long calcularDiferenciaEnDias(Date fecha1, Date fecha2) {
        long diferenciaMillis = fecha2.getTime() - fecha1.getTime();
        return TimeUnit.MILLISECONDS.toDays(diferenciaMillis);
    }
}
