package co.edu.udea.compumovil.proyectocm_gr02_20181;

/**
 * Created by santiago.molinae on 31/05/18.
 */

public class User {
    private String userName, userEmail, eventsJoin, eventsCreated;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getEventsJoin() {
        return eventsJoin;
    }

    public void setEventsJoin(String eventsJoin) {
        this.eventsJoin = eventsJoin;
    }

    public String getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(String eventsCreated) {
        this.eventsCreated = eventsCreated;
    }

    public User(String userName, String userEmail, String eventsJoin, String eventsCreated, String uid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.eventsJoin = eventsJoin;
        this.eventsCreated = eventsCreated;
        this.uid = uid;
    }
}
