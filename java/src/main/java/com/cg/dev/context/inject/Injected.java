package com.cg.dev.context.inject;

public class Injected {
    private Injector injector;

    @Override
    public String toString() {
        return "Injected{" +
                "injector=" + injector +
                '}';
    }

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
