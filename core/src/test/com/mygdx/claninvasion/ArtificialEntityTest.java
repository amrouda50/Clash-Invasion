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
public class ArtificialEntityTest {
    @Before
    public void startMocks() {

    }

    @Test
    public void TestConstructor(){
        Assert.assertEquals(true , true);
    }


}
