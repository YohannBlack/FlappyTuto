package Model;

import java.awt.*;

public class Etat {

    /*** Constantes ***/
    private final int JUMP_HEIGHT = 15;
    private final int DOWN_SPEED = 3;
    public static final int MAX_HEIGHT = 500;
    public static final int MIN_HEIGHT = 150;
    public static final int MIN_WIDTH = -100;
    public final int HEIGHT_OVALE = 60;

    /*** Contante pour le nombre de points pour la ligne brisee ***/
    public static final int MAX_POINTS = 20;

    /*** Varaible de la hauteur de l'ovale ***/
    private int hauteur = 0;

    /*** La thread pour pouvoir faire retomber l'ovale ***/
    private ThreadDown fallDown;

    /*** Le parcours ***/
    private Parcours parcours;

    public Etat(){
        //On commence le thread pour faire tomber l'ovale
        this.fallDown = new ThreadDown(this);
        fallDown.start();

        //Initialisation du parcours
        parcours = new Parcours();
    }

    /**
     * Change the height of the oval with JUMP_HEIGHT
     */
    public void jump(){
        hauteur += JUMP_HEIGHT;
        if(hauteur + HEIGHT_OVALE > MAX_HEIGHT) hauteur = MAX_HEIGHT - HEIGHT_OVALE;

        fallDown.resetCounter();
    }

    /**
     * Permet de faire tomber l'ovale
     */
    public void moveDown(){
        hauteur -= DOWN_SPEED;
        if(hauteur < MIN_HEIGHT)
            hauteur = MIN_HEIGHT;
    }

    /**
     * Getter for HAUTEUR
     * @return HAUTEUR (height of the oval)
     */
    public int getHauteur(){
        return this.hauteur;
    }


    /**
     * Getter pour le parcours
     * @return un tableau de points
     */
    public Point[] getParcours() {
        return parcours.getPoints();
    }


    /**
     * Une thread pour pouvoir faire descendre l'ovale
     */
    class ThreadDown extends Thread {

        private Etat etat;
        private final int DELAY = 100;

        public ThreadDown(Etat e){
            this.etat = e;
        }

        public void resetCounter(){ this.interrupt();}

        /**
         * Permet de faire descendre l'ovale toutes les
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
}
