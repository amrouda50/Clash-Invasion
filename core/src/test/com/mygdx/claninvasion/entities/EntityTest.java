package com.mygdx.claninvasion.entities;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.claninvasion.exceptions.EntityOutsideOfBoundsException;
import com.mygdx.claninvasion.model.entity.Entity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class EntityTest {

    public WorldMap map;
    public Entity x;
    public WorldCell mockCell;

    @Before
    public void startMocks() {
        mockCell = mock(WorldCell.class);
        map = new WorldMap();
        for(int i = 0 ; i < 12*12 ; i++){
            map.addCell(mockCell);
        }
    }

    @Test
    public void testGettersAndConstructor(){
        Pair<Integer , Integer> position = new Pair<>(5, 5);
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(5, 5) , 12);
        //test symbol constructor functionality and getter symbol
        Assert.assertEquals(x.getSymbol() ,  EntitySymbol.BARBARIAN);
        //test x axis position
        Assert.assertEquals(x.getPositionX() , position.getValue0());
        //test y axis position
        Assert.assertEquals(x.getPositionY() , position.getValue1());
    }

    @Test(expected = EntityOutsideOfBoundsException.class)
    public void testPositionOutOfBounds(){
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(32, 12) , 12);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLessOneSizeException() {
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(32, 12) , 0);
    }

    @Test
    public void testPositionInBounds(){
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(12, 12) , 12);
        new Entity(EntitySymbol.BARBARIAN , new Pair<>(0, 0) , 12);
        new Entity(EntitySymbol.BARBARIAN , new Pair<>(6, 6) , 12);
    }

    @Test
    public void testPositions() {
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(12, 12) , 12);
        Assert.assertEquals(new Vector2(12, 12), x.getVec2Position());
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(12, 0) , 12);
        Assert.assertEquals(new Vector2(12, 0), x.getVec2Position());

        Assert.assertEquals(new Pair<>(12, 0), x.getPosition());
    }

    @Test
    public void testMapSize() {
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(12, 12) , 12);
        Assert.assertEquals(12, x.getMapSize());
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<>(0, 0) , 1);
        Assert.assertEquals(1, x.getMapSize());
    }
}
