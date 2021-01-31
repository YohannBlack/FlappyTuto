package View;

import Model.Etat;

/**
 * Permet de repaindre toutes les 30 milsec
 */
class ThreadAffichage extends Thread {

    private final int DELAY = 150;
    private Affichage affichage;
    private Etat etat;

    public ThreadAffichage(Affichage affichage, Etat etat) {
        this.etat = etat;
        this.affichage = affichage;
    }

    /**
     * Permet de redessiner au bout d'un certain temps
     */
    @Override
    public void run() {
        //Tant qu'on a pas perdu on continue a redesisner
        while (etat.testPerdu()) {
            //Redessine
            affichage.revalidate();
            affichage.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Aaffichage de l'ecran de fin de partie qunad on perd
        affichage.endScreen();
    }
}
