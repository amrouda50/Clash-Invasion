package com.mygdx.claninvasion.model.entity;

import org.javatuples.Pair;
import java.util.concurrent.CompletableFuture;

public interface Fireable {
    CompletableFuture<Boolean> fire(Pair<Integer, Integer> src, Pair<Integer, Integer> dest);
}
