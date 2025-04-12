package src.consola;
import src.modelos.RecursoDigital;
import src.modelos.Usuario;
import src.gestores.GestorUsuario;
import src.gestores.GestorRecursos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Consola {
    private GestorUsuario gestorUsuario;
    private GestorRecursos gestorRecursos;
    private Scanner scanner;

    public Consola(){
         List<Usuario> usuarios = new ArrayList<>();
         List<RecursoDigital> recursoDigitalList = new ArrayList<>();
          this.scanner = new Scanner(System.in);
         this.gestorRecursos = new GestorRecursos(recursoDigitalList);
         this.gestorUsuario = new GestorUsuario(usuarios);
    }

    public void iniciar(){
        boolean salir = false;

        while (!salir){
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Gestionar usuarios");
            System.out.println("2. Gestionar Recursos");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

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
                    System.out.println("Opcion invalidad");
            }
        }
    }

    public void menuUsuarios(){
        System.out.println("\n==== Menú Usuarios ====");
        System.out.println("1. Añadir usuarios");
        System.out.println("2. Eliminar usuarios");
        System.out.println("3. Buscar usuarios");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                gestorUsuario.anadirUsuario();
                break;
            case 2:
                gestorUsuario.eliminarUsuario();
                break;
            case 3:
                gestorUsuario.buscarUsuario();
                break;
            default:
                System.out.println("Opcion invalida");
        }
    }

    public void menuRecursos(){
        System.out.println("\n==== Menú Recursos ====");
        System.out.println("1. Añadir recursos");
        System.out.println("2. Eliminar recursos");
        System.out.println("3. Buscar recursos");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion){
            case 1:
                System.out.println("Añadir recursos");
                break;
            case 2:
                System.out.println("Eliminar recursos");
                break;
            case 3:
                gestorRecursos.buscarRecurso();
                break;
            default:
                System.out.println("Opcion invalida");
        }
    }



}
