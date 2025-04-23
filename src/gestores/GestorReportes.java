package src.gestores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import src.enums.categoriaRecurso;
import src.modelos.Prestamo;

public class GestorReportes {
    private List<Prestamo> prestamos;
    private final ExecutorService executor;

    public  GestorReportes(List<Prestamo> prestamos){
        this.prestamos = prestamos;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void generarTodosLosReportes() {
        System.out.println("\n================= GENERANDO REPORTES =================\n");
        mostrarMasPrestados();
        mostrarUsuariosMasActivos();
        mostrarEstadisticasPorCategoria();
        System.out.println("\n======================================================");
    }


    public void mostrarMasPrestados(){
        System.out.println("\nRecursos más prestados:");
        Map<String, Integer> conteoRecursos = new HashMap<>();

        for (Prestamo prestamo : prestamos){
            String titulo = prestamo.getRecurso().getTitulo();
            conteoRecursos.put(titulo,conteoRecursos.getOrDefault(titulo,0)+1);
        }

        imprimirResultadosOrdenados(conteoRecursos, "prestamos");
    }

    public void mostrarUsuariosMasActivos(){
        System.out.println("\nUsuarios más activos:");
        Map<String, Integer> conteoUsuarios = new HashMap<>();

        for (Prestamo prestamo : prestamos){
            String email = prestamo.getUsuario().getEmail();
            conteoUsuarios.put(email,conteoUsuarios.getOrDefault(email,0)+1);
        }

        imprimirResultadosOrdenados(conteoUsuarios, "prestamos");
    }

    public void mostrarEstadisticasPorCategoria(){
        System.out.println("\nEstadísticas por categoría:");
        Map<categoriaRecurso, Integer> conteoCategorias = new HashMap<>();

        for (Prestamo prestamo : prestamos){
            categoriaRecurso categoria = prestamo.getRecurso().getCategoria();
            conteoCategorias.put(categoria,conteoCategorias.getOrDefault(categoria,0)+1);
        }

        for (Map.Entry<categoriaRecurso, Integer> entrada : conteoCategorias.entrySet()) {
            System.out.println("- " + entrada.getKey() + ": " + entrada.getValue() + " prestamos");
        }

    }

    public void imprimirResultadosOrdenados(Map<String, Integer> mapa, String unidad){
        List<Map.Entry<String,Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());

        listaOrdenada.sort((e1,e2)-> e2.getValue().compareTo(e1.getValue()));

        for (Map.Entry<String, Integer> entrada : listaOrdenada) {
            System.out.println("- " + entrada.getKey() + ": " + entrada.getValue() + " " + unidad);
        }
    }

    public void cerrar(){
        executor.shutdown();
    }
}
