package src.gestores;
import src.modelos.Usuario;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GestorUsuario {
    private List<Usuario> usuarios;

    public GestorUsuario(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    public void anadirUsuario(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuantos usuarios quiere añadir? ");
        int cont = scanner.nextInt();
        scanner.nextLine();

        for (int i =1; i <= cont; i++){
            try {
                System.out.print("Ingrese nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingrese edad: ");
                String edad = scanner.nextLine();

                System.out.print("Ingrese email: ");
                String email = scanner.nextLine();

                Usuario usuario = new Usuario(nombre, edad, email);
                usuarios.add(usuario);
            } catch (IllegalArgumentException error){
                System.out.print("Error al agregar usuario: "+error.getMessage());
                System.out.println("\nPor favor, reintente con los datos correctos.\n");
                i--;
            }
        }
    }

    public void eliminarUsuario(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el email del usuario a eliminar: ");
        String email = scanner.nextLine();
        boolean eliminado = false;

        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()){
            Usuario usuario = iterator.next();
            if (usuario.getEmail().equalsIgnoreCase(email)){
                iterator.remove();
                eliminado = true;
                System.out.print("Usuario eliminado correctamente");
                break;
            }
        }
        if (!eliminado){
            System.out.print("No se encontraron usuarios con este email");
        }
    }

    public void buscarUsuario(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el email del usuario: ");
        String email = scanner.nextLine();
        boolean encontrado = false;

        for(Usuario usuario: usuarios){
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                System.out.println(usuario.toString());
                encontrado = true;
                break;
            }
        }
        if (!encontrado){
            System.out.println("No se encontraron usuarios con este email");
        }
    }

}
