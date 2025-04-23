package src.modelos;
import src.interfaces.Prestable;
import src.enums.estadoPrestamo;
import java.util.Date;

public class Prestamo {
    private int id;
    private RecursoDigital recurso;
    private Usuario usuario;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private estadoPrestamo estado;

    public Prestamo (int id, RecursoDigital recurso,Usuario usuario , Date fechaInicio, Date fechaVencimiento, estadoPrestamo estado){
        this.id = id;
        this.recurso = recurso;
        this.usuario = usuario;
        setFechaInicio(fechaInicio);
        setFechaVencimiento(fechaVencimiento);
        this.estado = estado;
    }

    //getter
    public int getId(){
        return this.id;
    }

    public RecursoDigital getRecurso(){
        return this.recurso;
    }
    public Usuario getUsuario(){
        return this.usuario;
    }

    public Date getFechaInicio(){
        return this.fechaInicio;
    }

    public Date getFechaVencimiento(){
        return this.fechaVencimiento;
    }

    public estadoPrestamo getEstado(){
        return this.estado;
    }

    //setters
    public void setFechaInicio(Date fechaInicio){
        Date fechaActual = new Date();
        if (fechaInicio.after(fechaActual)) {
            throw new IllegalArgumentException("La fecha de inicio no puede estar en el futuro.");
        }
        this.fechaInicio = fechaInicio;

    }

    public void setFechaVencimiento(Date fechaVencimiento){
        if (fechaVencimiento.before(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a la fecha de inicio.");
        }
            this.fechaVencimiento = fechaVencimiento;
    }

    public void setEstado(estadoPrestamo estado){
        this.estado =estado;
    }

    //Metodos
    public boolean estaVencido(){
        return new Date().after(fechaVencimiento);
    }

    public void marcarComoDevuelto() {
        setEstado(estadoPrestamo.DEVUELTO);

        if (recurso instanceof Prestable) {
            ((Prestable) recurso).marcarComoDisponible();
        } else {
            System.out.println("Este recurso no permite devoluciones (no es prestable).");
        }
    }



    @Override
    public String toString(){
        return ("| id: " +id+ " | Recurso: " +recurso.getTitulo() + " | Usuario "+usuario.getEmail() +" | Fecha inicio: " +fechaInicio+ " | Fecha devolucion: " +fechaVencimiento+ " | Estado: "+estado);
    }

}
