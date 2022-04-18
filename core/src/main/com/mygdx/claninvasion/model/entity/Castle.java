package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Castle entity
 * TODO: Logic part is missing
 */
public final class Castle extends ArtificialEntity {
    private final Player player;
    private final Stack<Soldier> soldiers;

    // topLeft, topRight, bottomRight, bottomLeft
    private Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> positions;
    public static int AMOUNT_OF_SOLDIERS = 1;
    private Pair<Integer, Integer> soldierPosition;

    public Castle(EntitySymbol symbol,  Quartet<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> positions, Player player) {
        super(symbol,  positions.getValue0());
        this.positions = positions;
        soldiers = new Stack<>();
        this.player = player;
        soldierPosition = symbol == EntitySymbol.CASTEL ? positions.getValue2() : positions.getValue0();
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

    public int getSoldiersMoneyCost() {
        Optional<Integer> sum = soldiers.stream().map(Soldier::getCost).reduce(Integer::sum);
        return sum.orElse(0);
    }

    public CompletionStage<Integer> trainSoldiers(EntitySymbol entitySymbol, Predicate<Integer> run) {
        ExecutorService executor = newFixedThreadPool(2);
        for (int i = 0; i < AMOUNT_OF_SOLDIERS; i++) {
            Soldier soldier;
            if (entitySymbol == EntitySymbol.BARBARIAN) {
                soldier = new Barbarian(EntitySymbol.BARBARIAN, soldierPosition);
            } else if (entitySymbol == EntitySymbol.DRAGON) {
                soldier = new Dragon(EntitySymbol.DRAGON, soldierPosition);
            } else {
                throw new IllegalArgumentException("No such soldier exists");
            }

            soldiers.add(soldier);
        }
        run.test(getSoldiersMoneyCost());

        CompletableFuture<Integer> supply = CompletableFuture.supplyAsync(() -> {
            int value = 0;
            for (Soldier soldier : soldiers) {
                try {
                    value += soldier.train(executor).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            return value;
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

    public Stack<Soldier> getSoldiers() {
        return soldiers;
    }
}
