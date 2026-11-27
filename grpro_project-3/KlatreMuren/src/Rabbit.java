import java.awt.Color;
import java.util.Random;

import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.World;


public class Rabbit implements Actor, DynamicDisplayInformationProvider {
    int age;
    int hunger;
    int energy;
    boolean sleeping;
    Random rn = new Random();
    
    public Rabbit(int age, int hunger) {
        this.age = age;
        this.hunger = hunger;
        if (age < 1 || age > 8) {
        this.energy = 1;
        }
        else {
        this.energy = 2;
        }   
        sleeping = false;
    }
        /* hvis kanin er ældre end 1 år sætte
        den til stor kanin samt checker om den sover
        det samme med lille kanin */
    @Override
    public DisplayInformation getInformation() {
        if (age > 1) {
        if (sleeping == false) {
            return new DisplayInformation(Color.gray, "rabbit-large.png"); }
            else {
                return new DisplayInformation(Color.gray, "rabbit-sleeping.png");
            }
    }
        else {
            if (sleeping == false)
            return new DisplayInformation(Color.gray, "rabbit-small.png");
            else {
                return new DisplayInformation(Color.gray, "rabbit-small-sleeping.png");
            }
        }
    }
    
    /* alt der sker i tic i verden skal skrives 
    her i void act*/
    @Override
    public void act(World world) {
        hungerGrows();
        
        // Kills rabbit if too hungry or old
    if (age > 10 || hunger > 12) {
        rabbitDie(world);
        }
    
    if (world.isDay() == true) {
        if (hunger > 5) {
            // move towards grass during daytime if hungry
            world.getSurroundingTiles();
            world.move(world.getTile(null), null);
        }
        else {
            // move randomly during daytime if not hungry
            world.getSurroundingTiles();
        }
    }

    if (world.isNight() == true) {
        // move towards at hole at night
    }

    }

    public void digsHole(World world) { //Kaninen skal kunne grave et hul under specifikke betingelser.
        if (age > 2 && hunger < 5) {
            RabbitHole hole = new RabbitHole();
            world.add(hole);
        }
    }
    // aging funktion
    void aging() {
            age++;
    }
    
    int getAge() {
        return age;
    }
    
    // increases hunger funktion
    void hungerGrows() {
        hunger++;
    }

    int getHunger() {
        return hunger;
    }

    void eatGrass() {
        System.out.println("yum yum");
        hunger = hunger - 8;
    }

    //rabbit die funktion
    void rabbitDie(World world) {
        System.out.println("pow! i am dead!");
        world.delete(this);
    }

    // call funktions for hole 
    void sleep() {
        sleeping = true;
    }

    void awake() {
        sleeping = false;
    }
}