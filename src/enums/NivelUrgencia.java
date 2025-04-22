package src.enums;

public enum NivelUrgencia {
    INFO("INFO", "\u001B[34m"),       // Azul
    WARNING("ADVERTENCIA", "\u001B[33m"), // Amarillo
    ERROR("URGENTE", "\u001B[31m");      // Rojo

    private final String etiqueta;
    private final String color;

    NivelUrgencia(String etiqueta, String color) {
        this.etiqueta = etiqueta;
        this.color = color;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String formatear(String mensaje) {
        return color + mensaje + "\u001B[0m"; // reset color
    }
}
