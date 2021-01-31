package Model;

import View.Affichage;

import java.awt.*;
import java.util.ArrayList;

public class Etat {

    /*** Constantes ***/
    private final int JUMP_HEIGHT = 20;
    private final int DOWN_SPEED = 5;
    public static final int MAX_HEIGHT = 550;
    public static final int MIN_HEIGHT = 100;
    public static final int MIN_WIDTH = -120;
    public static final int MAX_WIDTH = 1200;
    public static final int HEIGHT_OVALE = 90;

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

    public boolean testPerdu() {
        //La liste des points du parcours
//        Point[] point = this.parcours.getPoints();
        ArrayList<Point> point = Parcours.points;

        int xOvale = 90 + (Affichage.WIDTH/20)/2;
        //Les points qui sont avant et apres notres ovale
        Point pp = new Point();
        Point np = new Point();

        for(int i = 1; i < point.size(); i++){
            //Si le point est apres notre ovale
            if(point.get(i).x > xOvale) {
                pp = point.get(i-1);
                np = point.get(i);
                //On s'arrete des qu'on a trouve les deux points
                break;
            }
        }

        //Calcule de la pente
        float m = (np.y - pp.y) / (float)(np.x - pp.x);
        float c = pp.y - m * pp.x;

        //Calcule du y
        float y = m * (90 + (Affichage.WIDTH/20)/2) + c;

        //la hauteur de l'ovale
        int Y_OVALE = Affichage.HEIGHT - this.hauteur;

        //On regarde si notre y est entre les dimension de l'ovale
        return (y > Y_OVALE) && (y < Y_OVALE + HEIGHT_OVALE);
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
}
