package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.Attacks;
import com.mygdx.claninvasion.model.player.Player;
import org.javatuples.Pair;

import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
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
        health = new AtomicInteger(level.current().getMaxHealth() + 1000);
        initHealth = health.get();
        soldiers = new Stack<>();
        this.player = player;
        soldierPosition = position;
    }

    private Pair<Integer, Integer> generateRandomSoldierPosition() {
        int randomX = ThreadLocalRandom.current().nextInt(-1,0);
        int randomY = ThreadLocalRandom.current().nextInt(-1, 0);
        return new Pair<>(
                soldierPosition.getValue0() + randomX,
                soldierPosition.getValue1() + randomY
        );
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
                soldier = new Barbarian(EntitySymbol.BARBARIAN, generateRandomSoldierPosition());
                soldier.setAttackType(Attacks.SWORD);

            } else if (entitySymbol == EntitySymbol.DRAGON) {
                soldier = new Dragon(EntitySymbol.DRAGON, generateRandomSoldierPosition());
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

    public void healHealthIncrease(int amount){
        this.health = new AtomicInteger(getHealth() + amount);
    }

    public Stack<Soldier> getSoldiers() {
        return soldiers;
    }
}
