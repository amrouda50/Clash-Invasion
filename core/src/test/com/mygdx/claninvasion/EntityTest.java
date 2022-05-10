package com.mygdx.claninvasion;
import com.badlogic.gdx.Screen;
import com.mygdx.claninvasion.model.entity.Barbarian;
import com.mygdx.claninvasion.model.entity.Entity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.model.map.WorldMap;
import com.mygdx.claninvasion.view.screens.GamePage;
import com.mygdx.claninvasion.view.screens.GameScreens;
import com.mygdx.claninvasion.view.screens.GameScreensManager;
import org.javatuples.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

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
    public void TestGettersAndConstructor(){
        Pair<Integer , Integer> position = new Pair<Integer , Integer>(5 , 5);
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<Integer, Integer>(5 , 5) , 12);
        //test symbol constructor functionality and getter symbol
        Assert.assertEquals(x.getSymbol() ,  EntitySymbol.BARBARIAN);
        //test x axis position
        Assert.assertEquals(x.getPositionX() , position.getValue0());
        //test y axis position
        Assert.assertEquals(x.getPositionY() , position.getValue1());
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void TestPositionOutOfBounds(){
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<Integer, Integer>(32 , 12) , 12);
    }
    @Test
    public void TestPositionInBounds(){
        x = new Entity(EntitySymbol.BARBARIAN , new Pair<Integer, Integer>(12 , 12) , 12);
        Entity y = new Entity(EntitySymbol.BARBARIAN , new Pair<Integer, Integer>(0, 0) , 12);
        Entity z = new Entity(EntitySymbol.BARBARIAN , new Pair<Integer, Integer>(6, 6) , 12);
    }
}
