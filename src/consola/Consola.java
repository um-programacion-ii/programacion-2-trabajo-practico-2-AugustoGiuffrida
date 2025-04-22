package src.consola;
import src.alertas.AlertaVencimiento;
import src.enums.estadoPrestamo;
import src.enums.prioridadReserva;
import src.gestores.*;
import src.modelos.*;
import src.servicios.ServicioNotificacionesEmail;
import src.enums.categoriaRecurso;

import java.util.*;

public class Consola {
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private GestorPrestamos gestorPrestamos;
    private GestorReserva gestorReserva;
    private GestorNotificaciones gestorNotificaciones;
    private GestorReportes gestorReportes;
    private ServicioNotificacionesEmail servicioNotificacionesEmail;
    private AlertaVencimiento alertaVencimiento;
    private Scanner scanner;

    public Consola(){
        Map<String,Usuario> usuarios = new HashMap<>();
        List<RecursoDigital> recursoDigitalList = new ArrayList<>();
        List<Prestamo> prestamos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.gestorRecursos = new GestorRecursos(recursoDigitalList);
        this.servicioNotificacionesEmail = new ServicioNotificacionesEmail();
        this.gestorReportes= new GestorReportes(prestamos);
        this.gestorNotificaciones = new GestorNotificaciones();
        this.gestorNotificaciones.agregarServicio(servicioNotificacionesEmail);
        this.gestorUsuario = new GestorUsuario(usuarios, gestorNotificaciones);
        this.gestorReserva = new GestorReserva(gestorRecursos,gestorUsuario, gestorNotificaciones ,1);
        this.gestorPrestamos = new GestorPrestamos(1, prestamos, gestorRecursos, gestorUsuario, gestorReserva, gestorNotificaciones);
        AlertaVencimiento alerta = new AlertaVencimiento(prestamos, gestorPrestamos);
        this.alertaVencimiento = new AlertaVencimiento(prestamos, gestorPrestamos);

        Date hoy = new Date(); // hoy
        Date manana = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // mañana

        Usuario usuarioTest = new Usuario("Test",33 ,"test@example.com","22222");
        RecursoDigital recursoTest = new Libro("libro", "pepe", 2000, false, 1000,"genero");

        usuarios.put(usuarioTest.getEmail(),usuarioTest);
        recursoDigitalList.add(recursoTest);

        Prestamo prestamoHoy = new Prestamo(99, recursoTest, usuarioTest, hoy, hoy, estadoPrestamo.ACTIVO);
        Prestamo prestamoManana = new Prestamo(100, recursoTest, usuarioTest, hoy, manana, estadoPrestamo.ACTIVO);

        prestamos.add(prestamoHoy);
        prestamos.add(prestamoManana);
    }

    public void iniciar(){
        boolean salir = false;
        while (!salir){
            alertaVencimiento.verificarAlertas(scanner);
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Gestionar usuarios");
            System.out.println("2. Gestionar recursos");
            System.out.println("3. Gestionar prestamos");
            System.out.println("4. Gestionar reservas");
            System.out.println("5. Gestionar reportes");
            System.out.println("6. Salir");
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
                    menuReportes();
                    break;
                case 6:
                    salir = true;
                    scanner.close();
                    gestorNotificaciones.cerrar();
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
                    prioridadReserva prioridad = seleccionarPrioridad(scanner);
                    gestorReserva.registrarReserva(prioridad,scanner);
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

    public prioridadReserva seleccionarPrioridad(Scanner scanner){
        System.out.println("1. Alta\n2. Media\n3. Baja");
        System.out.print("Seleccione prioridad:");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion){
            case 1: return prioridadReserva.ALTA;
            case 2: return prioridadReserva.MEDIA;
            case 3: return prioridadReserva.BAJA;
            default:
                System.out.println("Opción inválida. Se asigna prioridad MEDIA por defecto.");
                return prioridadReserva.MEDIA;
        }
    }


    public void menuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n==== Menú Reportes ====");
            System.out.println("1. Recursos más prestados");
            System.out.println("2. Usuarios más activos");
            System.out.println("3. Estadísticas por categoría");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestorReportes.mostrarMasPrestados();
                    break;
                case 2:
                    gestorReportes.mostrarUsuariosMasActivos();
                    break;
                case 3:
                    gestorReportes.mostrarEstadisticasPorCategoria();
                    break;
                case 4:
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
            System.out.println("3. Renovar Prestamos");
            System.out.println("4. Listar Prestamos");
            System.out.println("5. Volver al menú principal");
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
                    gestorPrestamos.renovarPrestamo(scanner);
                    break;
                case 4:
                    gestorPrestamos.listarPrestamos();
                    break;
                case 5:
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
