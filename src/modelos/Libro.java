package src.modelos;
import src.interfaces.Prestable;
import java.util.Scanner;

public class Libro extends RecursoDigital implements Prestable {
    private int isbn;
    private String genero;

    public Libro(String titulo, String autor, int anioPublicacion, boolean disponible, int isbn, String genero){
        super(titulo, autor, anioPublicacion, disponible);
        this.isbn = isbn;
        setGenero(genero);
    }

    //getters
    public int getIsbn(){
        return this.isbn;
    }

    public  String getGenero(){
        return  this.genero;
    }

    @Override
    public boolean estaDisponible(){
        return this.disponible;
    }

    //setters
    public void setIsbn(int isbn){
        this.isbn = isbn;
    }

    @Override
    public void marcarComoPrestado() {
        disponible = false;
    }

    @Override
    public void marcarComoDisponible() {
        disponible = true;
    }

    public void setGenero(String genero){
        if (genero == null || genero.trim().isEmpty()){
            throw new IllegalArgumentException("El genero no puede estar vacio");
        }
        this.genero = genero;
    }

    @Override
    public String toString(){
        return super.toString() + "| ISBN:" +isbn+ "| Genero: " +genero;
    }


    public static Libro crearRecurso() {
        Object[] datosGenerales = RecursoDigital.leerDatosGenerales();
        String titulo = (String) datosGenerales[0];
        String autor = (String) datosGenerales[1];
        int anio = (int) datosGenerales[2];
        boolean disponible = (boolean) datosGenerales[3];

        Scanner scanner = new Scanner(System.in);
        System.out.print("ISBN: ");
        int isbn = Integer.parseInt(scanner.nextLine());
        System.out.print("Género: ");
        String genero = scanner.nextLine();

        return new Libro(titulo, autor, anio, disponible, isbn, genero);
    }


}
