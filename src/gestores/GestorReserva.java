package src.gestores;

import src.enums.estadoReserva;
import src.enums.prioridadReserva;
import src.exepciones.RecursoNoDisponibleException;
import src.exepciones.UsuarioNoEncontradoException;
import src.modelos.RecursoDigital;
import src.modelos.Reserva;
import src.modelos.Usuario;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class GestorReserva {
    private BlockingQueue<Reserva> colaReservas = new PriorityBlockingQueue<>();
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private static int contadorReserva = 1;

    public GestorReserva(GestorRecursos gestorRecursos, GestorUsuario gestorUsuario , int contadorReserva){
        this.gestorUsuario = gestorUsuario;
        this.gestorRecursos = gestorRecursos;
        GestorReserva.contadorReserva = contadorReserva;
    }

    public void registrarReserva(prioridadReserva prioridad, Scanner scanner){
        while (true) {
            try {
                Usuario usuario = solicitarUsuario(scanner);
                RecursoDigital recurso = solicitarRecurso(scanner);

                validarReserva(usuario, recurso);

                Reserva reserva = crearReserva(usuario, recurso, prioridad);
                guardarReserva(reserva);

                System.out.println("Reserva registrada con éxito.");
                break;

            } catch (UsuarioNoEncontradoException | RecursoNoDisponibleException | IllegalArgumentException error){
                System.out.println("Error: " + error.getMessage());
            } catch (InputMismatchException error){
                System.out.println("Entrada inválida. Intente nuevamente.");
                scanner.nextLine();
            }
        }
    }

    private void validarReserva(Usuario usuario, RecursoDigital recurso){
        if (recurso.estaDisponible()) {
            throw new IllegalArgumentException("El recurso está disponible. No se puede reservar.");
        }

        for (Reserva reserva : colaReservas) {
            if (reserva.getUsuario().equals(usuario) &&
                    reserva.getRecurso().equals(recurso) &&
                    reserva.getEstado() == estadoReserva.ACTIVA) {
                throw new IllegalArgumentException("Ya existe una reserva activa para este usuario y recurso.");
            }
        }
    }

    private Reserva crearReserva(Usuario usuario, RecursoDigital recurso, prioridadReserva prioridad){
        int id = contadorReserva++;
        Date fechaSolicitud = new Date();
        return new Reserva(id, usuario, recurso, fechaSolicitud, prioridad, estadoReserva.ACTIVA);
    }

    private void guardarReserva(Reserva reserva){
        try {
            colaReservas.put(reserva);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo interrumpido al agregar reserva.");
        }
    }

    public Usuario solicitarUsuario(Scanner scanner) throws UsuarioNoEncontradoException {
        System.out.print("Ingrese el Email del usuario: ");
        String email = scanner.nextLine();
        return gestorUsuario.obtenerUsuarioPorEmail(email);
    }

    public RecursoDigital solicitarRecurso(Scanner scanner) throws RecursoNoDisponibleException {
        System.out.print("Ingrese el título del recurso: ");
        String titulo = scanner.nextLine();
        return gestorRecursos.obtenerRecursoPorTitulo(titulo);
    }

    public Reserva obtenerSiguienteReservaPorRecurso(RecursoDigital recurso) {
        for (Reserva reserva : colaReservas) {
            if (reserva.getRecurso().equals(recurso) && reserva.getEstado().equals(estadoReserva.ACTIVA)) {
                return reserva;
            }
        }
        return null;
    }


    public void mostrarReservas(){
        if (colaReservas.isEmpty()) {
            System.out.println("La cola de reservas está vacía.");
            return;
        }

        for (Reserva reserva : colaReservas){
            System.out.println("- " + reserva);
        }
    }


    public void cancelarReserva(Scanner scanner){
        System.out.println("Ingrese ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean encontrada = false;
        for(Reserva reserva: colaReservas){
            if(reserva.getId() == id && reserva.getEstado().equals(estadoReserva.ACTIVA)){
                reserva.cancelar();
                System.out.println("La reserva se cancelo correctamente");
                encontrada = true;
            }
        }
        if (!encontrada) {
            System.out.println("No se encontró una reserva activa con ese ID.");
        }
    }

    public void completarReserva(Scanner scanner){
        System.out.println("Ingrese ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean encontrada = false;
        for(Reserva reserva: colaReservas){
            if(reserva.getId() == id && reserva.getEstado().equals(estadoReserva.ACTIVA)){
                reserva.completar();
                System.out.println("La reserva se completo correctamente");
                encontrada = true;
            }
        }
        if (!encontrada) {
            System.out.println("No se encontró una reserva activa con ese ID.");
        }
    }

}
