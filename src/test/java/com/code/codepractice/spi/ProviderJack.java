package com.code.codepractice.spi;

public class ProviderJack implements ProviderInterface {

    @Override
    public void start() {
        System.out.println("start the jack provider...");
    }

    @Override
    public void close() {
        System.out.println("close the jerry provider...");
    }
}
