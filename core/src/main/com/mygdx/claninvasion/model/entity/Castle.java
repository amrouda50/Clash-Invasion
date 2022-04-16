package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.Player;
import org.javatuples.Pair;
import org.javatuples.Quartet;

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
    private Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> positions;
    private static int AMOUNT_OF_SOLDIERS = 2;
    private Pair<Integer, Integer> soldierPosition = new Pair<>(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public Castle(EntitySymbol symbol,  Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> positions, Player player) {
        super(symbol,  positions.getValue0());
        this.positions = positions;
        soldiers = new ArrayList<>();
        this.player = player;
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
        soldiers.clear();
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

    public boolean isInsidePosition(Pair<Integer, Integer> pair) {
        if (pair.equals(positions.getValue0())) return true;
        if (pair.equals(positions.getValue1())) return true;
        if (pair.equals(positions.getValue2())) return true;
        return pair.equals(positions.getValue3());
    }

    public Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>>
    getPositions() {
        return positions;
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
