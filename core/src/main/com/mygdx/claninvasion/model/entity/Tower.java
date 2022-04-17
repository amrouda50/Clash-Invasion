package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.map.WorldCell;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Tower extends ArtificialEntity implements Defensible {

    public Level level;
    public AttackType attackType;
    int towerLevel;
    public RomanFort romanFort;
    public StrategicTower strategicTower;
    public HillTower hillTower;

    public WorldCell worldCell;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int towerLevel) {
        super(entitySymbol, position);
        this.towerLevel = towerLevel;
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


    @Override
    public CompletableFuture<Boolean> attack(ArtificialEntity artificialEntity, Fireable fire) {
        float distance = getVec2Position().dst(
                artificialEntity.getVec2Position().x,
                artificialEntity.getVec2Position().y
        );

        if (distance > 2) {
            return CompletableFuture.supplyAsync(() -> false);
        }

        if (!artificialEntity.isAlive()) {
            return CompletableFuture.supplyAsync(() -> false);
        }

        CompletableFuture<Boolean> future = CompletableFuture
                .supplyAsync(() -> fire.fire(position, artificialEntity.position).join());
        future
                .orTimeout(3, SECONDS)
                .thenAccept(a -> artificialEntity.damage(100))
                .thenAccept(a -> System.out.println("Attack by tower was completed"))
                .completeExceptionally(new RuntimeException("Could not finish the defend method"));
        return future;
    }

    public void attack(List<Pair<Integer,Integer>> neighbors, List<Soldier> soldiers) {
            if(towerLevel == 0) {
                System.out.println("Tower level is level0."); //Assuming Roman Fort is tower level 0 as it could not be confirmed
            }
    }

}
