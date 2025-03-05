package cn.org.pc6pc1.iast.http;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

//对 Servlet 请求对象的一个封装
public class IASTServletRequest {
    private Object request;

    private Class requestClass;

    public IASTServletRequest(Object request) {
        this.request = request;
        this.requestClass = request.getClass();
    }

    public Class getRequestClass() {
        return requestClass;
    }

    public String getRequestBody() throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }


    public String getHeader(String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getHeader", String.class);
        method.setAccessible(true);
        return (String) method.invoke(request, name);
    }

    public Enumeration<String> getHeaders(String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getHeaders", String.class);
        method.setAccessible(true);
        return (Enumeration) method.invoke(request, name);
    }

    public Enumeration<String> getHeaderNames() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getHeaderNames");
        method.setAccessible(true);
        return (Enumeration) method.invoke(request);
    }


    public String getMethod() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getMethod");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }


    public String getContextPath() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getContextPath");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getQueryString() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getQueryString");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getRequestedSessionId() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRequestedSessionId");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getRequestURI() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRequestURI");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public StringBuffer getRequestURL() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = requestClass.getMethod("getRequestURL");
        method.setAccessible(true);
        return (StringBuffer) method.invoke(request);
    }

    public String getServletPath() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getServletPath");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getCharacterEncoding() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getCharacterEncoding");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String setCharacterEncoding(String enc) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("setCharacterEncoding", String.class);
        method.setAccessible(true);
        return (String) method.invoke(request, enc);
    }

    public int getContentLength() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getContentLength");
        method.setAccessible(true);
        return (int) method.invoke(request);
    }

    public String getContentType() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getContentType");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getParameter(String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getParameter", String.class);
        method.setAccessible(true);
        return (String) method.invoke(request, name);
    }


    public Enumeration<String> getParameterNames() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getParameterNames");
        method.setAccessible(true);
        return (Enumeration) method.invoke(request);
    }

    public String[] getParameterValues(String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getParameterValues", String.class);
        method.setAccessible(true);
        return (String[]) method.invoke(request, name);
    }

    public Map<String, String[]> getParameterMap() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getParameterMap");
        method.setAccessible(true);
        return (Map) method.invoke(request);
    }


    public String getProtocol() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getProtocol");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getScheme() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getScheme");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getServerName() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getServerName");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public int getServerPort() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getServerPort");
        method.setAccessible(true);
        return (int) method.invoke(request);
    }

    public BufferedReader getReader() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getReader");
        method.setAccessible(true);
        return (BufferedReader) method.invoke(request);
    }


    public String getRemoteAddr() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRemoteAddr");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getRemoteHost() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRemoteHost");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getRealPath(String path) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRealPath", String.class);
        method.setAccessible(true);
        return (String) method.invoke(request, path);
    }


    public int getRemotePort() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getRemotePort");
        method.setAccessible(true);
        return (int) method.invoke(request);
    }

    public String getLocalName() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getLocalName");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public String getLocalAddr() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getLocalAddr");
        method.setAccessible(true);
        return (String) method.invoke(request);
    }

    public int getLocalPort() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = requestClass.getMethod("getLocalPort");
        method.setAccessible(true);
        return (int) method.invoke(request);
    }

}