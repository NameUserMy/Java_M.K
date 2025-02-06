package com.backend.services.random;

import java.util.Random;

import com.google.inject.Singleton;

@Singleton
public class UtilRandomService implements RandomService {
    private long seed = System.nanoTime();
    private final Random random=new Random(seed);
    @Override
    public int randomInt() {
        return random.nextInt(1000);
    }

}
