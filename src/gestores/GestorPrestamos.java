package src.gestores;

import src.enums.estadoPrestamo;
import src.exepciones.RecursoNoDisponibleException;
import src.exepciones.UsuarioNoEncontradoException;
import src.interfaces.Prestable;
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

    public GestorPrestamos(int contadorPrestamos, List<Prestamo> prestamos, GestorRecursos gestorRecursos, GestorUsuario gestorUsuario, GestorReserva gestorReserva, GestorNotificaciones gestorNotificaciones){
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

    public void devolverPrestamo(Scanner scanner){
        System.out.print("Ingrese ID del prestamo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean encontrado = false;

        for (Prestamo prestamo : prestamos) {
            if (id == prestamo.getId()){
                encontrado = true;
                if (estadoPrestamo.ACTIVO == prestamo.getEstado()) {
                    prestamo.setEstado(estadoPrestamo.DEVUELTO);
                    manejarRecursoDevuelto(prestamo);
                } else {
                    System.out.println("El prestamo" + prestamo + " ya ha sido devuelto");
                }
                break;
            }
        } if (!encontrado){
            System.out.println("No se encontraron prestamos la id: " +id);
        }
    }

    private void manejarRecursoDevuelto(Prestamo prestamo) {
        RecursoDigital recursoDevuelto = prestamo.getRecurso();

        if (recursoDevuelto instanceof Prestable prestable) {
            Reserva siguienteReserva = gestorReserva.obtenerSiguienteReservaPorRecurso(recursoDevuelto);

            if (siguienteReserva != null) {
                asignarReservaComoPrestamo(siguienteReserva);
                System.out.println("El recurso fue prestado automáticamente al usuario con reserva prioritaria.");
            } else {
                prestable.marcarComoDisponible();
                System.out.println("Recurso devuelto y marcado como disponible.");
            }
        }
    }

    private void asignarReservaComoPrestamo(Reserva reserva) {
        Prestamo nuevoPrestamo = crearPrestamo(reserva.getUsuario(), reserva.getRecurso());
        guardarPrestamo(nuevoPrestamo);

        if (reserva.getRecurso() instanceof Prestable prestable) {
            prestable.marcarComoNoDisponible();
        }
        reserva.completar();
    }

    public void registrarPrestamo(Scanner scanner){
        while (true){
            try {
                Usuario usuario = solicitarUsuario(scanner);
                RecursoDigital recurso = solicitarRecurso(scanner);

                validarRecurso(recurso);

                Prestamo prestamo = crearPrestamo(usuario, recurso);
                guardarPrestamo(prestamo);
                gestorNotificaciones.notificar("Préstamo registrado: " + recurso.getTitulo() + " a " + usuario.getEmail());
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

    public void guardarPrestamo(Prestamo prestamo){
        prestamos.add(prestamo);
    }

}
