package cn.org.pc6pc1.iast.core;


import cn.org.pc6pc1.iast.contenxt.HttpRequestContext;
import cn.org.pc6pc1.iast.contenxt.RequestContext;
import cn.org.pc6pc1.iast.http.IASTServletRequest;
import cn.org.pc6pc1.iast.http.IASTServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


// 实现Http埋点
public class Http {

    /**
     * 在HTTP方法结束前调用，主要是对存在当前上下文的结果进行可视化打印输出
     */
    public static void leaveHttp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        IASTServletRequest request = RequestContext.getHttpRequestContextThreadLocal().getServletRequest();
        if(!request.getRequestURI().equals("/test_war_exploded/"))
        {
            System.out.printf("URL            : %s \n", request.getRequestURL().toString());
            System.out.printf("URI            : %s \n", request.getRequestURI());
            System.out.printf("QueryString    : %s \n", request.getQueryString());
            System.out.printf("HTTP Method    : %s \n", request.getMethod());
        }
        RequestContext.getHttpRequestContextThreadLocal().getCallChain().forEach(item -> {
            if (item.getChainType().contains("leave")) {
                String returnData = null;
                if (item.getReturnObject().getClass().equals(byte[].class)) {
                    returnData = new String((byte[]) item.getReturnObject());
                } else if (item.getReturnObject().getClass().equals(char[].class)) {
                    returnData = new String((char[]) item.getReturnObject());
                } else {
                    returnData = item.getReturnObject().toString();
                }

                System.out
                        .printf("Type: %s CALL Method Name: %s CALL Method Return: %s \n", item.getChainType(),
                                item.getJavaClassName() +"#"+ item.getJavaMethodName(), returnData);
            } else {
                System.out
                        .printf("Type: %s CALL Method Name: %s CALL Method Args: %s \n", item.getChainType(),
                                item.getJavaClassName() +"#"+ item.getJavaMethodName(),
                                Arrays.asList(item.getArgumentsArray()));
            }

            // 如果是Sink类型，则还会输出调用栈信息
            if (item.getChainType().contains("Sink")) {
                int                 depth    = 1;
                StackTraceElement[] elements = item.getStackTraceElement();

                for (StackTraceElement element : elements) {
                    if (element.getClassName().contains("cn.org.javaweb.iast") ||
                            element.getClassName().contains("java.lang.Thread")) {
                        continue;
                    }
                    System.out.printf("%9s".replace("9", String.valueOf(depth)), "");
                    System.out.println(element);
                    depth++;
                }
            }
        });
    }

    /**
     * 判断当前上下文是否缓存了Request请求
     *
     * @return boolean
     */
    public static boolean haveEnterHttp() {
        HttpRequestContext context = RequestContext.getHttpRequestContextThreadLocal();
        return context != null;
    }


    /**
     * 在HTTP方法进入的时候调用，如果当前上下文为空，
     * 就将`HttpServletRequest`和`HttpServletResponse`对象存到当前线程的上下文中
     * 方便后续对数据的调取使用。
     */
    public static void enterHttp(Object[] objects) {
        if (!haveEnterHttp()) {
            IASTServletRequest  request  = new IASTServletRequest(objects[0]);
            IASTServletResponse response = new IASTServletResponse(objects[1]);

            RequestContext.setHttpRequestContextThreadLocal(request, response);
        }
    }


}
