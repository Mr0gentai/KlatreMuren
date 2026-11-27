import java.awt.Color;
import java.util.*;
import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.World;
import itumulator.world.Location;


public class Rabbit implements Actor, DynamicDisplayInformationProvider {
    int age;
    int hunger;
    int energy;
    boolean sleeping;
    Random rn = new Random();
    int viewDistance;

    
    Rabbit(int age, int hunger) {
        this.age = age;
        this.hunger = hunger;
        if (age < 1 || age > 8) {
        this.energy = 1;
        }
        else {
        this.energy = 2;
        }   
        sleeping = false;
        this.viewDistance = 2;
    
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

        if (world.getCurrentTime() == 0) {
            aging();
        }
        

        // Rabbit dies if too hungry or old
    if (age > 10) {
        rabbitDie(world);
        System.out.println("A rabbit has died from old age");
        }

    if (hunger > 12) {
        rabbitDie(world);
        System.out.println("A rabbit has died from hunger");
    }
    
    if (world.isDay() == true) {
        if (hunger > 5) {
            if (standingOnGrass(world) == true) {
                eatGrass(world);
            }
            else {
            moveTowardsGrass(world);
            
            }
        }
        else {
            // moves randomly during daytime if not hungry
            moveRandomly(world);
    }

    }
    

    }

    private boolean standingOnGrass(World world) {
        Location current = world.getLocation(this);
        return world.containsNonBlocking(current) && world.getNonBlocking(current) instanceof Grass;
    }

    private void moveTowardsGrass(World world) {
        Location current = world.getLocation(this);
        if (world.containsNonBlocking(current) && world.getNonBlocking(current) instanceof Grass) {
            return;
        }

        Location target = null;
        int nearestDistance = Integer.MAX_VALUE;
        for (Location tile : world.getSurroundingTiles(current, viewDistance)) {
            if (world.containsNonBlocking(tile) && world.getNonBlocking(tile) instanceof Grass) {
                int distance = distance(tile, current);
                if (distance < nearestDistance || (distance == nearestDistance && rn.nextBoolean())) {
                    target = tile;
                    nearestDistance = distance;
                }
            }
        }

        if (target == null) {
            moveRandomly(world);
            return;
        }

        List<Location> steps = new ArrayList<>();
        for (Location tile : world.getEmptySurroundingTiles(current)) {
            if (distance(tile, target) < distance(current, target)) {
                steps.add(tile);
            }
        }
       
    }

    private void moveRandomly(World world) {
        Location current = world.getLocation(this);
        List<Location> available = new ArrayList<>(world.getEmptySurroundingTiles(current));
        available.add(current);

            world.move(this, available.get(this.rn.nextInt(available.size())));
        }
    

    private int distance(Location first, Location second) {
        return Math.max(
                Math.abs(first.getX() - second.getX()),
                Math.abs(first.getY() - second.getY()));
    }

    // private void moveTowardsHole(World world) 
        // This method is currently not implemented. It can be used to move the rabbit towards a hole in the future.
        
    

    private void aging() {
          age++;
        }
    
    
    public int getAge() {
        return age;
    }

    private void hungerGrows() {
        hunger++;
    }

    public int getHunger() {
        return hunger;
    }

    void eatGrass(World world) {
        System.out.println("yum yum");
        hunger = hunger - 8;
        Location current = world.getLocation(this);
        world.delete(world.getNonBlocking(current) instanceof Grass);

    }

    //rabbit die funktion
    void rabbitDie(World world) {
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