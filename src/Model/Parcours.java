package Model;

import View.Affichage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Parcours {

    //Une variable Random qui va nous permettre d'initialiser les points du parcours
    private final Random rand = new Random();

    //L'arrayListe des points du parcours
    private ArrayList<Point> points = new ArrayList<Point>();

    private final int GAP = 120;

    /*** Constante pour l'avancement de la ligne ***/
    private final int SPEED = 2;

    //Variable pour le score
    private int score = 0;

    /*** Constructeur ***/
    public Parcours(){
        initParcours();
    }

    /**
     * Permet d'initialiser les positions des points du parcours
     * de facon aleatoire
     */
    private void initParcours(){
        int rapportH = (int) (Affichage.HEIGHT / (float) (Etat.MAX_HEIGHT - Etat.MIN_HEIGHT));
        int heightOvale = 60 * rapportH;
        //Point de depart
        Point p = new Point(90 + (Affichage.WIDTH/20)/2, 498 + (heightOvale/2));
        points.add(p);
        int x = p.x;
        //Points au hasard dans la fenetre
        while(x < Etat.MAX_WIDTH){
            //On change le x
            x += GAP;
            //On creer un y random
            int y = Etat.MIN_HEIGHT + rand.nextInt(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT);
            points.add(new Point (x, y));
        }
    }

    /**
     * Creer un point au hasard
     * @Param un point (le dernier point de la liste point)
     * @return un point
     */
    private Point randPoint(Point p){
        return new Point(p.x + GAP, Etat.MIN_HEIGHT + rand.nextInt(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT));
    }

    /**
     * Change la position des points
     * Creer et enleve les points pour une ligne infini
     * @return les points a afficher
     */
    public Point[] getPoints(){
        //On prend le premier point du parcours
        Point p = points.get(0);
        //On verifie qu'il est toujours dans les limites qu'on a defini
        if(p.x - SPEED < Etat.MIN_WIDTH) {
            //Si ce n'est pas le cas on l'enleve
            points.remove(p);
            //Et on rajoute un point a la fin
            points.add(randPoint(points.get(points.size() - 1)));
        }
        //Le tableau de points a rendre
        Point res[] = new Point[points.size()];
        int i = 0;
        //On parcours tous les points deja bouge
        for(Point po : points) {
            //On les rajoute au tableau qu'on passera a l'affichage
            res[i] = po;
            i = i+1;
        }
        return res;
    }

    /**
     * Getter pour l'ecart entre les X des points
     * @return l'ecart
     */
    public int getGAP(){
        return this.GAP;
    }

    /**
     * GETTER pour le score
     * @return le score
     */
    public int getScore(){ return this.score; }

    /**
     * Permet de changer la position des points du parcours
     */
    public void setPosition(){
        score += SPEED;
        for (Point point : points) point.x -= SPEED;
    }
}
