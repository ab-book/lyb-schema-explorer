//package ares.remoting.framework.provider;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
///**
// * 服务端bean代理工厂
// *
// * @author liyebing created on 16/10/3.
// * @version $Id$
// */
//public class ProviderProxyBeanFactory implements InvocationHandler {
//
//    private Object serviceBean;
//
//
//    public ProviderProxyBeanFactory(Object serviceBean) {
//        this.serviceBean = serviceBean;
//    }
//
//    @Override
//    public Object invoke(Object serviceBean, Method method, Object[] args) throws Throwable {
//
//        // before(); 这里嵌入拦截器ServerInvokeInterceptor
//        Object result = method.invoke(serviceBean, args);
//        // after();
//        return result;
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T> T getProxy() {
//        return (T) Proxy.newProxyInstance(serviceBean.getClass().getClassLoader(), serviceBean.getClass().getInterfaces(), this);
//    }
//
//    private static volatile ProviderProxyBeanFactory singleton;
//
//    public static ProviderProxyBeanFactory singleton(Object serviceBean) throws Exception {
//        if (null == singleton) {
//            synchronized (ProviderProxyBeanFactory.class) {
//                if (null == singleton) {
//                    singleton = new ProviderProxyBeanFactory(serviceBean);
//                }
//            }
//        }
//        return singleton;
//    }
//
//}
