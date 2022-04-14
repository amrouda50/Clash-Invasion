package com.mygdx.claninvasion.view.exceptions;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class UnknownTiledMapLayerException extends RuntimeException {
    public UnknownTiledMapLayerException(TiledMapTileLayer layer) {
        super("Layer with name " + layer.getName() + " is unrecognizable by the renderer");
    }
}
