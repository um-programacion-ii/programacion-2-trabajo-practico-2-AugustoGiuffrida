package src.modelos;

public class Usuario {
    private String nombre;
    private Integer edad;
    private String email;

    public Usuario(String nombre, Integer edad, String email){
        setNombre(nombre);
        setEdad(edad);
        setEmail(email);
    }

    //getters
    public String getNombre(){
        return this.nombre;
    }

    public Integer getEdad(){
        return this.edad;
    }

    public String getEmail(){
        return this.email;
    }

    //setters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEdad(Integer edad){
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