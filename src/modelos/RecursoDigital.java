package src.modelos;
import src.interfaces.IRecursoDigital;
import java.util.Scanner;
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
        return "Recurso: " + titulo + " | autor: " + autor + " | anioPublicacion: " + anioPublicacion + " | Disponibilidad: " +disponible;
    }

    protected static Object[] leerDatosGenerales() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        System.out.print("¿Está disponible? (true/false): ");
        boolean disponible = Boolean.parseBoolean(scanner.nextLine());

        return new Object[]{titulo, autor, anio, disponible};
    }


}
