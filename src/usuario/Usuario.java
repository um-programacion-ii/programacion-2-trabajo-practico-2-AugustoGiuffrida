package src.usuario;

public class Usuario {
    private String nombre;
    private int edad;
    private String email;

    public Usuario(String nombre, int edad, String email){
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
    }

    //getters
    public String getNombre(){
        return this.nombre;
    }

    public int getEdad(){
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

    public void setEdad(int edad){
        if (edad <0){
            throw new IllegalArgumentException("La edad no puede ser negativa");
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
