package cn.iocoder.learning.demo.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MyServiceInterceptor {

//    @RuntimeType
//    public static Object intercept(@SuperCall Callable<?> zuper) throws Exception {
//        System.out.println("intercept：拦截了");
//        System.out.println("zuper：" + zuper);
//
////        return zuper.call();
//        return 2;
//    }

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
		System.out.println("intercept：" + method);
		long start = System.nanoTime();
		try {
			// 原有函数执行
			return callable.call();
		} finally {
			long end = System.nanoTime();
			long diff = end - start;
			System.out.println("这次调用一共花了 " + diff + " ns, " + diff / 1000.0 + " us, " + diff / 1000000.0 + " ms");
		}
	}








}
