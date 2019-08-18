package com.yyp.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.yyp.server.rest.Home;
import com.yyp.server.rest.PosesRest;

public class YypApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public YypApplication() {
        singletons.add(new Home());
        singletons.add(new PosesRest());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
