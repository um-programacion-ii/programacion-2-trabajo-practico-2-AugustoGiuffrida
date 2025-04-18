package src.modelos;
import  src.enums.estadoReserva;
import  src.enums.prioridadReserva;
import java.util.Date;

public class Reserva {
    private int id;
    private Usuario usuario;
    private RecursoDigital recurso;
    private Date fechaSolicitud;
    private prioridadReserva prioridad;
    private estadoReserva estado;

    public Reserva(int id, Usuario usuario, RecursoDigital recurso, Date fechaSolicitud, prioridadReserva prioridad, estadoReserva estado){
        this.id = id;
        this.usuario = usuario;
        this.recurso = recurso;
        this.fechaSolicitud = fechaSolicitud;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public RecursoDigital getRecurso(){
        return this.recurso;
    }

    public Date getFechaSolicitud(){
        return this.fechaSolicitud;
    }

    public prioridadReserva getPrioridad(){
        return this.prioridad;
    }

    public estadoReserva getEstado(){
        return this.estado;
    }

    //setters
    public void setFechaSolicitud(Date fechaSolicitud){
        this.fechaSolicitud = fechaSolicitud;
    }

    public void completar(){
        this.estado = estadoReserva.COMPLETADA;
    }

    public void cancelar(){
        this.estado = estadoReserva.CANCELADA;
    }

    @Override
    public String toString(){
        return "ID: " + id +
                " | Usuario: " +  usuario.getEmail() +
                " | Recurso: " + recurso.getTitulo() +
                " | Fecha de solicitud: " + fechaSolicitud +
                " | Prioridad: " + prioridad +
                " | Estado: " + estado;

    }

}
