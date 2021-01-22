package Model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Parcours {

    //Une variable Random qui va nous permettre d'initialiser les points du parcours
    private final Random rand = new Random();

    //L'array des points du parcours
    private ArrayList<Point> points = new ArrayList<Point>();

    /*** Constante pour l'avancement de la ligne ***/
    private final int SPEED = 2;

    /*** Constructeur ***/
    public Parcours(){
        initPosition();
    }

    /**
     * Permet d'initialiser les positions des points du parcours
     */
    private void initPosition(){
        //Point de depart
        points.add(new Point(0, 0));
        int x = 0;
        //Points au hasard dans la fenetre
        while(x < Etat.MAX_HEIGHT){
            //On change le x
            x += 10 + rand.nextInt(30);
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
        return new Point(p.x + 10 + rand.nextInt(30), Etat.MIN_HEIGHT + rand.nextInt(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT));
    }

    /**
     * Bouge, ajoute, et enleve les points
     * @return une liste le points bouges
     */
    private ArrayList<Point> movedPoints(){
        //On creer notre liste qu'on voudra rendre
        ArrayList<Point> res = new ArrayList<>();
        //On parcours nos points
        for(Point p : points){
            //On regarde si le point est toujours dans les limites de la 'fenetre'
            if((p.x - SPEED) < Etat.MIN_WIDTH) {
                //On l'enleve si ce n'est pas le cas
                points.remove(p);
                //On creer un nouveau point a la fin de notre liste
                points.add(randPoint(points.get(points.size()-1)));
            } else {
                //Sinon on fait bouger notre point vers l'arriere
                p.x -= SPEED;
                //On le rajoute a notre liste a rendre
                res.add(p);
            }
        }
        return res;
    }

    /**
     * Change la position des points et
     * @return les points a afficher
     */
    public Point[] getPoints(){
        //La liste de points bouges
        ArrayList<Point> mPoints = movedPoints();
        //Le tableau de points a rendre
        Point res[] = new Point[mPoints.size()];
        int i = 0;
        //On parcours tous les points deja bouge
        for(Point p : mPoints) {
            //On les rajoute au tableau qu'on passera a l'affichage
            res[i] = p;
            i = i+1;
        }
        return res;
    }
}
