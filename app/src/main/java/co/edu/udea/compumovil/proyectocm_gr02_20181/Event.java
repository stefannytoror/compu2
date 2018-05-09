package co.edu.udea.compumovil.proyectocm_gr02_20181;

/**
 * Created by personal on 7/05/18.
 */

public class Event {
    private String uid , origen , destino , usuario;

    public Event(){

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

    public Event (String uid , String origen , String  destino , String usuario){
        this.uid = uid; // primary key
        this.origen = origen;
        this.destino = destino;
        this.usuario = usuario;
    }

    public Event (String uid , String origen , String  destino){
        this.uid = uid; // primary key
        this.origen = origen;
        this.destino = destino;
    }



}


