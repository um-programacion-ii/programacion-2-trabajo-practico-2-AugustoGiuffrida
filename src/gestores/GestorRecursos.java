package src.gestores;

import src.modelos.RecursoDigital;
import java.util.List;
import java.util.Scanner;

public class GestorRecursos {
    private List<RecursoDigital> recursoDigital;

    public GestorRecursos(List<RecursoDigital> recursoDigital) {
        this.recursoDigital = recursoDigital;
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
            System.out.println("No se encontraron usuarios con este email");
        }
    }

}