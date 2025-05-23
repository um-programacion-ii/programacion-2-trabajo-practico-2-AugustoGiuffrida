package src.modelos;
import src.enums.categoriaRecurso;
import src.interfaces.Prestable;
import src.interfaces.Renovable;

import java.util.Scanner;

public class Revista extends RecursoDigital implements Prestable, Renovable {
    private String edicion;
    private String clasificacion;
    private boolean renovable = true;


    public Revista(String titulo, String autor, int anioPublicacion, boolean disponible, String edicion, String clasificacion){
        super(titulo, autor, anioPublicacion, disponible);
        setEdicion(edicion);
        setClasificacion(clasificacion);
    }

    //getters
    public String getEdicion(){
        return this.edicion;
    }

    public String getClasificacion(){
        return this.clasificacion;
    }

    @Override
    public boolean permiteRenovacion(){
        return this.renovable;
    }

    @Override
    public categoriaRecurso getCategoria() {
        return categoriaRecurso.REVISTA;
    }

    //setters
    @Override
    public synchronized void marcarComoNoDisponible() {
        disponible = false;
    }

    public void setRenovable(boolean renovable) {
        this.renovable = renovable;
    }

    @Override
    public synchronized void marcarComoDisponible() {
        disponible = true;
    }

    public void setEdicion(String edicion){
        if (edicion == null || edicion.trim().isEmpty()){
            throw new IllegalArgumentException("La edición no puede estar vacía");
        }
        this.edicion = edicion;
    }

    public void setClasificacion(String clasificacion){
        if (clasificacion == null || clasificacion.trim().isEmpty()){
            throw new IllegalArgumentException("La clasificacion no puede estar vacía");
        }
        this.clasificacion = clasificacion;
    }

    @Override
    public String toString(){
        return super.toString() + " | Clasificación: " + clasificacion + " | Edición: " + edicion;
    }

    public static Revista crearRecurso() {
        Object[] datosGenerales = RecursoDigital.leerDatosGenerales();
        String titulo = (String) datosGenerales[0];
        String autor = (String) datosGenerales[1];
        int anio = (int) datosGenerales[2];
        boolean disponible = (boolean) datosGenerales[3];

        Scanner scanner = new Scanner(System.in);
        System.out.print("Edicion: ");
        String edicion = scanner.nextLine();
        System.out.print("Clasificación: ");
        String clasificacion = scanner.nextLine();

        return new Revista(titulo, autor, anio, disponible, edicion, clasificacion);
    }
}
