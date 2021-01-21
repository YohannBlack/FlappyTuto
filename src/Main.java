import Model.Etat;
import View.Affichage;

public class Main {

    public static void main(String [] args){
        Etat etat = new Etat();
        Affichage vue = new Affichage(etat);
    }

}
