package com.mygdx.claninvasion.model.map;

import com.mygdx.claninvasion.model.entity.*;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;


public class EntitiesCreators {
    public interface EntityCreator<T> {
        Entity create(EntitySymbol entitySymbol, Pair<Integer, Integer> position, Object args);
    }

    private static final Map<EntitySymbol, EntityCreator<?>> creators = new HashMap<>(Map.of(
            EntitySymbol.BARBARIAN, (EntityCreator<Void>) (entitySymbol, position, args) -> new Barbarian(entitySymbol, position),
            EntitySymbol.DRAGON, (EntityCreator<Void>)  (entitySymbol, position, args) -> new Dragon(entitySymbol, position),
            EntitySymbol.TOWER, (EntityCreator<Void>) (entitySymbol, position, args) -> new Tower(entitySymbol, position),
            EntitySymbol.MINING, (EntityCreator<BlockingQueue<Integer>>) (entitySymbol, position, queue) -> new MiningFarm(entitySymbol, position, (BlockingQueue<Integer>) queue)
    ));

    public static Entity createEntity(EntitySymbol entitySymbol, Pair<Integer, Integer> position, Object obj) {
        return creators.get(entitySymbol).create(entitySymbol, position, obj);
    }
}
