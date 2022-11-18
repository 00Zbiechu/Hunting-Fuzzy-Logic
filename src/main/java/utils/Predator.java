package utils;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Predator {
    private int x;
    private int y;
    private int width = 50; // nie final bo nie połaczy sie przeszkod
    private int height = 50;


    public Predator(int x, int y) {
        this.x = x;
        this.y = y;
    }


    //Obliczanie odległości (c) z wzoru Pitagorasa na podstawie wartości X auta i przeszkody oraz Y auta i przeszkody
    public double getDistanceFromPrey(Prey prey){

        return Math.sqrt(Math.pow(this.x- prey.getX(),2)+Math.pow(this.y- prey.getY(),2));

    }
    

}
