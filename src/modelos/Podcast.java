package src.modelos;

public class Podcast extends RecursoDigital{
    private String nombreSerie;
    private int episodioNumero;

    public Podcast(String titulo, String autor, int anioPublicacion, boolean disponible, int episodioNumero, String nombreSerie){
        super(titulo, autor, anioPublicacion, disponible);
        setEpisodioNumero(episodioNumero);
        setNombreSerie(nombreSerie);
    }

    //getters
    public String getNombreSerie(){
        return this.nombreSerie;
    }

    public int getEpisodioNumero(){
        return this.episodioNumero;
    }

    //setters
    public void setEpisodioNumero(int episodioNumero){
        if (episodioNumero <=0){
            throw new IllegalArgumentException("El número de episodio debe ser mayor a 0");
        }
        this.episodioNumero = episodioNumero;
    }

    public void setNombreSerie(String nombreSerie){
        if (nombreSerie == null || nombreSerie.trim().isEmpty()){
            throw new IllegalArgumentException("El nombre de la serie no puede estar vacio");
        }
        this.nombreSerie = nombreSerie;
    }

    @Override
    public String toString() {
        return super.toString() + " | Nombre de la serie: " + nombreSerie + " | Número de episodio: " + episodioNumero;
    }

}
