package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import org.javatuples.Pair;

import java.util.concurrent.CompletableFuture;

public class Tower extends ArtificialEntity implements Defensible {
    public static int COST = 200;
    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

    }

    Tower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }

    @Override
    public void damage(int amount) {
        super.damage(amount);
    }

    @Override
    public void heal() {
        super.heal();
    }

    public static int RADIUS = 2;

    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );

        return distance <= RADIUS;
    }


    public void attack(ArtificialEntity artificialEntity) {
        if (!artificialEntity.isAlive()) {
            return;
        }
        artificialEntity.setDecreaseHealth(10);
        System.out.println("Descresing.. Current is" + artificialEntity.getHealth() + ", Entity " + artificialEntity);
    }

    @Override
    public CompletableFuture<Boolean> attack(ArtificialEntity artificialEntity, Fireable fire) {
        float distance = getVec2Position().dst(
                artificialEntity.getVec2Position().x,
                artificialEntity.getVec2Position().y
        );

//        if (distance > 2) {
//            return CompletableFuture.supplyAsync(() -> false);
//        }

//        if (!artificialEntity.isAlive()) {
//            System.out.println("Exited...");
//            return CompletableFuture.supplyAsync(() -> false);
//        }

//        CompletableFuture<Boolean> future = CompletableFuture
//                .supplyAsync(() ->{
//                    try {
//                        Thread.sleep(400);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return true;
//                });
//        future
//                .thenAccept(a -> artificialEntity.damage(100))
//                .thenAccept(a -> System.out.println("Attack by tower was completed: " +
//                        artificialEntity.getHealth() + ", Entity " + artificialEntity))
//                .completeExceptionally(new RuntimeException("Could not finish the defend method"));
//        return future;

        return null;
    }
}
