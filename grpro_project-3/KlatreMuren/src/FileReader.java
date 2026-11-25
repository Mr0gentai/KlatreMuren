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
        
        while(scanner.hasNext()) { //tager ikke højde for 20-30 eks.
            String actor = scanner.next();
            int amount = scanner.nextInt();

            for(int i = 0; i < amount; i++) {
                if (actor.equals("rabbit")) {
                    Rabbit rabbit = new Rabbit(world, location, true);
                }
                if (actor.equals("grass")) {
                    Grass grass = new Grass(world, location, true);
                }
            }
        }

        //Overvej brugen af maps
        //Map<String, Integer> actorCounts = new HashMap<>();
        world.add(Object); //skal virke på vilkårlig actor

        scanner.close();
    }

}

    
