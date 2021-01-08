package com.code.codepractice.spi;

/**
 * 1.java spi
 * 在 resources/META_INF/services/下添加fullName文件
 * com.code.codepractice.spi.ProviderInterface
 * ServiceLoad会读取文件中的相应配置
 * {@link java.util.ServiceLoader}
 * LazyIterator方法
 * nextService方法  Class.forName
 *
 * spring spi
 *在resources/META_INF 文件下条件
 * spring.factories
 * {@link org.springframework.core.io.support.SpringFactoriesLoader}
 * public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
 * loadSpringFactories方法
 * instantiateFactory方法  ClassUtils.forName
 */
public interface ProviderInterface {
    void start();

    void close();
}
