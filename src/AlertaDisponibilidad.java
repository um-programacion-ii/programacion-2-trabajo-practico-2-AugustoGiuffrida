package src;

import src.modelos.RecursoDigital;
import src.modelos.Usuario;

import java.util.Scanner;

public class AlertaDisponibilidad {
    private Usuario usuario;
    private RecursoDigital recurso;
    private boolean confirmada;

    public AlertaDisponibilidad(Usuario usuario, RecursoDigital recurso) {
        this.usuario = usuario;
        this.recurso = recurso;
        this.confirmada = false;
    }

    public void notificar() {
        System.out.println("El recurso '" + recurso.getTitulo() + "' reservado por "+usuario.getEmail() +" ya está disponible.");
        System.out.print("¿Deseas realizar el préstamo? (s/n): ");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine().trim().toLowerCase();
        this.confirmada = respuesta.equals("s");
    }

    public boolean isConfirmada() {
        return confirmada;
    }

}

