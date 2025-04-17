package src.gestores;

import src.enums.tipoFiltro;
import src.interfaces.Prestable;
import src.interfaces.Renovable;
import src.modelos.RecursoDigital;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class GestorRecursos {
    private List<RecursoDigital> recursoDigital;

    public GestorRecursos(List<RecursoDigital> recursoDigital) {
        this.recursoDigital = recursoDigital;
    }

    public void agregarRecurso(RecursoDigital recurso) {
        recursoDigital.add(recurso);
        System.out.println("Recurso agregado exitosamente.");
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
        System.out.print("Ingrese dato del Recurso (Autor, titulo, año de publicacion): ");
        String dato = scanner.nextLine();

        List<RecursoDigital> encontrados = recursoDigital.stream()
                .filter(r ->r.getTitulo().equalsIgnoreCase(dato) || r.getAutor().equalsIgnoreCase(dato) || String.valueOf(r.getAnioPublicacion()).equals(dato))
                .toList();

        if (encontrados.isEmpty()){
            System.out.println("No hay coincidencias");
        } else{
            encontrados.forEach(System.out::println);
        }
    }

}