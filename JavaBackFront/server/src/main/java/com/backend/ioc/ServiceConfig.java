package com.backend.ioc;

import com.backend.services.random.RandomService;
import com.backend.services.random.UtilRandomService;
import com.backend.services.time.TimeService;
import com.backend.services.time.UtilTimeService;
import com.google.inject.AbstractModule;

public class ServiceConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(RandomService.class).to(UtilRandomService.class);
        bind(TimeService.class).to(UtilTimeService.class);
    }
}
