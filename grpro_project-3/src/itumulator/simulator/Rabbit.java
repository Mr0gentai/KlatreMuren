package itumulator.simulator;

import itumulator.world.World;

public class Rabbit implements Actor {
    int age;

    @Override
    public void act(World world) {
        // Implementation for rabbit's action
    }

    
    void eatGrass(World world) {
        
        System.out.println("yum yum");
    }

    void aging() {
        if (isdaytime()) {
            age++;
        }
    }

}