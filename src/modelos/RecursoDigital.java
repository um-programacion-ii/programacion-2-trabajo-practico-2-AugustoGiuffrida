package src.modelos;
import java.time.LocalDate;

public abstract class RecursoDigital {
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;

    public RecursoDigital (String titulo, String autor, int anioPublicacion){
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
    }

    //getters
    public String getTitulo(){
        return this.titulo;
    }

    public String getAutor(){
        return this.autor;
    }

    public int getAnioPublicacion(){
        return this.anioPublicacion;
    }

    //setters
    public void setTitulo(String titulo){
        if (titulo == null || titulo.trim().isEmpty()){
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }
        this.titulo = titulo;
    }

    public void setAutor(String autor){
        if (autor == null || autor.trim().isEmpty()){
            throw new IllegalArgumentException("El autor no puede estar vacio");
        }
        this.autor = autor;
    }

    public void setAnioPublicacion(int anioPublicacion){
        int anioActual = LocalDate.now().getYear();
        if (anioPublicacion < 1900 || anioPublicacion > anioActual){
            throw new IllegalArgumentException("El año de publicación debe estar entre 1900 y " + anioActual + ".");
        }
        this.anioPublicacion = anioPublicacion;
    }
}
