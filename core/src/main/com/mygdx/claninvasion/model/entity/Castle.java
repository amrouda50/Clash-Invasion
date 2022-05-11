package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;

import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Castle entity
 */
public final class Castle extends ArtificialEntity {
    private final Player player;
    private final Stack<Soldier> soldiers;
    public static int AMOUNT_OF_SOLDIERS = 1;
    private final Pair<Integer, Integer> soldierPosition;
    private final int mapsize;


    /**
     * @param symbol - sprite type (location, name etc.)
     * @param position - position in the cells array
     * @param player - player which has current castle
     * @param mapsize - size of the map, helps identifying if entity is not creatable
     */
    public Castle(EntitySymbol symbol, Pair<Integer, Integer> position, Player player , int mapsize, HealthBar healthBar) {
        super(symbol, position ,  mapsize);
        this.mapsize = mapsize;
        health = new AtomicInteger(level.current().getMaxHealth() + 1000);
        hpBar = healthBar;
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

    /**
     * Not possible for this entity
     * @param position - position where to go
     */
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

    public CompletionStage<Integer> trainSoldiers(EntitySymbol entitySymbol, Predicate<Integer> run, AttackType attackType) {
        ExecutorService executor = newFixedThreadPool(2);
        int money = 0;
        for (int i = 0; i < AMOUNT_OF_SOLDIERS; i++) {
            Soldier soldier;
            if (entitySymbol == EntitySymbol.BARBARIAN) {
                soldier = new Barbarian(EntitySymbol.BARBARIAN, generateRandomSoldierPosition() , this.mapsize);
            } else if (entitySymbol == EntitySymbol.DRAGON) {
                soldier = new Dragon(EntitySymbol.DRAGON, generateRandomSoldierPosition() , this.mapsize);
            } else {
                throw new IllegalArgumentException("No such soldier exists");
            }
            soldier.setAttackType(attackType);
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
     * @return - trained soldiers
     */
    public Stack<Soldier> getSoldiers() {
        return soldiers;
    }
}
