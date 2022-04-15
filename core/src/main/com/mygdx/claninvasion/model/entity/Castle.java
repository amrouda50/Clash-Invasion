package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.Player;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.concurrent.*;
import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Castle entity
 * TODO: Logic part is missing
 */
public class Castle extends ArtificialEntity {
    private final Player player;
    private final ArrayList<Soldier> soldiers;

    public Castle(Player player) {
        super(EntitySymbol.CASTEL,  new Pair<>(0, 0));
        this.player = player;
        soldiers = new ArrayList<>();
    }
    /**
     * @see ArtificialEntity
     * @param amount - amount of injury
     */
    @Override
    public void damage(int amount) {
        this.health.set(this.health.get() - amount);
    }

    public CompletionStage<Boolean> trainSoldiers() {
        ExecutorService executor = newFixedThreadPool(2);
        soldiers.clear();
        for (int i = 0; i < 10; i++) {
            soldiers.add(new Soldier(EntitySymbol.DRAGON, position));
        }

        CompletableFuture<Boolean> supply = CompletableFuture.supplyAsync(() -> {
            for (Soldier soldier : soldiers) {
                try {
                    soldier.train(executor).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }, executor);

        supply.whenComplete((a, b) -> executor.shutdownNow());

        return supply;
    }

    /**
     * Damage attacked soldier
     * TODO Implement logic
     */
    public void damageOpponents() {}

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
}
