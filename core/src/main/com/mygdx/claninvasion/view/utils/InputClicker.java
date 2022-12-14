package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.entity.ArtificialEntity;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.actors.HealthBar;
import com.mygdx.claninvasion.view.screens.MainGamePage;
import org.javatuples.Pair;

public class InputClicker implements RunnableTouchEvent {
    private final ClanInvasion app;
    private final MainGamePage page;
    public static boolean enabled = false;

    public InputClicker(ClanInvasion app , MainGamePage mainGame) {
        this.app = app;
        this.page = mainGame;
    }

    @Override
    public void run(Vector3 mousePosition) {
        if (enabled && app.getModel().isInteractive()) {
            Vector2 mouseOrtho = new IsometricToOrthogonalAdapt(new Vector2(mousePosition.x, mousePosition.y)).getPoint();
            Vector3 mouseOrtho3 = new Vector3(mouseOrtho.x + WorldCell.getTransformWidth(), mouseOrtho.y - WorldCell.getTransformWidth(), 0);

            for (WorldCell worldCell : app.getMap().getCells()) {
                if (worldCell.contains(mouseOrtho3)) {
                    HealthBar healthBar = new HealthBar(app.getCurrentPlayer().getColor());
                    ArtificialEntity artificialEntity = null;
                    if (worldCell.getOccupier() == null && EntitySymbol.isTower(page.getChosenSymbol())) {
                        artificialEntity = app.getCurrentPlayer().buildTower(worldCell, page.getChosenSymbol());
                    } else if (worldCell.getOccupier() == null && EntitySymbol.MINING == page.getChosenSymbol()) {
                        artificialEntity = app.getCurrentPlayer().createNewMining(worldCell);
                    }

                    if (artificialEntity != null) {
                        healthBar.setCoordinates(new Pair<>(worldCell.getWorldIsoPoint1().x , worldCell.getWorldIsoPoint1().y));
                        artificialEntity.setHealthBar(healthBar);
                    }

                    if (worldCell.getOccupier() != null) {
                        System.out.println((worldCell.getOccupier().getSymbol()));
                    }

                }
            }
        }
    }
}
