package src.gestores;

import src.enums.estadoPrestamo;
import src.exepciones.RecursoNoDisponibleException;
import src.exepciones.UsuarioNoEncontradoException;
import src.interfaces.Prestable;
import src.modelos.Prestamo;
import src.modelos.RecursoDigital;
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

    public GestorPrestamos(int contadorPrestamos, List<Prestamo> prestamos, GestorRecursos gestorRecursos, GestorUsuario gestorUsuario){
        GestorPrestamos.contadorPrestamos = contadorPrestamos;
        this.prestamos = prestamos;
        this.gestorUsuario = gestorUsuario;
        this.gestorRecursos = gestorRecursos;
    }

    public void listarPrestamos(){
        for (Prestamo prestamo : prestamos){
            System.out.println("- " + prestamo);
        }
    }

    public void devolverPrestamo(Scanner scanner){
        System.out.println("Ingrese ID del prestamo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean encontrado = false;

        for (Prestamo prestamo : prestamos) {
            if (id == prestamo.getId()){
                encontrado = true;
                if (estadoPrestamo.ACTIVO == prestamo.getEstado()) {
                    prestamo.setEstado(estadoPrestamo.DEVUELTO);
                    System.out.println("Recurso devuelto correctamente.");
                } else {
                    System.out.println("El prestamo" + prestamo + " ya ha sido devuelto");
                }
                break;
            }
        } if (!encontrado){
            System.out.println("No se encontraron prestamos la id: " +id);
        }
    }


    public void registrarPrestamo(Scanner scanner){
        while (true){
            try {
                Usuario usuario = solicitarUsuario(scanner);
                RecursoDigital recurso = solicitarRecurso(scanner);

                validarRecurso(recurso);

                Prestamo prestamo = crearPrestamo(usuario, recurso);
                guardarPrestamo(prestamo);

                System.out.println("Préstamo registrado con éxito.");
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
        System.out.println("Ingrese el Email del usuario: ");
        String email = scanner.nextLine();
        return gestorUsuario.obtenerUsuarioPorEmail(email);
    }

    public RecursoDigital solicitarRecurso(Scanner scanner) throws RecursoNoDisponibleException{
        System.out.println("Ingrese el titulo del recurso: ");
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
