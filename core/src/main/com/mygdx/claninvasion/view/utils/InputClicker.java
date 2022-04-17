package com.mygdx.claninvasion.view.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.claninvasion.ClanInvasion;
import com.mygdx.claninvasion.model.adapters.IsometricToOrthogonalAdapt;
import com.mygdx.claninvasion.model.entity.EntitySymbol;
import com.mygdx.claninvasion.model.map.WorldCell;
import com.mygdx.claninvasion.view.applicationlistener.MainGamePageUI;

public class InputClicker  implements  RunnableTouchEvent{
    private ClanInvasion app;
    private MainGamePageUI mainGamePageUI;
    public static boolean Enabled = false;
    public  InputClicker(ClanInvasion app , MainGamePageUI mainGamePageUI){
        this.app = app;
        this.mainGamePageUI = mainGamePageUI;
    }
    @Override
    public void run(Vector3 mousePosition) {
        if(Enabled) {
            Vector2 mouseOrtho = new IsometricToOrthogonalAdapt(new Vector2(mousePosition.x, mousePosition.y)).getPoint();
            Vector3 mouseOrtho3 = new Vector3(mouseOrtho.x + WorldCell.getTransformWidth(), mouseOrtho.y - WorldCell.getTransformWidth(), 0);

            for (WorldCell worldCell : app.getMap().getCells()) {
                if (worldCell.contains(mouseOrtho3)) {

                    if (worldCell.getOccupier() == null && EntitySymbol.TOWER == mainGamePageUI.getChoosenSymbol()) {
                        app.getCurrentPlayer().buildTower(worldCell);
                    } else if (worldCell.getOccupier() == null && EntitySymbol.MINING == mainGamePageUI.getChoosenSymbol()) {
                        app.getCurrentPlayer().createNewMining(worldCell);
                    }

                    if (worldCell.getOccupier() != null) {
                        System.out.println((worldCell.getOccupier().getSymbol()));
                    }

                }
            }
        }
    }
}
