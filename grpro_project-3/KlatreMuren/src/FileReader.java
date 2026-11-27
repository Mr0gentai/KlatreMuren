import itumulator.executable.Program;
import itumulator.world.World;

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

        while(scanner.hasNext()) {
            String actor = scanner.next();
            int amount = scanner.nextInt();

            for(int i = 0; i < amount; i++) {
                if(actor.equals("rabbit")) {
                    world.add(new Rabbit(new Random().nextInt(10), new Random().nextInt(10))); //Mangler et rabbit objekt

                } else if(actor.equals("rabbit_hole")) {

                    Random random = new Random();

                    int x = random.nextInt(size);
                    int y = random.nextInt(size);

                    Rabbithole hole = new Rabbithole();

                    Location location = new Location(x, y);
                }
            }
        }
        scanner.close();
        p.show();
    }
}   

