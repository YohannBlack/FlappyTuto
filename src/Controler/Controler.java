package Controler;

import View.Affichage;
import Model.Etat;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controler implements MouseListener {

    //Attributs du controleur
    private Etat etat;
    private Affichage affichage;

    //Constructeur
    public Controler(Etat etat, Affichage affichage){
        this.etat = etat;
        this.affichage = affichage;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     *
     * make a jump when the mouse button has been clicked on the panel
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("Ok");
        etat.jump();
        this.affichage.repaint(); //Permet de redessiner l'oval apres un saut
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
