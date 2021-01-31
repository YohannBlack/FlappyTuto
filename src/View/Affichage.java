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

        //On commence le thread pour l'affichage
        (new ThreadAffichage(this, this.etat)).start();
    }

    public void endScreen() {
        JOptionPane.showMessageDialog(frame, "Votre score : " + etat.getScore(), "Game Over", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
    }


    /**
     * Used to paint the oval on the screen
     * @param g
     */
    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        super.paint(g);

        //Dessin de l'ovale
        int width = getWidth();
        int height = getHeight();

        //La largeur de l'ovale
        int widthOvale = width / 20;
        //Sa position en X
        int xOvale = 90;

        //La hauteur de l'ovalee
        int heightOvale = Etat.HEIGHT_OVALE;
        //Sa posisiton en Y
        int Y_OVALE = height - etat.getHauteur();

        //Selectionne la couleur et dessin
        g.setColor(Color.BLACK);
        g.drawOval(xOvale, Y_OVALE, widthOvale, heightOvale);


        //Dessin de la ligne brisee
        g.setColor(Color.RED);
        Point[] points = etat.getParcours();
        for(int i = 1; i < points.length; i++){
            //On trace un lignee entre deux points
            Point previousPoint = points[i-1];
            Point currentPoint = points[i];
            g.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y);
        }
    }
}
