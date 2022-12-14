package com.mygdx.claninvasion.entities;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.model.entity.ArtificialEntity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.entity.Tower;
import com.mygdx.claninvasion.model.player.Player;
import com.mygdx.claninvasion.view.actors.HealthBar;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TowerTest {

    @Test
    public void canFireTest() {
        ArtificialEntity artificialEntity = mock(ArtificialEntity.class);
        when(artificialEntity.getVec2Position()).thenReturn(new Vector2(20, 20));

        Tower tower = new Tower(EntitySymbol.ROMAN_FORT, new Pair<>(21, 21), 32) {
            @Override
            public int getDecreaseRate() {
                return 0;
            }

            @Override
            public int getRadius() {
                return 4;
            }

            @Override
            public void setLevel(Player player) {}

            @Override
            public String getProjectileSource() {
                return null;
            }
        };

        boolean canFire = tower.canFire(artificialEntity);
        Assert.assertTrue(canFire);

        when(artificialEntity.getVec2Position()).thenReturn(new Vector2(20 + tower.getRadius(), 20 + tower.getRadius()));

        canFire = tower.canFire(artificialEntity);
        Assert.assertFalse(canFire);


        when(artificialEntity.getVec2Position()).thenReturn(new Vector2(24 + tower.getRadius(), 24 + tower.getRadius()));

        canFire = tower.canFire(artificialEntity);
        Assert.assertFalse(canFire);


        when(artificialEntity.getVec2Position()).thenReturn(new Vector2(20 + tower.getRadius() - 1, 20 + tower.getRadius() - 1));

        canFire = tower.canFire(artificialEntity);
        Assert.assertTrue(canFire);
    }

    @Test
    public void attackTest() {
        ArtificialEntity artificialEntity = new ArtificialEntity(EntitySymbol.BARBARIAN, new Pair<>(21, 21), 32) {
            @Override
            protected void setHealth(int newHealth) {
                super.setHealth(newHealth);
            }
        };
        artificialEntity.setHealthBar(mock(HealthBar.class));
        Tower tower = new Tower(EntitySymbol.ROMAN_FORT, new Pair<>(21, 21), 32) {
            @Override
            public int getDecreaseRate() {
                return 40;
            }

            @Override
            public int getRadius() {
                return 2;
            }

            @Override
            public void setLevel(Player player) {}

            @Override
            public String getProjectileSource() {
                return null;
            }
        };
        tower.attack(artificialEntity);
        Assert.assertEquals(
                artificialEntity.getLevel().current().getMaxHealth() - tower.getDecreaseRate(),
                artificialEntity.getHealth()
        );
    }
}
