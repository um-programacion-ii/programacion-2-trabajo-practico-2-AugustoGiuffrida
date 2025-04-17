package src.gestores;

import src.enums.tipoFiltro;
import src.exepciones.RecursoNoDisponibleException;
import src.interfaces.Prestable;
import src.interfaces.Renovable;
import src.modelos.*;
import src.enums.categoriaRecurso;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class GestorRecursos {
    private List<RecursoDigital> recursoDigital;

    public GestorRecursos(List<RecursoDigital> recursoDigital) {
        this.recursoDigital = recursoDigital;
    }

    public void agregarRecurso(categoriaRecurso categoriaRecurso) {
        while (true){
            try {
                RecursoDigital recurso = crearRecursoPorCategoria(categoriaRecurso);
                recursoDigital.add(recurso);
                break;
            } catch (NumberFormatException error) {
                System.out.println("Error al ingresar los datos: " + error.getMessage());
            } catch (IllegalArgumentException error){
                System.out.println("Error al ingresar los datos: " + error.getMessage());
                System.out.println("Por favor, vuelva a intentarlo.\n");
            }
        }
    }

    private RecursoDigital crearRecursoPorCategoria(categoriaRecurso categoria) {
        switch (categoria) {
            case LIBRO:
                return Libro.crearRecurso();
            case REVISTA:
                return Revista.crearRecurso();
            case PODCAST:
                return Podcast.crearRecurso();
            case AUDIOLIBRO:
                return Audiolibro.crearRecurso();
            default:
                throw new IllegalArgumentException("Categoría no válida.");
        }
    }

    public void filtrarPorCategoria(categoriaRecurso categoria){
        boolean encontrado = false;
        System.out.println("\n==== Recursos de la categoría: " + categoria + " ====");

        for (RecursoDigital recurso : recursoDigital){
            if (buscarPorCategoria(recurso,categoria)){
                encontrado = true;
            }
        }
        if(!encontrado){
            System.out.println("No hay recursos de esta categoría.");
        }
    }

    public boolean buscarPorCategoria (RecursoDigital recurso,categoriaRecurso categoria){
        if (recurso.getCategoria() == categoria){
            System.out.println("- " + recurso);
            return  true;
        }
        return false;
    }

    public void listarRecursos(){
        if (recursoDigital.isEmpty()) {
            System.out.println("No hay recursos disponibles.");
            return;
        }
        System.out.println("\n==== Lista de Recursos ====");
        for(RecursoDigital recurso: recursoDigital){
            System.out.println("- " + recurso);
        }
    }

    public void mostrarRecursosFiltrados(String titulo, tipoFiltro tipo){
        if (recursoDigital.isEmpty()) {
            System.out.println("No hay recursos disponibles.");
            return;
        }
        System.out.println("\n==== "+titulo+" ====");
        for (RecursoDigital recurso : recursoDigital) {
            switch (tipo) {
                case PRESTABLE:
                    if (recurso instanceof Prestable ) {
                        System.out.println("- " + recurso);
                    }
                    break;
                case RENOVABLE:
                    if(recurso instanceof Renovable && ((Renovable) recurso).permiteRenovacion()) {
                        System.out.println("- " + recurso);
                    }
                    break;
            }
        }
    }

    public void filtrarPrestables(){
        mostrarRecursosFiltrados("Lista de Recursos Prestables", tipoFiltro.PRESTABLE);
    }

    public void filtrarRenovables(){
        mostrarRecursosFiltrados("Lista de Recursos Renovables", tipoFiltro.RENOVABLE);
    }


    public void eliminarRecurso(Scanner scanner){
        System.out.print("Ingrese el titulo del recurso a eliminar: ");
        String titulo = scanner.nextLine();
        boolean eliminado = false;

        Iterator<RecursoDigital> iterator = recursoDigital.iterator();
        while(iterator.hasNext()){
            RecursoDigital recurso = iterator.next();
            if (recurso.getTitulo().equalsIgnoreCase(titulo)){
                System.out.println("El recurso " +recurso.getTitulo()+ " ha sido eliminado");
                iterator.remove();
                eliminado = true;
                break;
            }
        }
        if (!eliminado){
            System.out.println("No se encontraron coincidencias");
        }
    }

    public void buscarRecurso(Scanner scanner){
        try {
            System.out.print("Ingrese dato del Recurso (Autor, titulo, año de publicacion): ");
            String dato = scanner.nextLine();

            List<RecursoDigital> encontrados = recursoDigital.stream()
                    .filter(r -> r.getTitulo().equalsIgnoreCase(dato) || r.getAutor().equalsIgnoreCase(dato) || String.valueOf(r.getAnioPublicacion()).equals(dato))
                    .toList();

            if (encontrados.isEmpty()) {
                throw new RecursoNoDisponibleException("El recurso no esta disponible.");
            } else {
                encontrados.forEach(System.out::println);
            }
        } catch ( RecursoNoDisponibleException error){
            System.out.println("Error: " + error.getMessage());
        }
    }

}