package src.modelos;

public class Usuario {
    private String nombre;
    private String edad;
    private String email;

    public Usuario(String nombre, String edad, String email){
        setNombre(nombre);
        setEdad(edad);
        setEmail(email);
    }

    //getters
    public String getNombre(){
        return this.nombre;
    }

    public String getEdad(){
        return this.edad;
    }

    public String getEmail(){
        return this.email;
    }

    //setters
    public void setNombre(String nombre){
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    public void setEdad(String edad){
        if (edad == null || edad.trim().isEmpty()) {
            throw new IllegalArgumentException("La edad no puede estar vacía");
        }

        try {
            int edadNumerica = Integer.parseInt(edad);
            if (edadNumerica < 0) {
                throw new IllegalArgumentException("La edad no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser un número válido");
        }

        this.edad = edad;
    }

    public void setEmail(String email){
        if (email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        this.email =  email;
    }

    //Metodos
    @Override
    public String toString() {
        return "Usuario: " + nombre + " | Edad: " + edad + " | Email: " + email;
    }

}
