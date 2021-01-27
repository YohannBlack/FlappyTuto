package Model;

public class Voler extends Thread{

    private Etat etat;
    private final int DELAY = 100;

    public Voler(Etat e){
        this.etat = e;
    }

    public void resetCounter(){ this.interrupt();}

    /**
     * Permet de faire descendre l'ovale toutes les DELAY
     */
    @Override
    public void run(){
        boolean b = true;
        while(true){
            if(b) etat.moveDown();
            try{
                b = true;
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                b = false;
            }
        }
    }
}
