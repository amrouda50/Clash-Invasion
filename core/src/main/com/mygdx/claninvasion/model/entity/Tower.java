package com.mygdx.claninvasion.model.entity;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.claninvasion.model.Globals;
import com.mygdx.claninvasion.model.entity.attacktype.AttackType;
import com.mygdx.claninvasion.model.level.GameSoldierLevelIterator;
import com.mygdx.claninvasion.model.level.GameTowerLevel;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import com.mygdx.claninvasion.model.map.WorldCell;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Tower extends ArtificialEntity implements Defensible {
    private final int radius = 4;
    public static int COST = 200;

    public Level level;
    public AttackType attackType;
    int towerLevel;
    public RomanFort romanFort;
    public StrategicTower strategicTower;
    public HillTower hillTower;

    public WorldCell worldCell;

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position, int towerLevel) {
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

    public void attack(List<Pair<Integer,Integer>> neighbors, List<Soldier> soldiers) {
            if(towerLevel == 0) {
                System.out.println("Tower level is level0."); //Assuming Roman Fort is tower level 0 as it could not be confirmed
            }
    }

}

