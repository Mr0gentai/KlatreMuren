import itumulator.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import itumulator.world.Location;

public abstract class ObjectActor {
    //superklasse for alle objekter, som deler fælles egenskaber.
    World world;

    ObjectActor(World world, Location location, Boolean isRandom) {
        this.world = world;
        if (isRandom) {
            randomPlace(world);
        } else if (location != null) {
            place(world, location, this);
        }
    }

    public void place(World world, Location location, Object object){
        //ObjectActor placeres i verden
        world.setTile(location, object);
    }

    public void randomPlace(World world){
        //ObjectActor placeres tilfældigt i verden
        Set<Location> occupiedLocations = new HashSet<>();

        for(int i = 0; i < world.getSize()*world.getSize(); i++){
            int x = new Random().nextInt(world.getSize());
            int y = new Random().nextInt(world.getSize());
            Location l = new Location(x, y);
            if (!occupiedLocations.contains(l)) {
                if (world.isTileEmpty(l)) {
                    occupiedLocations.add(l);
                } else {
                    place(world, l, this);
                    break;
                }
            }
        }
    }
}