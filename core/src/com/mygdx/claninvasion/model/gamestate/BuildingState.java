package com.mygdx.claninvasion.model.gamestate;

public class BuildingState implements GameState{

    public boolean isBuildingPhase() {
        return buildingPhase;
    }

    public void setBuildingPhase(boolean buildingPhase) {
        this.buildingPhase = buildingPhase;
    }

    private boolean buildingPhase;

}
