package utils;

import java.util.ArrayList;
import sterowanie.LogikaRozmyta;
import sterowanie.PanelRysowania;

import javax.swing.*;

public class MenadzerTrasa {


    private ArrayList<Predator> predators;
    private Prey prey;

    private double os_x;                                    // wspolrzedne wyjsciowe do sterowania
    private double os_y;

    private final int czuloscRuchu = 4;                       // zmiena odpowiedzialna za czulosc ruchu dla kazdego zbioru jesli dokonamy zmiany musi byc inna , by ruch wydawal sie liniowy nie skokowy
    private LogikaRozmyta fuzzy;

    public MenadzerTrasa() {
        this.predators = new ArrayList<>();
        fuzzy = new LogikaRozmyta();

    }


    public Prey getPrey() {
        return prey;
    }

    public double getOs_x() {
        return os_x;
    }

    public double getOs_y() {
        return os_y;
    }



    public void resetujProgram() {

        this.predators = new ArrayList<>(); // zerowanie punktow
        prey = null;                        // kasowanie ofiary

    }

    public void addPredator(int x, int y) {

        this.predators.add(new Predator(x, y));

    }

    public ArrayList<Predator> getPredators() {

        return predators;

    }

    public void addPrey(int x, int y) {
        this.prey = new Prey(x, y);

    }

    public boolean wykonajRuch(PanelRysowania panel) {

        // ruch wykonujemy tak dopoki nie ominiemy przeszkody
        if(prey !=null) {

            // auto nie jest na koncu mapy nastepuje ruch
            if (prey.getX() < panel.getWidth() - prey.getWidth() / 2) {

                // pobranie najbliszego punktu do ominiecja
                Predator najblisza = predators.get(0);

                double r = 0;

                // szukanie najbliszego punktu do ominiecia
                for (int i = 1; i < predators.size(); i++) {

                    Predator temp = predators.get(i);

                    r = najblisza.getDistanceFromPrey(prey);

                    if (r > temp.getDistanceFromPrey(prey)) {
                        // jesli znajdziemy blizszy zamieniamy
                        najblisza = temp;

                    }

                }

                // pobranie roznicy medzy drapieżnikiem a ofiarą
                int rx = najblisza.getX() - prey.getX();
                int ry = najblisza.getY() - prey.getY();

                System.out.println("rx " + rx + "  ry " + ry);
                if (Math.abs(rx) > 100) {                                         // maksymalna wartosc i minimalna zbiorow dla wartosci wejsciowych wynosi -100 i 100 
                    rx = rx > 0 ? 100 : -100;
                }
                if (Math.abs(ry) > 100) {
                    ry = ry > 0 ? 100 : -100;
                }
                fuzzy.Fuzzify(rx, ry);                                      // obliczenia zbiorow
                os_x = fuzzy.GetDirectionX();       //pobraniemetodasierodka ciezkosci warrtosci wyjsciowych 
                os_y = fuzzy.GetDirectionY();

                System.out.println("::os_x " + os_x + "  os_y " + os_y);
                prey.setX(prey.getX() + (int) os_x / czuloscRuchu);             // ustawianie wspolrzednych 4 oznacza czuloscruchu
                prey.setY(prey.getY() - (int) os_y / czuloscRuchu);

                //Poruszanie predatorami na podstawie pozycji ofiary
                for (Predator p : predators){


                        if(p.getX()<prey.getX()){
                            p.setX(p.getX()+2);
                        }else{
                            p.setX(p.getX()-2);
                        }

                        if(p.getY()<prey.getY()){
                            p.setY(p.getY()+2);
                        }else{
                            p.setY(p.getY()-2);
                        }

                }

                return true;

        } else {

            return false; // auto dojechalo do mety

        }
        }else {

            return false; // auto dojechalo do mety

        }
    }


}
