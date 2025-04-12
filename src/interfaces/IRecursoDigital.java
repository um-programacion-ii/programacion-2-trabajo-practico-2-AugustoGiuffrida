package src.interfaces;

public interface IRecursoDigital {
        String getTitulo();
        String getAutor();
        int getAnioPublicacion();
        boolean estaDisponible();
        void marcarComoPrestado();
        void marcarComoDisponible();
}


