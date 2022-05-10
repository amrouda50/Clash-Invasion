package com.mygdx.claninvasion.model.map;

import com.mygdx.claninvasion.model.entity.*;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class EntitiesCreators {
    public interface EntityCreator<T> {
        Entity create(EntitySymbol entitySymbol, Pair<Integer, Integer> position, Object args, int mapSize);
    }

    private static final Map<EntitySymbol, EntityCreator<?>> creators = new HashMap<>(Map.of(
            EntitySymbol.BARBARIAN, (EntityCreator<Void>) (entitySymbol, position, args, mapSize) -> new Barbarian(entitySymbol, position , mapSize),
            EntitySymbol.DRAGON, (EntityCreator<Void>)  (entitySymbol, position, args ,  mapSize) -> new Dragon(entitySymbol, position, mapSize),
            EntitySymbol.TOWER, (EntityCreator<Void>) (entitySymbol, position, args ,  mapSize) -> new Tower(entitySymbol, position , mapSize),
            EntitySymbol.MINING, (EntityCreator<BlockingQueue<Integer>>) (entitySymbol, position, queue ,  mapSize) -> new MiningFarm(entitySymbol, position, (BlockingQueue<Integer>) queue , mapSize)
    ));

    public static Entity createEntity(EntitySymbol entitySymbol, Pair<Integer, Integer> position, Object obj , int mapSize) {
        return creators.get(entitySymbol).create(entitySymbol, position, obj ,  mapSize);
    }
}
