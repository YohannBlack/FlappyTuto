package View;

/**
 * Permet de repaindre toutes les 30 milsec
 */
class ThreadAffichage extends Thread {

    private final int DELAY = 150;
    private Affichage affichage;

    public ThreadAffichage(Affichage affichage) {
        this.affichage = affichage;
    }

    /**
     * Permet de redessiner au bout d'un certain temps
     */
    @Override
    public void run() {
        while (true) {
            affichage.revalidate();
            affichage.repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
