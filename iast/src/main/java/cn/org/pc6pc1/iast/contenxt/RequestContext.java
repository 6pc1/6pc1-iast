package cn.org.pc6pc1.iast.contenxt;

import cn.org.pc6pc1.iast.http.IASTServletRequest;
import cn.org.pc6pc1.iast.http.IASTServletResponse;

// 存储管理当前线程相关的HttpRequestContext对象
public class RequestContext {

    // 使用ThreadLocal存储HttpRequestContext
    private static final ThreadLocal<HttpRequestContext> HTTP_REQUEST_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    public static HttpRequestContext getHttpRequestContextThreadLocal(){
        return HTTP_REQUEST_CONTEXT_THREAD_LOCAL.get();
    }

    public static void setHttpRequestContextThreadLocal(IASTServletRequest request, IASTServletResponse response){
        IASTServletRequest iastServletRequest = request;
        IASTServletResponse iastServletResponse = response;
        HTTP_REQUEST_CONTEXT_THREAD_LOCAL.set(new HttpRequestContext(iastServletRequest, iastServletResponse));
    }

}
