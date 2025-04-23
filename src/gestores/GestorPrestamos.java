package src.gestores;

import src.alertas.AlertaDisponibilidad;
import src.enums.estadoPrestamo;
import src.exepciones.RecursoNoDisponibleException;
import src.exepciones.UsuarioNoEncontradoException;
import src.interfaces.Prestable;
import src.interfaces.Renovable;
import src.modelos.Prestamo;
import src.modelos.RecursoDigital;
import src.modelos.Reserva;
import src.modelos.Usuario;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestorPrestamos {
    private static int contadorPrestamos = 1;
    private List<Prestamo> prestamos;
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private GestorReserva gestorReserva;
    private GestorNotificaciones gestorNotificaciones;

    public GestorPrestamos(int contadorPrestamos, List<Prestamo> prestamos, GestorRecursos gestorRecursos,
    GestorUsuario gestorUsuario, GestorReserva gestorReserva, GestorNotificaciones gestorNotificaciones){

        GestorPrestamos.contadorPrestamos = contadorPrestamos;
        this.prestamos = prestamos;
        this.gestorUsuario = gestorUsuario;
        this.gestorRecursos = gestorRecursos;
        this.gestorReserva = gestorReserva;
        this.gestorNotificaciones = gestorNotificaciones;
    }

    public void listarPrestamos(){
        for (Prestamo prestamo : prestamos){
            System.out.println("- " + prestamo);
        }
    }

    public synchronized void devolverPrestamo(Scanner scanner){
        System.out.print("Ingrese ID del prestamo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean encontrado = false;

        for (Prestamo prestamo : prestamos) {
            if (id == prestamo.getId()){
                encontrado = true;
                System.out.println("Hilo " + Thread.currentThread().getName() + " está intentando devolver un préstamo...");
                if (estadoPrestamo.ACTIVO == prestamo.getEstado()) {
                    prestamo.setEstado(estadoPrestamo.DEVUELTO);
                    //Devolucion automatica
                    manejarRecursoDevuelto(prestamo,scanner);
                } else {
                    System.out.println("El prestamo" + prestamo + " ya ha sido devuelto");
                }
                break;
            }
        } if (!encontrado){
            System.out.println("No se encontraron prestamos la id: " +id);
        }
    }

    private void manejarRecursoDevuelto(Prestamo prestamo, Scanner scanner) {
        RecursoDigital recursoDevuelto = prestamo.getRecurso();

        if (recursoDevuelto instanceof Prestable prestable) {
            Reserva siguienteReserva = gestorReserva.obtenerSiguienteReservaPorRecurso(recursoDevuelto);

            if (siguienteReserva != null) {
                Usuario usuario = siguienteReserva.getUsuario();
                AlertaDisponibilidad alerta = new AlertaDisponibilidad(usuario, recursoDevuelto);
                alerta.notificar();

                if (alerta.isConfirmada()) {
                    asignarReservaComoPrestamo(siguienteReserva);
                    System.out.println("Préstamo realizado al usuario con reserva.");
                } else {
                    System.out.println("El usuario rechazó el préstamo. Se notificará al siguiente en la cola.");
                    siguienteReserva.cancelar();

                    manejarRecursoDevuelto(prestamo, scanner);
                }

            } else {
                prestable.marcarComoDisponible();
                System.out.println("Recurso devuelto y marcado como disponible.");
            }
        }
    }


    public void asignarReservaComoPrestamo(Reserva reserva) {
        Prestamo nuevoPrestamo = crearPrestamo(reserva.getUsuario(), reserva.getRecurso());
        guardarPrestamo(nuevoPrestamo);

        if (reserva.getRecurso() instanceof Prestable prestable) {
            prestable.marcarComoNoDisponible();
        }
        reserva.completar();
    }

    public boolean puedeRenovarse(Prestamo prestamo) {
        if (prestamo.getEstado() != estadoPrestamo.ACTIVO) {
            System.out.println("El préstamo no está activo y no puede renovarse.");
            return false;
        }

        RecursoDigital recurso = prestamo.getRecurso();

        if (!(recurso instanceof Renovable)) {
            System.out.println("Este recurso no permite renovaciones.");
            return false;
        }

        Renovable renovable = (Renovable) recurso;

        if (!renovable.permiteRenovacion()) {
            System.out.println("Este recurso no se puede renovar actualmente.");
            return false;
        }

        return true;
    }

    public void realizarRenovacion(Prestamo prestamo) {
        Date nuevaFecha = new Date(prestamo.getFechaVencimiento().getTime() + (7L * 24 * 60 * 60 * 1000));
        prestamo.setFechaVencimiento(nuevaFecha);

        gestorNotificaciones.notificar(prestamo.getUsuario().getEmail(), "Se ha renovado el préstamo del recurso: " + prestamo.getRecurso().getTitulo() + ". Nueva fecha de vencimiento: " + nuevaFecha);

        System.out.println("Préstamo renovado exitosamente.");
    }


    public synchronized void renovarPrestamo(Scanner scanner) {
        System.out.print("Ingrese ID del préstamo a renovar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getId() == id) {
                if (puedeRenovarse(prestamo)) {
                    realizarRenovacion(prestamo);
                }
                return;
            }
        }

        System.out.println("No se encontró ningún préstamo con el ID: " + id);
    }



    public synchronized void registrarPrestamo(Scanner scanner){
        while (true){
            try {
                Usuario usuario = solicitarUsuario(scanner);
                RecursoDigital recurso = solicitarRecurso(scanner);

                validarRecurso(recurso);

                Prestamo prestamo = crearPrestamo(usuario, recurso);
                System.out.println("Hilo " + Thread.currentThread().getName() + " está intentando registrar un préstamo...");
                guardarPrestamo(prestamo);

                gestorNotificaciones.notificar(usuario.getEmail(), "Préstamo registrado: " + recurso.getTitulo() + " a " + usuario.getEmail());
                if (recurso instanceof Prestable prestable) {
                    prestable.marcarComoNoDisponible();
                }
                break;

            } catch (UsuarioNoEncontradoException | RecursoNoDisponibleException | IllegalArgumentException error){
                System.out.println("Error: " + error.getMessage());
            } catch (InputMismatchException error){
                System.out.println("Entrada invalida: " + error.getMessage());
                scanner.nextLine();
            }
        }
    }

    public Usuario solicitarUsuario(Scanner scanner) throws UsuarioNoEncontradoException{
        System.out.print("Ingrese el Email del usuario: ");
        String email = scanner.nextLine();
        return gestorUsuario.obtenerUsuarioPorEmail(email);
    }

    public RecursoDigital solicitarRecurso(Scanner scanner) throws RecursoNoDisponibleException{
        System.out.print("Ingrese el titulo del recurso: ");
        String titulo = scanner.nextLine();
        return gestorRecursos.obtenerRecursoPorTitulo(titulo);
    }

    public void validarRecurso(RecursoDigital recurso){
        if(!(recurso instanceof Prestable)){
            throw new IllegalArgumentException("El recurso no es prestable.");
        }
        if (!(recurso.estaDisponible())){
            throw new IllegalArgumentException("El recurso no esta disponible.");
        }
    }

    public Prestamo crearPrestamo(Usuario usuario, RecursoDigital recurso){
        Date fechaInicio = new Date();
        Date fechaVencimiento = new Date(fechaInicio.getTime() + (7L * 24 * 60 * 60 * 1000));

        return new Prestamo(contadorPrestamos++, recurso, usuario, fechaInicio, fechaVencimiento, estadoPrestamo.ACTIVO);
    }

    public synchronized void guardarPrestamo(Prestamo prestamo){
        System.out.println("Hilo " + Thread.currentThread().getName() + " guardó el préstamo con ID: " + prestamo.getId());
        prestamos.add(prestamo);
    }

}
