package src.modelos;
import src.interfaces.IRecursoDigital;

import java.time.LocalDate;

public abstract class RecursoDigital implements IRecursoDigital {
    protected String titulo;
    protected String autor;
    protected int anioPublicacion;
    protected boolean disponible;

    public RecursoDigital (String titulo, String autor, int anioPublicacion, boolean disponible){
        setTitulo(titulo);
        setAutor(autor);
        setAnioPublicacion(anioPublicacion);
        this.disponible = disponible;
    }

    @Override
    public String getTitulo(){
        return this.titulo;
    }

    @Override
    public String getAutor(){
        return this.autor;
    }

    @Override
    public int getAnioPublicacion(){
        return this.anioPublicacion;
    }

    @Override
    public void marcarComoPrestado() {
        disponible = false;
    }

    @Override
    public void marcarComoDisponible() {
        disponible = true;
    }

    @Override
    public boolean estaDisponible(){
        return this.disponible;
    }

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

    @Override
    public String toString() {
        return "Recurso: " + titulo + " | autor: " + autor + " | anioPublicacion: " + anioPublicacion;
    }

}
