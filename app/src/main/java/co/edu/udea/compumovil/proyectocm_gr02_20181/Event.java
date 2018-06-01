package co.edu.udea.compumovil.proyectocm_gr02_20181;

/**
 * Created by personal on 7/05/18.
 */

public class Event {
    private String uid;
    private String origen;
    private String destino;
    private String usuario;
    private String hora;
    private String fecha;
    private String coordenadaOrigen;
    private String coordenadaDestino;

    public Event(){

    }

    public String getCoordenadaOrigen() {
        return coordenadaOrigen;
    }

    public void setCoordenadaOrigen(String coordenadaOrigen) {
        this.coordenadaOrigen = coordenadaOrigen;
    }

    public String getCoordenadaDestino() {
        return coordenadaDestino;
    }

    public void setCoordenadaDestino(String coordenadaDestino) {
        this.coordenadaDestino = coordenadaDestino;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }


    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Event (String uid , String origen , String  destino , String usuario){
        this.uid = uid; // primary key
        this.origen = origen;
        this.destino = destino;
        this.usuario = usuario;
    }

    public Event (String uid , String origen , String  destino,String hora ,String fecha){
        this.uid = uid; // primary key
        this.origen = origen;
        this.destino = destino;
        this.hora = hora;
        this.fecha = fecha;
    }

    public Event(String uid, String origen, String destino, String usuario, String hora,
                 String fecha, String coordenadaOrigen , String coordenadaDestino) {
        this.uid = uid;
        this.origen = origen;
        this.destino = destino;
        this.usuario = usuario;
        this.hora = hora;
        this.fecha = fecha;
        this.coordenadaOrigen = coordenadaOrigen;
        this.coordenadaDestino = coordenadaDestino;
    }
}


