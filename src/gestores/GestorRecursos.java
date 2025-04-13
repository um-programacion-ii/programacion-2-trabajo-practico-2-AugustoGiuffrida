package src.gestores;

import src.modelos.RecursoDigital;

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

    public void eliminarRecurso(){
        Scanner scanner = new Scanner(System.in);
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

    public void buscarRecurso(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese dato del Recurso (Autor, titulo, año de publicacion): ");
        String dato = scanner.nextLine();
        boolean encontrado = false;

        for(RecursoDigital recurso: recursoDigital){
            if (recurso.getTitulo().equalsIgnoreCase(dato) || recurso.getAutor().equalsIgnoreCase(dato) ||String.valueOf(recurso.getAnioPublicacion()).equals(dato)) {
                System.out.println(recurso.toString());
                encontrado = true;
                break;
            }
        }
        if (!encontrado){
            System.out.println("No hay coincidencias");
        }
    }

}