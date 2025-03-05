package cn.org.pc6pc1.iast.contenxt;


import cn.org.pc6pc1.iast.http.IASTServletRequest;
import cn.org.pc6pc1.iast.http.IASTServletResponse;

import java.util.LinkedList;

// 用于存储与单个 HTTP 请求及其在 Web 应用中的处理过程相关的信息
public class HttpRequestContext {
    private final IASTServletRequest servletRequest;

    private final IASTServletResponse servletResponse;

    private LinkedList<CallChain> callChain;

    public HttpRequestContext(IASTServletRequest servletRequest, IASTServletResponse servletResponse) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
        callChain = new LinkedList<>();
    }

    public IASTServletRequest getServletRequest() {
        return servletRequest;
    }

    public LinkedList<CallChain> getCallChain() {
        return callChain;
    }

    public void addCallChain(CallChain callChain) {
        this.callChain.add(callChain);
    }
}
