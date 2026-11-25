import itumulator.simulator.Actor;
import itumulator.world.*;
import java.util.*;

public class Grass implements Actor, NonBlocking {
    private double spreadRate = 0.5; 
    private World world;

    Grass(World world){ 
        // Græs skal kunne plantes med en position i verden
        this.world = world;
    }

    public void act(World world){
        if (new Random().nextDouble() < spreadRate) {
            spread(world);
        }
    }

    public void place(World world, Location location){
        //Græs placeres i verden
        world.setTile(location, this);
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
                    place(world, l);
                    break;
                }
            }
        }
    }

    public void spread(World world){
        //Græs spreder sig til nærliggende felter
        //Mangler at tage højde for andet græs
        Set<Location> emptyTiles = world.getEmptySurroundingTiles();
        Location[] emptyTileArray = emptyTiles.toArray(new Location[0]);
        place(world, emptyTileArray[new Random().nextInt(emptyTiles.size())]);; // Random int mellem 0 og set size
    }   

    //local motods
    private void deleteGrass(World world){
        world.delete(this);
    }
}
