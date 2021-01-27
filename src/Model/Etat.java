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
    public static final int MIN_WIDTH = -75;
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

    /**
     * Permet de voir si a partir d'une equation lineaire
     * si la ligne passe par une ellipse
     * @param p1 le premier point
     * @param p2 le second point
     * @param centreX le centre de l'ellipse en X
     * @param centreY le cenntre de l'ellipse en Y
     * @param rayonV le rayon vertical de l'ellipse
     * @param rayonH le rayon horizontal de l'ellipse
     * @return vrai s'il y a intersection
     */
    private boolean Intersection(Point p1, Point p2, int centreX, int centreY, int rayonV, int rayonH){
        ArrayList<Point> intersect = new ArrayList<>();
        //Translation pour que le point 0, 0 soit le centre de l'ovale
        int x1 = p1.x - centreX;
        int y1 = p1.y - centreY;;

        int x2 = p2.x - centreX;
        int y2 = p2.y - centreY;

        //Cas extreme qui ne devrait normalement jamais arriver
        //Ou la pente est verticale
        if(x1 == x2){
            double y = (double) (rayonV/rayonH) * Math.sqrt(rayonH*rayonH - x1*x1);
            if(Math.min(y1, y2) <= y && y <= Math.max(y1, y2)) {
                intersect.add(new Point(x1 + centreX, (int) (y + centreY)));
//                return true;
            }
            if(Math.min(y1, y2) <= -y && -y <= Math.max(y1, y2)) {
                intersect.add(new Point(x1 + centreX, (int) (-y + centreY)));
//                return true;
            }
        } else {
            //On calcule l'equation lineaire y = mx + c
            double m = (double) (y2 - y1) / (x2 - x1);
            double c = (y1 - m*x1);

            //Les parties d'une equation du second degre
            //Ax^2 + Bx + C
            double A = m*m * rayonH*rayonH + rayonV*rayonV;
            double B = 2*m*c * rayonH*rayonH;
            double C = rayonH*rayonH * c*c - rayonH*rayonH * rayonV*rayonV;

            //Calcule du discriminant B^2 - 4AC
            double D = B*B - 4 * A * C;

            System.out.println(D);

            if(D > 0) {
                //Calcule des coordonnees des racines
                double rx1 = (-B + Math.sqrt(D)) / (2*A);
                double rx2 = (-B - Math.sqrt(D)) / (2*A);

                double ry1 = m * rx1 + c;
                double ry2 = m * rx2 + c;

                //On regarde si notre point d'interseciton est bien dans notre ligne
                if(estDansLaLigne(new Point(x1, y1), new Point(x2, y2), new Point((int)rx1, (int)ry1))) {
                    //On le rajoute a la liste des points d'intersections
                    intersect.add(new Point((int)rx1, (int)ry1));
                }
                //On regarde si notre point d'interseciton est bien dans notre ligne
                if(estDansLaLigne(new Point(x1, y1), new Point(x2, y2), new Point((int)rx2, (int)ry2))) {
                    //On le rajoute a la liste des points d'intersections
                    intersect.add(new Point((int)rx2, (int)ry2));
                }
            } else if (D == 0) {
                //Calcule les coordonnees de notre seul racine
                double rx = -B / (2*A);
                double ry = m * rx + c;

                //On regarde si notre point d'interseciton est bien dans notre ligne
                if(estDansLaLigne(new Point(x1, y1), new Point(x2, y2), new Point((int)rx, (int)ry))) {
                    //On le rajoute a la liste des points d'intersections
                    intersect.add(new Point((int)rx, (int)ry));
                }
            }
//            return D >= 0;
        }
        return intersect.size() > 0;
    }

    /**
     * Permet de verifier si un point est dans les extremites d'une ligne
     * @param p1 Une extremiter de la ligne
     * @param p2 L'autre extremiter de la ligne
     * @param x Le point
     * @return vrai si les coordonners de X sont compris entre les extremiters
     */
    private boolean estDansLaLigne(Point p1, Point p2, Point x){
        double xMin = Math.min(p1.x, p2.x);
        double xMax = Math.max(p1.x, p2.x);

        double yMin = Math.min(p1.y, p2.y);
        double yMax = Math.max(p1.y, p2.y);

        return (xMin <= x.x && x.x <= xMax) && (yMin <= x.y && x.y <= yMax);
    }


    /**
     * Permet de savoir s'il y a collision ou non
     * @return vrai si c'est le cas
     */
    public boolean testPerdu(){
        //Les points du parcours
        Point p1 = this.parcours.getPoints()[0];
        Point p2 = this.parcours.getPoints()[1];
        Point p3 = this.parcours.getPoints()[2];

        double rapportH = Affichage.HEIGHT / (float)(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT);
        //La hauteur de l'ovale
        int heightOvale = (int) (60 * rapportH);
        //Le centre en Y de l'ovale
        int y = this.hauteur + (heightOvale/2);

        //La largeur de l'ovale
        int widthOvale = Affichage.WIDTH/20;

        //Les rayons horizontal et vertical
        int rayonH = widthOvale / 2;
        int rayonV = heightOvale/ 2;

        //On regarde s'il y a intersection
        System.out.println(Intersection(p1, p2, 90 + (Affichage.WIDTH/20)/2, 498 + (heightOvale/2), rayonV, rayonH));
        return !Intersection(p1, p2, 90 + (Affichage.WIDTH/20)/2, 498 + (heightOvale/2), rayonV, rayonH);
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
