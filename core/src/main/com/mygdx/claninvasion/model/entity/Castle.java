package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.Stack;
import java.util.concurrent.*;
import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Castle entity
 * TODO: Logic part is missing
 */
public final class Castle extends ArtificialEntity {
    private final Player player;
    private final Stack<Soldier> soldiers;
    private static int AMOUNT_OF_SOLDIERS = 1;
    private Pair<Integer, Integer> soldierPosition;

    public Castle(EntitySymbol symbol,  Pair<Integer, Integer> position, Player player) {
        super(symbol,  position);
        soldiers = new Stack<>();
        this.player = player;
        soldierPosition = position;
        soldierPosition = new Pair<>(soldierPosition.getValue0() - 3, soldierPosition.getValue1() - 3);
    }

    @Override
    public void changePosition(Pair<Integer, Integer> position) {
        throw new RuntimeException("Can not change castle position");
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
        for (int i = 0; i < AMOUNT_OF_SOLDIERS; i++) {
            Soldier soldier = new Soldier(EntitySymbol.BARBARIAN, soldierPosition);
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

    public Stack<Soldier> getSoldiers() {
        return soldiers;
    }
}
