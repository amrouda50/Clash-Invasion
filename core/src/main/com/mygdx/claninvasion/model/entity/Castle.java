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
    private Player player;
    private final ArrayList<Soldier> soldiers;
    private static int AMOUNT_OF_SOLDIERS = 10;

    public Castle(EntitySymbol symbol, Pair<Integer, Integer> position, Player player) {
        super(symbol,  position);
        soldiers = new ArrayList<>();
        this.player = player;
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
        for (int i = 0; i < AMOUNT_OF_SOLDIERS; i++) {
            Soldier soldier = (Soldier) player.getMap().createMapEntity(EntitySymbol.BARBARIAN, position, null);
            soldiers.add(soldier);
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
