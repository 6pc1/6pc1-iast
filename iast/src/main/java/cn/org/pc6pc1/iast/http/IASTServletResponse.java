package cn.org.pc6pc1.iast.http;


import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;

//对 Servlet 请求响应的一个封装
public class IASTServletResponse {

    private Object response;

    private Class responseClass;

    public IASTServletResponse(Object response){
        this.response = response;
        this.responseClass = response.getClass();
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public boolean containsHeader(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("containsHeader", String.class);
        method.setAccessible(true);
        return (Boolean) method.invoke(response, name);
    }

    public String encodeURL(String url) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("encodeURL", String.class);
        method.setAccessible(true);
        return (String) method.invoke(response, url);
    }

    public String encodeRedirectURL(String url) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("encodeRedirectURL", String.class);
        method.setAccessible(true);
        return (String) method.invoke(response, url);
    }

    public String encodeUrl(String url) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("encodeUrl", String.class);
        method.setAccessible(true);
        return (String) method.invoke(response, url);
    }

    public String encodeRedirectUrl(String url) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("encodeRedirectUrl", String.class);
        method.setAccessible(true);
        return (String) method.invoke(response, url);
    }

    public void sendError(int code, String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("sendError", int.class, String.class);
        method.setAccessible(true);
        method.invoke(response, code, message);
    }

    public void sendError(int code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("sendError", int.class);
        method.setAccessible(true);
        method.invoke(response, code);
    }

    public void sendRedirect(String location) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("sendRedirect", String.class);
        method.setAccessible(true);
        method.invoke(response, location);
    }

    public void setDateHeader(String name, long date) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setDateHeader", String.class, long.class);
        method.setAccessible(true);
        method.invoke(response, name, date);
    }

    public void addDateHeader(String name, long date) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("addDateHeader", String.class, long.class);
        method.setAccessible(true);
        method.invoke(response, name, date);
    }

    public void setHeader(String name, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setHeader", String.class, String.class);
        method.setAccessible(true);
        method.invoke(response, name, value);
    }

    public void addHeader(String name, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("addHeader", String.class, String.class);
        method.setAccessible(true);
        method.invoke(response, name, value);
    }

    public void setIntHeader(String name, int value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setIntHeader", String.class, int.class);
        method.setAccessible(true);
        method.invoke(response, name, value);
    }

    public void addIntHeader(String name, int value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("addIntHeader", String.class, int.class);
        method.setAccessible(true);
        method.invoke(response, name, value);
    }

    public void setStatus(int code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setStatus", int.class);
        method.setAccessible(true);
        method.invoke(response, code);
    }

    public void setStatus(int code, String message) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setStatus", int.class, String.class);
        method.setAccessible(true);
        method.invoke(response, code, message);
    }

    public void getStatus() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getStatus");
        method.setAccessible(true);
        System.out.println(method.invoke(response));
    }

    public String getHeader(String name) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getHeader", String.class);
        method.setAccessible(true);
        return (String) method.invoke(response, name);
    }

    public Collection<String> getHeaders(String name) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = responseClass.getMethod("getHeaders", String.class);
        method.setAccessible(true);
        return (Collection) method.invoke(response, name);
    }

    public Collection<String> getHeaderNames() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getHeaderNames");
        method.setAccessible(true);
        return (Collection) method.invoke(response);
    }

    public String getCharacterEncoding() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getCharacterEncoding");
        method.setAccessible(true);
        return (String) method.invoke(response);
    }

    public String getContentType() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getContentType");
        method.setAccessible(true);
        return (String) method.invoke(response);
    }

    public PrintWriter getWriter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getWriter");
        method.setAccessible(true);
        return (PrintWriter) method.invoke(response);
    }

    public void setCharacterEncoding(String charset) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setCharacterEncoding", String.class);
        method.setAccessible(true);
        method.invoke(response, charset);
    }

    public void setContentLength(int len) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setContentLength", int.class);
        method.setAccessible(true);
        method.invoke(response, len);
    }

    public void setContentLengthLong(long len) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setContentLengthLong", long.class);
        method.setAccessible(true);
        method.invoke(response, len);
    }

    public void setContentType(String type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setContentType", String.class);
        method.setAccessible(true);
        method.invoke(response, type);
    }

    public void setBufferSize(int size) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setBufferSize", int.class);
        method.setAccessible(true);
        method.invoke(response, size);
    }

    public int getBufferSize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getBufferSize");
        method.setAccessible(true);
        return (Integer) method.invoke(response);
    }

    public void flushBuffer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("flushBuffer");
        method.setAccessible(true);
        method.invoke(response);
    }

    public void resetBuffer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("resetBuffer");
        method.setAccessible(true);
        method.invoke(response);
    }

    public boolean isCommitted() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("isCommitted");
        method.setAccessible(true);
        return (Boolean) method.invoke(response);
    }

    public void reset() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("reset");
        method.setAccessible(true);
        method.invoke(response);
    }

    public void setLocale(Locale loc) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("setLocale", Locale.class);
        method.setAccessible(true);
        method.invoke(response, loc);
    }

    public Locale getLocale() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = responseClass.getMethod("getLocale");
        method.setAccessible(true);
        return (Locale) method.invoke(response);
    }


}
