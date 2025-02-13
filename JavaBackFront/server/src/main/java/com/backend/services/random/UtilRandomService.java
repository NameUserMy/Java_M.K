package com.backend.services.random;

import java.util.Random;

import com.backend.services.time.TimeService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UtilRandomService implements RandomService {

    private final TimeService timeService;
    private final Random random = new Random();

    @Inject
    public UtilRandomService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public int randomInt() {
        random.setSeed(timeService.getSeed());
        return random.nextInt(1000);
    }

    @Override
    public String noRestrictionsStr(int length) {
        return new RandomString().noRestrictionsStr(length);
    }

    @Override
    public String fileNameRandomStr(int length) {
        return new RandomString().fileNameRandomStr(length);
    }

}
