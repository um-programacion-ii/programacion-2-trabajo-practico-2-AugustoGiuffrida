package src.consola;
import src.enums.prioridadReserva;
import src.gestores.GestorPrestamos;
import src.gestores.GestorReserva;
import src.modelos.*;
import src.gestores.GestorUsuario;
import src.gestores.GestorRecursos;
import src.servicios.ServicioNotificacionesEmail;
import src.enums.categoriaRecurso;

import java.util.*;

public class Consola {
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private GestorPrestamos gestorPrestamos;
    private GestorReserva gestorReserva;
    private ServicioNotificacionesEmail servicioNotificacionesEmail;
    private Scanner scanner;

    public Consola(){
        Map<String,Usuario> usuarios = new HashMap<>();
        List<RecursoDigital> recursoDigitalList = new ArrayList<>();
        List<Prestamo> prestamos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.servicioNotificacionesEmail = new ServicioNotificacionesEmail("");
        this.gestorRecursos = new GestorRecursos(recursoDigitalList);
        this.gestorUsuario = new GestorUsuario(usuarios, servicioNotificacionesEmail);
        this.gestorReserva = new GestorReserva(gestorRecursos,gestorUsuario, 1);
        this.gestorPrestamos = new GestorPrestamos(1, prestamos, gestorRecursos, gestorUsuario, gestorReserva);
    }

    public void iniciar(){
        boolean salir = false;

        while (!salir){
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Gestionar usuarios");
            System.out.println("2. Gestionar recursos");
            System.out.println("3. Gestionar prestamos");
            System.out.println("4. Gestionar reservas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    menuUsuarios();
                    break;
                case 2:
                    menuRecursos();
                    break;
                case 3:
                    menuPrestamos();
                    break;
                case 4:
                    menuReservas();
                    break;
                case 5:
                    salir = true;
                    System.out.println("Saliendo, ¡hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void menuReservas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Reservas ====");
            System.out.println("1. Añadir reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Completar reserva");
            System.out.println("4. Mostrar estado de reservas");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestorReserva.registrarReserva(prioridadReserva.ALTA,scanner);
                    break;
                case 2:
                    gestorReserva.cancelarReserva(scanner);
                    break;
                case 3:
                    gestorReserva.completarReserva(scanner);
                    break;
                case 4:
                    gestorReserva.mostrarReservas();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    public void menuPrestamos(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Prestamos ====");
            System.out.println("1. Añadir Prestamos");
            System.out.println("2. Devolver Prestamos");
            System.out.println("3. Listar Prestamos");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    gestorPrestamos.registrarPrestamo(scanner);
                    break;
                case 2:
                    gestorPrestamos.devolverPrestamo(scanner);
                    break;
                case 3:
                    gestorPrestamos.listarPrestamos();
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void menuUsuarios(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Usuarios ====");
            System.out.println("1. Añadir usuarios");
            System.out.println("2. Eliminar usuarios");
            System.out.println("3. Buscar usuarios");
            System.out.println("4. Listar usuarios");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    gestorUsuario.anadirUsuario(scanner);
                    break;
                case 2:
                    gestorUsuario.eliminarUsuario(scanner);
                    break;
                case 3:
                    gestorUsuario.buscarUsuario(scanner);
                    break;
                case 4:
                    gestorUsuario.listarUsuarios();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void menuRecursos(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Recursos ====");
            System.out.println("1. Añadir recursos");
            System.out.println("2. Eliminar recursos");
            System.out.println("3. Buscar recursos");
            System.out.println("4. Filtrar recursos");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    menuAnadirRecursos();
                    break;
                case 2:
                    gestorRecursos.eliminarRecurso(scanner);
                    break;
                case 3:
                    gestorRecursos.buscarRecurso(scanner);
                    break;
                case 4:
                    filtrarRecursos();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void filtrarRecursos(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú filtrado Recursos ====");
            System.out.println("1. Listar todos los recursos");
            System.out.println("2. Listar recursos que se pueden prestar");
            System.out.println("3. Listar recursos renovables");
            System.out.println("4. Filtrar por categoria");
            System.out.println("5. Volver al menú recursos");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    gestorRecursos.listarRecursos();
                    break;
                case 2:
                    gestorRecursos.filtrarPrestables();
                    break;
                case 3:
                    gestorRecursos.filtrarRenovables();
                    break;
                case 4:
                    menuFiltrarRecursos();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public void menuFiltrarRecursos(){
        System.out.println("Filtrar por categoría:");
        System.out.println("1. LIBRO\n2. REVISTA\n3. PODCAST\n4. AUDIOLIBRO");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                gestorRecursos.filtrarPorCategoria(categoriaRecurso.LIBRO);
                break;
            case 2:
                gestorRecursos.filtrarPorCategoria(categoriaRecurso.REVISTA);
                break;
            case 3:
                gestorRecursos.filtrarPorCategoria(categoriaRecurso.PODCAST);
                break;
            case 4:
                gestorRecursos.filtrarPorCategoria(categoriaRecurso.AUDIOLIBRO);
                break;
            default:
                System.out.println("Opción no válida.");
        }

    }

    public void menuAnadirRecursos(){
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Añadir Recursos ====");
            System.out.println("1. Añadir libro");
            System.out.println("2. Añadir podcast");
            System.out.println("3. Añadir audiolibro");
            System.out.println("4. Añadir revista");
            System.out.println("5. Volver al menú recursos");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion){
                case 1:
                    gestorRecursos.agregarRecurso(categoriaRecurso.LIBRO);
                    break;
                case 2:
                    gestorRecursos.agregarRecurso(categoriaRecurso.PODCAST);
                    break;
                case 3:
                    gestorRecursos.agregarRecurso(categoriaRecurso.AUDIOLIBRO);
                    break;
                case 4:
                    gestorRecursos.agregarRecurso(categoriaRecurso.REVISTA);
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
