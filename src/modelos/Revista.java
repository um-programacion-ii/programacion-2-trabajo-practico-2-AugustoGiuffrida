package src.modelos;

public class Revista extends RecursoDigital {
    private String edicion;
    private String clasificacion;

    public Revista(String titulo, String autor, int anioPublicacion, boolean disponible, String edicion, String clasificacion){
        super(titulo, autor, anioPublicacion, disponible);
        this.edicion = edicion;
        this.clasificacion =clasificacion;
    }

    //getters
    public String getEdicion(){
        return this.edicion;
    }

    public String getClasificacion(){
        return this.clasificacion;
    }

    //setters
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
}
