package com.code.codepractice.spi;

public class ProviderTom implements ProviderInterface {

    @Override
    public void start() {
        System.out.println("start the tom provider...");
    }

    @Override
    public void close() {
        System.out.println("close the tom provider...");
    }
}
