
import java.util.*;

public class avis{

    protected int nbEtoiles;
    protected String desc;
    protected user deQui, pourQui;
    protected Date date;

    public avis(user from, user to, int nbEtoiles, String description) {
        this.date = new Date();
        this.nbEtoiles = nbEtoiles;
        this.desc = description;
        this.pourQui = to;
        this.deQui = from;
    }

    public avis(user from, user to, int nbEtoiles) {
        this.date = new Date();
        this.pourQui = to;
        this.deQui = from;
    }

    public void getAvis() {
        System.out.println("Nombre d'étoiles attribuées à " + this.pourQui + ":" + this.nbEtoiles + " , Posté par : " + this.deQui + " à " + this.date);
    }

}
