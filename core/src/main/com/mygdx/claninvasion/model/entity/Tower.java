package com.mygdx.claninvasion.model.entity;

import com.mygdx.claninvasion.model.entity.attacktype.*;
import com.mygdx.claninvasion.model.level.Level;
import com.mygdx.claninvasion.model.level.LevelIterator;
import com.mygdx.claninvasion.model.level.Levels;
import org.javatuples.Pair;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Tower extends ArtificialEntity implements Defensible {
    private final int radius = 4;

    protected Attacks attackTypeName;
    protected AttackTypeTower attackType;

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackTypeTower attackType) {
        this.attackType = attackType;
    }

    public Attacks getAttackTypeName() {
        return attackTypeName;
    }

    public void setAttackTypeName(Attacks attackTypeName) {
        AttackTypeTower attackType;
        this.attackTypeName = attackTypeName;
        if (attackTypeName == Attacks.FIRE) {
            attackType = new AttackTypeFire();
            setAttackType(attackType);
        } else if (attackTypeName == Attacks.ARTILLERY) {
            attackType = new AttackTypeArtillery();
            setAttackType(attackType);
        } else if (attackTypeName == Attacks.ARCHER) {
            attackType = new AttackTypeArcher();
            setAttackType(attackType);
        }
    }

    public Tower(EntitySymbol entitySymbol, Pair<Integer, Integer> position) {
        //Change later
        super(entitySymbol, position);

        level = Levels.createTowerLevelIterator();
        health = new AtomicInteger();
        super.setHealth(level.current().getMaxHealth());
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

        attackType.attack();

        System.out.println("Tower is attacking and decreasing health by " + attackType.getDecreaseHealth());
        artificialEntity.setDecreaseHealth(attackType.getDecreaseHealth());
    }
}

