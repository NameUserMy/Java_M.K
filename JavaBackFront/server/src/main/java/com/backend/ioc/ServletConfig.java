package com.backend.ioc;

import com.backend.filters.AuthFilter;
import com.backend.filters.AuthJwtFilter;
import com.backend.filters.CharSetFilter;
import com.backend.servlets.*;

import com.google.inject.servlet.ServletModule;


public class ServletConfig extends ServletModule{

    @Override
    protected void configureServlets() {
        filter("/*").through(CharSetFilter.class);
        filter("/*").through(AuthFilter.class);
        //filter("/*").through(AuthJwtFilter.class);


        //!! For all servlets in project 
        //delete annatotaion @webServle
        //add anatation @singleton
        serve("/home").with(HomeServlet.class);
        serve("/user").with(UserServlet.class);
        serve("/random").with(RandomServlet.class);
    }



}
