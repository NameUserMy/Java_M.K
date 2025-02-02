package com.backend.ioc;

import com.backend.services.random.RandomService;
import com.backend.services.random.UtilRandomService;
import com.google.inject.AbstractModule;

public class ServiceConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(RandomService.class).to(UtilRandomService.class);
    }
}
