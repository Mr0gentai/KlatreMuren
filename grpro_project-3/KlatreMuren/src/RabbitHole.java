import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.world.Location;

public class RabbitHole extends ObjectActor implements NonBlocking {

    public RabbitHole(World world, Location location, Boolean isRandom){ 
        super(world, location, isRandom);
        if (isRandom) {
            randomPlace(world);
        } else if (location != null) {
            place(world, location, this);
        }
    }
}