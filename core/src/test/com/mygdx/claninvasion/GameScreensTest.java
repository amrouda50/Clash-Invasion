package com.mygdx.claninvasion;

import com.badlogic.gdx.Screen;
import com.mygdx.claninvasion.view.screens.GamePage;
import com.mygdx.claninvasion.view.screens.GameScreens;
import com.mygdx.claninvasion.view.screens.GameScreensManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class GameScreensTest {
    public GamePage page1;
    public GamePage page2;

    @Before
    public void beforeTest() {
        page1 = mock(GamePage.class);
        page2 = mock(GamePage.class);
    }

    @Test
    public void testIsEmptyPush() {
        GameScreensManager gameScreens = new GameScreens();
        Assert.assertTrue("Game screen should be empty", gameScreens.isEmpty());
        gameScreens.push(page1);
        Assert.assertFalse("Game screen should not be empty", gameScreens.isEmpty());
    }

    @Test
    public void testGet() {
        GameScreensManager gameScreens = new GameScreens();
        gameScreens.push(page1);
        Assert.assertSame("Pages should be the same", gameScreens.get(), page1);
        Assert.assertFalse("Pages should not be empty", gameScreens.isEmpty());
    }

    @Test
    public void testPop() {
        GameScreensManager gameScreens = new GameScreens();
        gameScreens.push(page1);
        gameScreens.push(page2);
        Screen screen = gameScreens.pop();
        Assert.assertSame("Pop method should return the last element", screen, page2);
        Assert.assertSame("Sublast element became last ", gameScreens.get(), page1);
    }

    @Test(expected = EmptyStackException.class)
    public void testPopInTheEmptyManager() {
        GameScreensManager gameScreens = new GameScreens();
        gameScreens.push(page1);
        gameScreens.push(page2);
        gameScreens.pop();
        gameScreens.pop();
        gameScreens.pop();
        Assert.assertTrue("Manager is empty", gameScreens.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptySizedManager() {
        GameScreensManager gameScreens = new GameScreens();
        gameScreens.push(page1);
        gameScreens.push(page2);
        gameScreens.pop();
        gameScreens.pop();
        Assert.assertTrue("Manager is empty", gameScreens.isEmpty());
        gameScreens.get();
    }

    @Test(expected = EmptyStackException.class)
    public void testReplace() {
        GameScreensManager gameScreens = new GameScreens();

        GamePage page3 = mock(GamePage.class);
        GamePage page4 = mock(GamePage.class);

        gameScreens.push(page1);
        gameScreens.push(page2);
        GamePage page2 = gameScreens.replace(page3);

        Assert.assertSame("Replacing object same as passed to replace method ", gameScreens.get(), page3);
        Assert.assertSame("Returned object previous last manager page ", page2, this.page2);
        Assert.assertNotSame("Returned object is not the same as the one passed ", page2, page3);

        gameScreens.pop();
        gameScreens.pop();
        gameScreens.pop();

        Assert.assertTrue("Manager is empty", gameScreens.isEmpty());
        gameScreens.replace(page4);
    }
}
