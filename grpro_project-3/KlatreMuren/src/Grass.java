import itumulator.simulator.Actor;
import itumulator.world.Location;
import itumulator.world.NonBlocking;
import itumulator.world.World;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Grass implements Actor, NonBlocking {
    private double spreadRate = 0.5; 
    private World world;

    Grass(World world, Location location, Boolean isRandom){ 
        this.world = world;
        if (isRandom) {
            randomPlace(world);
        } else if (location != null) {
            place(world, location, this);
        }
    }

    public void act(World world){
        if (new Random().nextDouble() < spreadRate) {
            spread(world);
        }
    }

    public void place(World world, Location location, Object object){
        //Græs placeres i verden
        world.setTile(location, object);
    }

    public void randomPlace(World world){
        //Græs placeres tilfældigt i verden
        Set<Location> occupiedLocations = new HashSet<>();

        for(int i = 0; i < world.getSize()*world.getSize(); i++){
            int x = new Random().nextInt(world.getSize());
            int y = new Random().nextInt(world.getSize());
            Location l = new Location(x, y);
            if (!occupiedLocations.contains(l)) {
                if (world.containsNonBlocking(l)) {
                    occupiedLocations.add(l);
                } else {
                    place(world, l, this);
                    break;
                }
            }
        }
    }

    public void spread(World world){
        //Græs spreder sig til nærliggende felter
        Set<Location> emptyTiles = world.getSurroundingTiles();
        Location[] emptyTileArray = emptyTiles.toArray(new Location[0]);
        Set<Location> occupiedLocations = new HashSet<>();

        for(int i = emptyTileArray.length - 1; i >=0; i--){
            Location l = emptyTileArray[new Random().nextInt(emptyTiles.size())];
            if (!occupiedLocations.contains(l) && !world.containsNonBlocking(l)) {
                place(world, l, new Grass(world, null,false));
                break;
            } else if (emptyTiles.size() == occupiedLocations.size()) {
                break;
                //Hvis alle nærliggende felter er optaget, så stopper spredningen
                //Kan overvejes at lave en boolean der hedder "isSpreadable" som tjekker om der er plads til at sprede sig, og hvis ikke, så stopper spredningen
            } else {
                occupiedLocations.add(l);
                i++;
            }
        }
    }   

    //local motods
    private void deleteGrass(World world){
        world.delete(this);
    }
}