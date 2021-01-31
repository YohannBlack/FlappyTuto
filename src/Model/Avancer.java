package Model;

public class Avancer extends Thread{

    /*** Attributs ***/
    private Parcours parcours;
    private Etat etat;

    /*** Constantes ***/
    private final int DELAY = 75;

    public Avancer (Parcours parcours, Etat etat){
        this.parcours = parcours;
        this.etat = etat;
    }

    /**
     * Permet de faire avancer la ligne
     */
    public void run(){
        while(etat.testPerdu()){
            parcours.setPosition();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
