package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.entity.attacktype.AttackTypeArcher;
import com.mygdx.claninvasion.model.entity.attacktype.Attacks;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import com.mygdx.claninvasion.model.map.WorldCell;
import org.javatuples.Pair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Tower extends ArtificialEntity implements Defensible {
    private final int radius = 4;
    public static int COST = 200;

    public Level level;

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(Attacks attackType) {
        if(attackType == Attacks.ARCHER) {
            this.attackType = new AttackTypeArcher();
        }
    }

    public AttackType attackType;
    public WorldCell worldCell;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        super(entitySymbol, position);

        level = Levels.createTowerLevelIterator();
        health = new AtomicInteger();
        super.setHealth(level.current().getMaxHealth());
        this.towerLevel = towerLevel;
    }

    Tower(LevelIterator<Level> levelIterator) {
        super(levelIterator);
    }

    @Override
    public void damage(int amount) {
        if (isAlive()) {
            super.damage(amount);
        } else {
            remove();
        }
    }

    @Override
    public AtomicLong getPercentage() {
        return super.getPercentage();
    }

    private void remove() {
    }

    @Override
    public void heal() {
        super.heal();
    }

    public boolean canFire(ArtificialEntity entity) {
        float distance = getVec2Position().dst(
                entity.getVec2Position().x,
                entity.getVec2Position().y
        );

        return distance <= radius;
    }

    @Override
    public void attack(ArtificialEntity artificialEntity) {
        reactionTime.get();

        if (!artificialEntity.isAlive()) {
            return;
        }

        artificialEntity.setDecreaseHealth(85);
    }


    public boolean checkNeighbors(int radius) {
        //Implementation to check the neigbors within a radius
        return false;
    }

    public List<Pair<Integer,Integer>> getNeighbors() {
        //Implementation to get neighbors from certain point
        return null;
    }

    public void damageEnemy(Pair<Integer, Integer> neighbor) {

    }
}

