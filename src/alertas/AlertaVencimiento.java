package src.alertas;

import src.gestores.GestorPrestamos;
import src.modelos.Prestamo;
import src.interfaces.Renovable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class AlertaVencimiento implements Runnable{
    private List<Prestamo> prestamos;
    private boolean activo = true;
    private GestorPrestamos gestorPrestamos;

    public AlertaVencimiento(List<Prestamo> prestamos, GestorPrestamos gestorPrestamos){
        this.prestamos = prestamos;
        this.gestorPrestamos = gestorPrestamos;
    }

    public void detener(){
        this.activo = false;
    }

    public void run(){
        while (activo){
            LocalDate hoy = LocalDate.now();
            for(Prestamo prestamo : prestamos){
                Date fechaRaw = prestamo.getFechaVencimiento();

                LocalDate fechaVencimiento = fechaRaw.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                if(fechaVencimiento.equals(hoy)){
                    System.out.println("Prestamo vence hoy");
                } else if(fechaVencimiento.minusDays(1).equals(hoy)){
                    System.out.println("Prestamo vence mañana");
                }
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private void mostrarAlerta(String mensaje, Prestamo prestamo) {
        System.out.println("\n========================");
        System.out.println(mensaje);
        System.out.println("Recurso: " + prestamo.getRecurso().getTitulo());
        System.out.println("Usuario: " + prestamo.getUsuario().getNombre());
        System.out.println("Fecha de devolución: " + prestamo.getFechaVencimiento());

        if(prestamo.getRecurso() instanceof Renovable){
            System.out.println("¿Desea renovar este recurso? (s/n)");
            Scanner scanner = new Scanner(System.in);
            String respuesta = scanner.nextLine();

            if(respuesta.equalsIgnoreCase("s")){
                if (gestorPrestamos.puedeRenovarse(prestamo)) {
                    gestorPrestamos.realizarRenovacion(prestamo);
                }
            }
        }
        System.out.println("========================\n");
    }


}

