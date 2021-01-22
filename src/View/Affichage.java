package View;

import Controler.Controler;
import Model.Etat;

import javax.swing.*;
import java.awt.*;

public class Affichage extends JPanel{

    /*** Attributs de l'affichage ***/
    private Etat etat;
    private JFrame frame;

    /*** Constantes de la fentre d'affichage ***/
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

//    /*** Constantes de l'ovale ***/
//    private final int X_OVAL = 400;
//    private final int WIDTH_OVAL = 15;

    //Contructeur
    public Affichage(Etat etat){
        //Part1.Affichage depend d'un etat
        this.etat = etat;
        //Creation de la fenetre
        frame = new JFrame("Tuto1");
        frame.setLocation(500, 250);
        //Ajout de la fenetre d'affichage
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(this);
        //Ajout du controleur
        frame.addMouseListener(new Controler(this.etat, this)); // On ajoute un mouseListener pour pouvoir faire bouger l'oval

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        (new ThreadAffichage(this)).start();
    }


    /**
     * Used to paint the oval on the screen
     * @param g
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);

        //Dessin de l'ovale
        int width = getWidth();
        int height = getHeight();

        //On calcule une rapport pour que l'ovale soit toujours
        //proportionnelle a la fenetre
        double rapportH = height / (float)(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT);

        int widthOvale = width / 20;
        int xOvale = width / 4;

        int heightOvale = (int) (etat.HEIGHT_OVALE * rapportH);
        int yOvale = (int) (height - (etat.getHauteur() - Etat.MIN_HEIGHT) * rapportH - heightOvale);

        //Selectionne la couleur et dessin
        g.setColor(Color.BLACK);
        g.drawOval(xOvale, yOvale, widthOvale, heightOvale);



        //Dessin de la ligne brisee
        double rapportW = width / (float)(Etat.MAX_HEIGHT - Etat.MIN_HEIGHT);

        g.setColor(Color.RED);
        Point[] points = etat.getParcours();
        for(int i = 1; i < points.length; i++){
            Point previousPoint = transform(points[i-1], rapportW, rapportH, height);
            Point currentPoint = transform(points[i], rapportW, rapportH, height);
            g.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y);
        }
    }

    private Point transform (Point p, double rapportW, double rapportH, int height) {
        int x = (int) (p.x*rapportW);
        int y = (int) (height - (p.y - Etat.MIN_HEIGHT) * rapportH);
        return new Point(x, y);
    }





    /**
     * Permet de repaindre toutes les 30 milsec
     */
    class ThreadAffichage extends Thread {

        private final int DELAY = 30;
        private Affichage affichage;

        public ThreadAffichage(Affichage affichage){
            this.affichage = affichage;
        }

        @Override
        public void run(){
            while(true){
                affichage.repaint();
                try{
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}