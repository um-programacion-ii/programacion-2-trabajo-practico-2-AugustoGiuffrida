package src.modelos;
import src.interfaces.Prestable;
import java.util.Scanner;

public class Audiolibro extends RecursoDigital implements Prestable {
    private String formatoAudio;
    private String idioma;

    public Audiolibro(String titulo, String autor, int anioPublicacion, boolean disponible, String formatoAudio, String idioma){
        super(titulo, autor, anioPublicacion, disponible);
        setFormatoAudio(formatoAudio);
        setIdioma(idioma);
    }

    //getters
    public String getFormatoAudio(){
        return this.formatoAudio;
    }

    public String getIdioma(){
        return  this.idioma;
    }

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

    public static Audiolibro crearRecurso() {
        Object[] datosGenerales = RecursoDigital.leerDatosGenerales();
        String titulo = (String) datosGenerales[0];
        String autor = (String) datosGenerales[1];
        int anio = (int) datosGenerales[2];
        boolean disponible = (boolean) datosGenerales[3];

        Scanner scanner = new Scanner(System.in);
        System.out.print("Formato de audio: ");
        String formatoAudio = scanner.nextLine();
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();

        return new Audiolibro(titulo, autor, anio, disponible, formatoAudio, idioma);
    }
}
