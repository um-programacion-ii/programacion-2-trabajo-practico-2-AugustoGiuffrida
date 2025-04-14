package src.modelos;

public class Usuario {
    private String nombre;
    private int edad;
    private String email;
    private String telefono;

    public Usuario(String nombre, int edad, String email, String telefono){
        setNombre(nombre);
        setEdad(edad);
        setEmail(email);
        setTelefono(telefono);
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
    public String getTelefono(){
        return this.telefono;
    }

    //setters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setEdad(int edad){
        this.edad = edad;
    }
    public void setEmail(String email){
        this.email =  email;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    //Metodos
    @Override
    public String toString() {
        return "Usuario: " + nombre + " | Edad: " + edad + " | Email: " + email + " | Telefono: " +telefono;
    }

}