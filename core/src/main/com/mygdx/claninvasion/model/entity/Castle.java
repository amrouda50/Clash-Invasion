package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.function.Predicate;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Castle entity
 * TODO: Logic part is missing
 */
public final class Castle extends ArtificialEntity {
    private final Player player;
    private final Stack<Soldier> soldiers;
    public static int AMOUNT_OF_SOLDIERS = 1;
    private Pair<Integer, Integer> soldierPosition;

    public Castle(EntitySymbol symbol, Pair<Integer, Integer> position, Player player) {
        super(symbol, position);
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
     * @param amount - amount of injury
     * @see ArtificialEntity
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
        int money = 0;
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
            money += soldier.getCost();
        }
        run.test(money);
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

    /**
     * Damage attacked soldier
     * TODO Implement logic
     */
    public void damageOpponents() {
    }

    public Stack<Soldier> getSoldiers() {
        return soldiers;
    }
}
