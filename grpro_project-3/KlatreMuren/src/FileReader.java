import itumulator.executable.Program;
import itumulator.world.World;
import itumulator.world.Location;
import itumulator.executable.DisplayInformation;
import java.awt.Color;

import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class FileReader { 
    int display_size = 800;
    int delay = 1000;

    public void readFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int size = scanner.nextInt();

        Program p = new Program(size, display_size, delay); 
        World world = p.getWorld();

        Random random = new Random();

        while(scanner.hasNext()) {
            String actor = scanner.next();
            int amount = scanner.nextInt();

            for(int i = 0; i < amount; i++) {
                Object worldObject;

                if(actor.equals("rabbit")) {
                    worldObject = new Rabbit(world, null, false,
                        random.nextInt(1, 9), random.nextInt(0, 13));
                } else if(actor.equals("burrow")) {
                    worldObject = new RabbitHole(world, null, false);
                } else if(actor.equals("grass")) {
                    worldObject = new Grass(world, null, false);
                } else {
                    continue;
                }
                    int x = random.nextInt(size);
                    int y = random.nextInt(size);

                    Location location = new Location(x, y);

                    world.setTile(location, worldObject);
                }
            }
            scanner.close();
            p.show();
        }
    }
