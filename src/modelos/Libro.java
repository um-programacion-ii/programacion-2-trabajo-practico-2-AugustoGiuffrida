package src.modelos;
import src.enums.categoriaRecurso;
import src.interfaces.Prestable;
import src.interfaces.Renovable;

import java.util.Scanner;

public class Libro extends RecursoDigital implements Prestable, Renovable {
    private int isbn;
    private String genero;
    private boolean renovable = true;

    public Libro(String titulo, String autor, int anioPublicacion, boolean disponible, int isbn, String genero){
        super(titulo, autor, anioPublicacion, disponible);
        setGenero(genero);
        setIsbn(isbn);
    }

    //getters
    public int getIsbn(){
        return this.isbn;
    }

    public  String getGenero(){
        return  this.genero;
    }

    @Override
    public boolean permiteRenovacion(){
        return this.renovable;
    }

    @Override
    public categoriaRecurso getCategoria() {
        return categoriaRecurso.LIBRO;
    }


    //setters
    public void setIsbn(int isbn){
        if (isbn <= 0){
            throw new IllegalArgumentException("El ISBN debe ser un número positivo.");
        }
        this.isbn = isbn;
    }

    public void setRenovable(boolean renovable) {
        this.renovable = renovable;
    }

    @Override
    public void marcarComoNoDisponible() {
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
