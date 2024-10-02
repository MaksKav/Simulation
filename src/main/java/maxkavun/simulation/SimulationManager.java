package main.java.maxkavun.simulation;

import main.java.maxkavun.simulation.actions.InitActions;
import main.java.maxkavun.simulation.entity.Creature;
import main.java.maxkavun.simulation.map.SimulationMap;
import main.java.maxkavun.simulation.renderer.Renderer;
import java.util.concurrent.*;


public class SimulationManager {


    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start() {
        InitActions.initialMap();

        for (Creature creature : InitActions.creatures) {
            executor.submit(creature);
        }
        scheduler.scheduleAtFixedRate(this::runIteration, 0, 1, TimeUnit.SECONDS);
    }

    public void runIteration() {
        CountDownLatch latch = new CountDownLatch((int) InitActions.creatures.stream().filter(Creature::getIsAlive).count());

        for (Creature creature : InitActions.creatures) {
            if (creature.getIsAlive()) {
                executor.submit(() -> {
                    try {
                        synchronized (creature) {
                            creature.notify();
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                latch.countDown();
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Renderer.drawMap(SimulationMap.getInstance());
    }

    public void stop() {
        executor.shutdown();
        scheduler.shutdown();
    }
}