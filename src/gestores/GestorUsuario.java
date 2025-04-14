package src.gestores;
import src.modelos.Usuario;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GestorUsuario {
    private List<Usuario> usuarios;

    public GestorUsuario(List<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\n==== Lista de Usuarios ====");
            for (Usuario usuario : usuarios) {
                System.out.println("- " + usuario);
            }
        }
    }

    public Usuario crearUsuario(Scanner scanner) throws IllegalArgumentException{
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        System.out.print("Ingrese edad: ");
        int edad = scanner.nextInt();
        if (edad < 0) {
            throw new IllegalArgumentException("La edad debe ser un numero entero");
        }
        scanner.nextLine();

        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("El email no puede estar vacio");
        } else if (existeEmail(email)){
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }

        System.out.print("Ingrese numero de telefono: ");
        String telefono = scanner.nextLine();
        if (telefono == null || telefono.isBlank()){
            throw new IllegalArgumentException("El número de teléfono no puede estar vacio");
        }
        return new Usuario(nombre, edad, email, telefono);
    }

    public boolean existeEmail(String email){
        for (Usuario u:usuarios){
            if(u.getEmail().equalsIgnoreCase(email)){
                return true;
            }
        }
        return false;
    }

    public void anadirUsuario(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuantos usuarios quiere añadir? ");
        int cont = scanner.nextInt();
        scanner.nextLine();

        for (int i =1; i <= cont; i++){
            try {
               Usuario nuevo = crearUsuario(scanner);
               usuarios.add(nuevo);
            } catch (IllegalArgumentException error) {
                System.out.print("Error al agregar usuario: " + error.getMessage());
                System.out.println("\nPor favor, reintente con los datos correctos.\n");
                i--;
            } catch (InputMismatchException error){
                System.out.println("Error: la entrada no es válida (se esperaba un número para la edad).");
                scanner.nextLine();
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
                System.out.println("El usuario con email '" + usuario.getEmail() + "' ha sido eliminado correctamente.");
                iterator.remove();
                eliminado = true;
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
