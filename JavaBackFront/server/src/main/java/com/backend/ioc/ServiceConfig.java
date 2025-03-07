package com.backend.ioc;

import com.backend.services.authuser.jwt.JwtToken;
import com.backend.services.authuser.jwt.JwtTokenService;
import com.backend.services.config.ConfigService;
import com.backend.services.config.JsonConfigService;
import com.backend.services.db.DbService;
import com.backend.services.db.MySqlDbService;
import com.backend.services.form_parse.FormParseService;
import com.backend.services.form_parse.MixedFormParseService;
import com.backend.services.hash.HashService;
import com.backend.services.hash.Md5HashService;
import com.backend.services.kdf.KdfService;
import com.backend.services.kdf.PbKdf1Service;
import com.backend.services.random.RandomService;
import com.backend.services.random.UtilRandomService;
import com.backend.services.storage.DiskStorageService;
import com.backend.services.storage.StorageService;
import com.backend.services.time.TimeService;
import com.backend.services.time.UtilTimeService;
import com.google.inject.AbstractModule;

public class ServiceConfig extends AbstractModule {

    @Override
    protected void configure() {
        bind(RandomService.class).to(UtilRandomService.class);
        bind(TimeService.class).to(UtilTimeService.class);
        bind(HashService.class).to(Md5HashService.class);
        bind(KdfService.class).to(PbKdf1Service.class);
        bind(DbService.class).to(MySqlDbService.class);
        bind(ConfigService.class).to(JsonConfigService.class);
        bind(FormParseService.class).to(MixedFormParseService.class);
        bind(StorageService.class).to(DiskStorageService.class);
        bind(JwtToken.class).to(JwtTokenService.class);
    }
}
