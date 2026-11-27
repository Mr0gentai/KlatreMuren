import java.awt.Color;
import java.util.*;
import itumulator.executable.*;
import itumulator.simulator.Actor;
import itumulator.world.World;
import itumulator.world.Location;


public class Rabbit extends ObjectActor implements Actor, DynamicDisplayInformationProvider {
    private int age;
    private int hunger;
    private int energy;
    private boolean sleeping;
    private Random rn = new Random();
    private int viewDistance;
    private RabbitHole home;
    
    public Rabbit(World world, Location location, boolean isRandom, int age, int hunger) {
        super(world, location, isRandom);
        this.age = age;
        this.hunger = hunger;
        this.energy = (age < 1 || age > 8) ? 1 : 2;
        this.sleeping = false;
        this.viewDistance = 2;
    }
        /* hvis kanin er ældre end 1 år sætte
        den til stor kanin samt checker om den sover
        det samme med lille kanin */
    @Override
    public DisplayInformation getInformation() {
        if (age > 1) {
            return sleeping
                ? new DisplayInformation(Color.gray, "rabbit-sleeping.png")
                : new DisplayInformation(Color.gray, "rabbit-large.png");
        }
        return sleeping
            ? new DisplayInformation(Color.gray, "rabbit-small-sleeping.png")
            : new DisplayInformation(Color.gray, "rabbit-small.png");
    }

    /* alt der sker i tic i verden skal skrives 
    her i void act*/
    @Override
    public void act(World world) {
        hungerGrows();

        if (world.getCurrentTime() == 0) {
            aging();
        }
        if (age > 10) {
            rabbitDie(world);
            return;
        }

        if (hunger > 12) {
            rabbitDie(world);
            return;
        }
    
        if (world.isDay()) {
            awake();
            if (hunger > 5) {
            if (standingOnGrass(world) == true) {
                eatGrass(world);
            } else {
                moveTowardsGrass(world);
            }
            } else {
                moveRandomly(world);
            }
        }
        else {
            sleep();
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

        if (!steps.isEmpty()) {
            world.move(this, steps.get(rn.nextInt(steps.size())));
        } else {
            moveRandomly(world);
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

    public void digsHole(World world) {
        if (home == null) {
            Location location = world.getLocation(this);
            home = new RabbitHole(world, location, false);
        }
    }

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
        hunger = hunger - 8;
        Location current = world.getLocation(this);
        Object grass = world.getNonBlocking(current);
        if (grass instanceof Grass) {
            world.delete(grass);
        }
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