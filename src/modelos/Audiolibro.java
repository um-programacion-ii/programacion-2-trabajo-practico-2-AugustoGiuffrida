package src.modelos;

public class Audiolibro extends RecursoDigital {
    private String formatoAudio;
    private String idioma;

    public Audiolibro(String titulo, String autor, int anioPublicacion, boolean disponible, String formatoAudio, String idioma){
        super(titulo, autor, anioPublicacion, disponible);
        this.formatoAudio = formatoAudio;
        this.idioma =idioma;
    }

    //getters
    public String getFormatoAudio(){
        return this.formatoAudio;
    }

    public String getIdioma(){
        return  this.idioma;
    }

    //setters
    public void setFormatoAudio(String formatoAudio){
        if (formatoAudio == null || formatoAudio.trim().isEmpty()){
            throw new IllegalArgumentException("El formato del Audio no puede estar vacío");
        }
        this.formatoAudio = formatoAudio;
    }

    public void setIdioma(String idioma){
        if (idioma == null || idioma.trim().isEmpty()){
            throw new IllegalArgumentException("El idioma no puede estar vacío");
        }
        this.idioma = idioma;
    }

    @Override
    public String toString(){
        return super.toString() + " | Formato Audio: " + formatoAudio + " | Idioma: " + idioma;
    }
}
