package cn.iocoder.learning.demo.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {

        System.out.println("Agent: 一点不萌");


        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                return builder
                        //.method(ElementMatchers.any())
                        .method(ElementMatchers.named("hello"))
                        .intercept(MethodDelegation.to(MyServiceInterceptor.class))
                        ;
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                System.out.println("onDiscovery typeName：" + typeName);
            }

            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
                System.out.println("onTransformation typeDescription：" + typeDescription);
                System.out.println("onTransformation dynamicType：" + dynamicType);
            }

            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
                System.out.println("onIgnored typeDescription：" + typeDescription);
            }

            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
                System.out.println("onError typeName：" + typeName);
            }

            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                System.out.println("onError typeName：" + typeName);
            }
        };


        new AgentBuilder.Default()
                .type(ElementMatchers.nameStartsWith("cn.iocoder.learning.demo")) // 指定需要拦截的类
//                .type(ElementMatchers.any())
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);

        System.out.println("Agent: 有一点萌");
    }

}
