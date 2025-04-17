package src.gestores;
import src.exepciones.UsuarioNoEncontradoException;
import src.modelos.Usuario;
import src.servicios.ServicioNotificacionesEmail;

import java.util.*;

public class GestorUsuario {
    private Map<String, Usuario> usuarios;
    private ServicioNotificacionesEmail servicioNotificacionesEmail;

    public GestorUsuario(Map<String, Usuario> usuarios, ServicioNotificacionesEmail servicioNotificacionesEmail){
        this.usuarios = usuarios;
        this.servicioNotificacionesEmail = servicioNotificacionesEmail;
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\n==== Lista de Usuarios ====");
            for (Usuario usuario : usuarios.values()) {
                System.out.println("- " + usuario);
            }
        }
    }

    public Usuario crearUsuario(Scanner scanner) throws IllegalArgumentException{
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        validarNombre(nombre);

        System.out.print("Ingrese edad: ");
        int edad = scanner.nextInt();
        validarEdad(edad);

        scanner.nextLine();

        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        validarEmail(email);

        System.out.print("Ingrese numero de telefono: ");
        String telefono = scanner.nextLine();
        validarTelefono(telefono);

        return new Usuario(nombre, edad, email, telefono);
    }

    public void validarNombre(String nombre){
        if (nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
    }

    public void validarEdad(int edad){
        if (edad < 0) {
            throw new IllegalArgumentException("La edad debe ser un numero entero");
        }
    }

    public void validarTelefono(String telefono){
        if (telefono == null || telefono.isBlank()){
            throw new IllegalArgumentException("El número de teléfono no puede estar vacio");
        }
    }

    public boolean existeEmail(String email){
        return usuarios.containsKey(email.toLowerCase());
    }

    public void validarEmail(String email){
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("El email no puede estar vacio");
        } else if (existeEmail(email)){
            throw new IllegalArgumentException("Ya existe un usuario con ese email.");
        }
    }

    public void anadirUsuario(Scanner scanner){

        int cantidad = cantidadUsuarios(scanner);

        int registros = 0;

        while (registros < cantidad){
            if (validarUsuario(scanner)){
                registros++;
            }
        }
    }

    public boolean validarUsuario(Scanner scanner){
        try {
            registrarNuevoUsuario(scanner);
            return true;
        } catch (IllegalArgumentException error) {
            System.out.println("Error al agregar usuario: " + error.getMessage());
            System.out.println("\nPor favor, reintente con los datos correctos.\n");
        } catch (InputMismatchException error){
            System.out.println("Error: la entrada no es válida (se esperaba un número para la edad).");
            scanner.nextLine();
        }
        return false;
    }

    public void registrarNuevoUsuario(Scanner scanner){
        Usuario nuevo = crearUsuario(scanner);
        usuarios.put(nuevo.getEmail().toLowerCase(),nuevo);
        servicioNotificacionesEmail.establecerDestinatario(nuevo.getEmail());
        System.out.println(servicioNotificacionesEmail.enviarNotificacion("¡Bienvenido/a al sistema, " + nuevo.getNombre() + "!"));;
    }

    public int cantidadUsuarios(Scanner scanner){
        while (true) {
            try {
                System.out.print("¿Cuantos usuarios quiere añadir? ");
                int cont = scanner.nextInt();
                scanner.nextLine();
                return cont;
            } catch (InputMismatchException error) {
                System.out.println("Error: la entrada no es válida, se esperaba un número.");
                scanner.nextLine();
            }
        }
    }

    public void eliminarUsuario(Scanner scanner ){
        System.out.print("Ingrese el email del usuario a eliminar: ");
        String email = scanner.nextLine();

        Usuario eliminado = usuarios.remove(email.toLowerCase());
        if (eliminado == null){
            System.out.println("No se encontraron usuarios con este email");
        } else {
            System.out.println("El usuario con email '" + email + "' ha sido eliminado correctamente.");
        }
    }

    public void buscarUsuario(Scanner scanner ) {
        try {
            System.out.print("Ingrese el email del usuario: ");
            String email = scanner.nextLine();

            Usuario buscado = usuarios.get(email.toLowerCase());
            if (buscado == null) {
                throw new UsuarioNoEncontradoException("No se encontraron usuarios con este email");
            } else {
                System.out.println(buscado);
            }
        } catch (UsuarioNoEncontradoException error){
            System.out.println("Error: " + error.getMessage());
        }

    }

}
