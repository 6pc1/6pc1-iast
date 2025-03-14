package cn.org.pc6pc1.iast.core;

import cn.org.pc6pc1.iast.contenxt.CallChain;
import cn.org.pc6pc1.iast.contenxt.RequestContext;

import static cn.org.pc6pc1.iast.core.Http.haveEnterHttp;

public class Propagator {

    // 进入Propagator点
    // argumentArray 参数数组
    // javaClassName 类名
    // javaMethodName 方法名
    // javaMethodDesc  方法描述符
    // isStatic      是否为静态方法
    public static void enterPropagator(Object[] argumentArray,
                                       String javaClassName,
                                       String javaMethodName,
                                       String javaMethodDesc,
                                       boolean isStatic) {
        if (haveEnterHttp()) {
            CallChain callChain = new CallChain();
            callChain.setChainType("enterPropagator");
            callChain.setArgumentsArray(argumentArray);
            callChain.setJavaClassName(javaClassName);
            callChain.setJavaMethodName(javaMethodName);
            callChain.setJavaMethodDesc(javaMethodDesc);
            callChain.setStatic(isStatic);

            RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);
        }

    }

    // 离开Propagator点
    // returnObject 返回值对象
    // javaClassName 类名
    // javaMethodName 方法名
    // javaMethodDesc  方法描述符
    // isStatic      是否为静态方法
    public static void leavePropagator(Object returnObject,
                                       String javaClassName,
                                       String javaMethodName,
                                       String javaMethodDesc,
                                       boolean isStatic) {
        if (haveEnterHttp()) {

            CallChain callChain = new CallChain();
            callChain.setChainType("leavePropagator");
            callChain.setReturnObject(returnObject);
            callChain.setJavaClassName(javaClassName);
            callChain.setJavaMethodName(javaMethodName);
            callChain.setJavaMethodDesc(javaMethodDesc);
            callChain.setStatic(isStatic);
            RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);

        }
    }


}
