//import java.awt.Color;

//import itumulator.executable.DisplayInformation;
import itumulator.executable.Program;
import java.io.FileNotFoundException;
//import itumulator.world.Location;
//import itumulator.world.World;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        FileReader reader = new FileReader();
        reader.readFile("Import/week-1/t1-1a.txt");

        //World w = p.getWorld();

        //w.setTile(new Location(0, 0), new <MyClass>());

        //p.setDisplayInformation(<MyClass>.class, new DisplayInformation(<Color>, "<ImageName>"));



        p.show();
    }
}