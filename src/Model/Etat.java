package Model;

import java.awt.*;

public class Etat {

    /*** Constantes ***/
    private final int JUMP_HEIGHT = 35;
    private final int DOWN_SPEED = 10;
    public static final int MAX_HEIGHT = 500;
    public static final int MIN_HEIGHT = 150;
    public static final int MIN_WIDTH = -80;
    public static final int MAX_WIDTH = 900;
    public final int HEIGHT_OVALE = 60;

    /*** Varaible de la hauteur de l'ovale ***/
    private int hauteur = 0;

    /*** La thread pour pouvoir faire retomber l'ovale ***/
    private Voler fallDown;

    /*** Le parcours ***/
    private Parcours parcours;

    public Etat(){
        //On commence le thread pour faire tomber l'ovale
        this.fallDown = new Voler(this);
        fallDown.start();

        //Initialisation du parcours
        parcours = new Parcours();
        //Commencement du thread pour faire avancer le parcours
        (new Avancer(this.parcours, this)).start();
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

    public boolean testPerdu(){
        //Le X de depart de l'ovale
        float currentX = 90;
        //La distance entre deux points du parcours en X
        float totalX = this.parcours.getGAP();
        //La distance entre deux points du parcours en Y
        //On prends l'index = 1 car on enleve un point quand il sort de l'ecran
        float totalY = (this.parcours.getPoints()[1].y - this.parcours.getPoints()[0].y)  ;
        //Le Y de notre Ovale
        float currentY = (this.parcours.getPoints()[1].y - (currentX*totalY)/totalX);

        System.out.println("TotalY = " + totalY);
        System.out.println("CurrentY = " + currentY);
        System.out.println("this.hauteur = " + this.hauteur);
        
        return this.hauteur > currentY;
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
     * Getter pour le score
     * @return le score du parcours
     */
    public int getScore() { return parcours.getScore();}


    /**
     * Une thread pour pouvoir faire descendre l'ovale
     */
    class Voler extends Thread {

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
}
