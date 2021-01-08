package com.code.codepractice.spi;

import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.ServiceLoader;

public class SpiTest {
    @Test
    public void testJavaSpi() {
        ServiceLoader<ProviderInterface> providerInterfaces = ServiceLoader.load(ProviderInterface.class);
        for (ProviderInterface provider : providerInterfaces) {
            provider.start();
            provider.close();
        }
    }
    @Test
    public void testSpringSpi() {
       List<ProviderInterface> providerInterfaces=SpringFactoriesLoader.loadFactories(ProviderInterface.class,null);
        for (ProviderInterface provider : providerInterfaces) {
            provider.start();
            provider.close();
        }
    }
}
