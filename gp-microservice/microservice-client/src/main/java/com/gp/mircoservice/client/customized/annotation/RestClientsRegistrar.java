package com.gp.mircoservice.client.customized.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 10:55
 * @since
 */
public class RestClientsRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware, EnvironmentAware {

    private BeanFactory beanFactory;

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        ClassLoader classLoader = metadata.getClass().getClassLoader();

        //
        /**
         * 获取扫描的路径
         * {@link com.gp.mircoservice.client.customized.annotation.EnableRestClient#clients()}
         */
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableRestClient.class.getName());
        Class<?>[] clientClasses = (Class<?>[]) attributes.get("clients");

        /**
         * 扫描所有被 {@link RestClient} 注解的接口
         * 1.扫描所有的包
         * 2.判断是否为接口
         * 3.是否有 @RestClient 注解
         * 4.重新构造bean
         */
        Stream.of(clientClasses)    // 1.
                .filter(Class::isInterface) // 2.
                .filter(interfaceClass -> AnnotationUtils.findAnnotation(interfaceClass, RestClient.class) != null)    // 3.
                .forEach(restClientClass -> {
                    // 4.

                    // 获取 @RestClient 元信息
                    RestClient restClient = AnnotationUtils.findAnnotation(restClientClass, RestClient.class);

                    // 获取 应用名称（处理占位符）
                    String serviceName = environment.resolvePlaceholders(restClient.name());

                    // @RestClient 接口编程 JDK 动态代理
                    Object proxy = Proxy.newProxyInstance(
                            classLoader,
                            new Class[]{restClientClass},
                            new RequestMappingMethodInvocationHandler(serviceName, beanFactory));

                    // 将@RestClient接口代理实现注册为Bean(@Autowired)
                    // BeanDefinitionRegistry registry
                    String beanName = "RestClient." + serviceName;

                    // 实现方略一：构造FactoryBean
                    // registerBeanByFactoryBean(serviceName,proxy,restClientClass,registry);

                    // 实现方略二：SingletonBeanRegistry
                    if (registry instanceof SingletonBeanRegistry) {
                        SingletonBeanRegistry singletonBeanRegistry = (SingletonBeanRegistry) registry;
                        singletonBeanRegistry.registerSingleton(beanName, proxy);
                    }
                });
    }

    private static void registerBeanByFactoryBean(String serviceName,
                                                  Object proxy, Class<?> restClientClass, BeanDefinitionRegistry registry) {
        String beanName = "RestClient." + serviceName;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RestClientClassFactoryBean.class);

        // 增加第一个构造器参数引用 : proxy
        beanDefinitionBuilder.addConstructorArgValue(proxy);
        // 增加第二个构造器参数引用 : restClientClass
        beanDefinitionBuilder.addConstructorArgValue(restClientClass);

        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    private static class RestClientClassFactoryBean implements FactoryBean {

        private final Object proxy;

        private final Class<?> restClientClass;

        private RestClientClassFactoryBean(Object proxy, Class<?> restClientClass) {
            this.proxy = proxy;
            this.restClientClass = restClientClass;
        }

        @Override
        public Object getObject() throws Exception {
            return proxy;
        }

        @Override
        public Class<?> getObjectType() {
            return restClientClass;
        }
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
