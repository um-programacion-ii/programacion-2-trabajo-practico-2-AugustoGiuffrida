package src.modelos;

public class Libro extends RecursoDigital{
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

    //setters
    public void setIsbn(int isbn){
        this.isbn = isbn;
    }

    public void setGenero(String genero){
        if (genero == null || genero.trim().isEmpty()){
            throw new IllegalArgumentException("El genero no puede estar vacio");
        }
        this.genero = genero;
    }

    @Override public String toString(){
        return super.toString() + "| ISBN:" +isbn+ "| Genero: " +genero;
    }

}
