package src.consola;
import src.modelos.*;
import src.gestores.GestorUsuario;
import src.gestores.GestorRecursos;
import src.servicios.ServicioNotificacionesEmail;

import java.util.*;

public class Consola {
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private ServicioNotificacionesEmail servicioNotificacionesEmail;
    private Scanner scanner;

    public Consola(){
        Map<String,Usuario> usuarios = new HashMap<>();
        List<RecursoDigital> recursoDigitalList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.servicioNotificacionesEmail = new ServicioNotificacionesEmail("");
        this.gestorRecursos = new GestorRecursos(recursoDigitalList);
        this.gestorUsuario = new GestorUsuario(usuarios, servicioNotificacionesEmail);
    }

    public void iniciar(){
        boolean salir = false;

        while (!salir){
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Gestionar usuarios");
            System.out.println("2. Gestionar recursos");
            System.out.println("3. Salir");
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
                    salir = true;
                    System.out.println("Saliendo, ¡hasta luego!");
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
            System.out.println("4. Mostrar recursos renovables o prestables");
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
            System.out.println("4. Volver al menú recursos");
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
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
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
                    Libro libro = Libro.crearRecurso();
                    gestorRecursos.agregarRecurso(libro);
                    break;
                case 2:
                    Podcast podcast = Podcast.crearRecurso();
                    gestorRecursos.agregarRecurso(podcast);
                    break;
                case 3:
                    Audiolibro audiolibro = Audiolibro.crearRecurso();
                    gestorRecursos.agregarRecurso(audiolibro);
                    break;
                case 4:
                    Revista revista = Revista.crearRecurso();
                    gestorRecursos.agregarRecurso(revista);
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
