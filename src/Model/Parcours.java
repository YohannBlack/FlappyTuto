package Model;

import View.Affichage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Parcours {

    private final Random rand = new Random();

    private ArrayList<Point> points = new ArrayList<Point>();

    public Parcours(){
        initPosition();
    }

    private void initPosition(){
        //Point de depart
        points.add(new Point(0, 0));
        int x = 0;
        //Points au hasard dans la fenetre
        while(x < Etat.MAX_HEIGHT){
            x += 10 + rand.nextInt(30);
            int y = Etat.MIN_HEIGHT + rand.nextInt(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT);
            points.add(new Point (x, y));
        }

//        for(Point p : points) p.translate(Affichage.WIDTH/4, 0);
    }

    public Point[] getPoints(){
        Point[] res = new Point[points.size()];
        int i = 0;
        for(Point p : points){
            res[i] = new Point(p);
            i = i+1;
        }

        return res;
    }
}
