import itumulator.world.NonBlocking;
import itumulator.world.World;
import itumulator.world.Location;
import itumulator.executable.DisplayInformation;
import itumulator.executable.DynamicDisplayInformationProvider;
import java.awt.Color;

public class RabbitHole extends ObjectActor implements NonBlocking, DynamicDisplayInformationProvider {

    public RabbitHole(World world, Location location, Boolean isRandom){
        super(world, location, isRandom);
        if (isRandom) {
            randomPlace(world);
        } else if (location != null) {
            place(world, location, this);
        }
    }

    @Override
    public DisplayInformation getInformation() {
        return new DisplayInformation(Color.darkGray, "hole.png");
    }
}