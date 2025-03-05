package cn.org.pc6pc1.iast.core;

import cn.org.pc6pc1.iast.contenxt.CallChain;
import cn.org.pc6pc1.iast.contenxt.RequestContext;

import static cn.org.pc6pc1.iast.core.Http.haveEnterHttp;

public class Sink {


    // 进入Sink点
    // argumentArray 参数数组
    // javaClassName 类名
    // javaMethodName 方法名
    // javaMethodDesc  方法描述符
    // isStatic      是否为静态方法

    public static void enterSink(Object[] argumentArray,
                                 String javaClassName,
                                 String javaMethodName,
                                 String javaMethodDesc,
                                 boolean isStatic) {
        if (haveEnterHttp()) {
            CallChain callChain = new CallChain();
            callChain.setChainType("enterSink");
            callChain.setArgumentsArray(argumentArray);
            callChain.setJavaClassName(javaClassName);
            callChain.setJavaMethodName(javaMethodName);
            callChain.setJavaMethodDesc(javaMethodDesc);
            callChain.setStatic(isStatic);
            callChain.setStackTraceElement(Thread.currentThread().getStackTrace());
            RequestContext.getHttpRequestContextThreadLocal().addCallChain(callChain);
        }
    }


}
