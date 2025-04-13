package src.modelos;
import src.interfaces.Prestable;
import java.util.Scanner;

public class Revista extends RecursoDigital implements Prestable {
    private String edicion;
    private String clasificacion;

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
    public boolean estaDisponible(){
        return this.disponible;
    }

    //setters
    @Override
    public void marcarComoPrestado() {
        disponible = false;
    }

    @Override
    public void marcarComoDisponible() {
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
