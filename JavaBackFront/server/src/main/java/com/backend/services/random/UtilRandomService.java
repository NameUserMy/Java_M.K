package com.backend.services.random;

import java.util.Random;

import com.google.inject.Singleton;

@Singleton
public class UtilRandomService implements RandomService {

    private final Random random=new Random();
    @Override
    public int randomInt() {
       
        return random.nextInt();
    }

}
