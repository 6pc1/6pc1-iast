package cn.org.pc6pc1.iast.core;


import cn.org.pc6pc1.iast.contenxt.HttpRequestContext;
import cn.org.pc6pc1.iast.contenxt.RequestContext;
import cn.org.pc6pc1.iast.http.IASTServletRequest;
import cn.org.pc6pc1.iast.http.IASTServletResponse;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


// 实现Http埋点
public class Http {

    private static ThreadLocal<Boolean> isExecOnce = ThreadLocal.withInitial(() -> true);


    // 在HTTP方法结束前调用，主要是对存在于上下文的结果进行可视化的打印
    public static void leaveHttp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        IASTServletRequest request = RequestContext.getHttpRequestContextThreadLocal().getServletRequest();
        // 将对应结果可视化打印
        // 防止初始化时的各种信息打印
        if (!request.getRequestURI().contains("favicon.ico") && !request.getRequestURI().contains("/")){
            System.out.printf("URL             : %s \n", request.getRequestURL().toString());
            System.out.printf("URI             : %s \n", request.getRequestURI());
            System.out.printf("QueryString     : %s \n", request.getQueryString());
            System.out.printf("HTTP Method     : %s \n", request.getMethod());
        }
        // 获取上下文的调用链
        RequestContext.getHttpRequestContextThreadLocal().getCallChain().forEach(item -> {
            if (item.getChainType().contains("leave")) {
                String returnData = null;
                if (item.getReturnObject().getClass().equals(byte[].class)) {
                    returnData = new String((byte[]) item.getReturnObject());
                } else if (item.getReturnObject().getClass().equals(char[].class)){
                    returnData = new String((char[]) item.getReturnObject());
                } else {
                    returnData = item.getReturnObject().toString();
                }
                // 打印类型，类名方法名，返回值
                System.out.printf("type: %s call method name: %s call method return: %s \n",
                        item.getChainType(), item.getJavaClassName() + "#" + item.getJavaMethodName(),
                        returnData);
            } else {
                // 打印类型，类名方法名，方法参数
                System.out.printf("type: %s call method name: %s call method args: %s \n",
                        item.getChainType(), item.getJavaClassName() + "#" + item.getJavaMethodName(),
                        Arrays.asList(item.getArgumentsArray()));
            }

            // 如果是Sink类型，则还会输出调用栈信息
            if (item.getChainType().contains("Sink")) {
                // 准备写文件，将对应的调用栈信息写入文件
                FileWriter writer = null;
                try {

                    int depth = 1;
                    writer = new FileWriter("E:\\AST\\IAST\\6pc1-iast\\iast\\src\\main\\resources\\callstack.txt", true);
                    StackTraceElement[] elements = item.getStackTraceElement();
                    for (StackTraceElement element : elements) {
                        if (element.getClassName().contains("cn.org.pc6pc1.iast") ||
                                element.getClassName().contains("java.lang.Thread") ||
                                element.getClassName().contains("sun.reflect.GeneratedMethodAccessor") ||
                                element.getClassName().contains("invoke")
                        ) {
                            continue;
                        }
                        String stackTraceLine = String.format("%9s".replace("9", String.valueOf(depth)), "") + element.toString();
                        writer.write(stackTraceLine + "\n");
                        System.out.println(stackTraceLine);
                        depth++;
                    }
                    replayRequest(request);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException | IllegalAccessException |
                         NoSuchMethodException e){
                    throw new RuntimeException(e);
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }


    // 判断当前上下文是否缓存了Request请求
    public static boolean haveEnterHttp() {
        HttpRequestContext context = RequestContext.getHttpRequestContextThreadLocal();
        return context != null;
    }


    // 匹配对应的命令执行语句
    public static boolean haveHackString(IASTServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String queryString = request.getQueryString();
        String[] blacklist = {"ls", "whoami", "pwd"};

        for (String hackString : blacklist) {
            if (queryString.contains(hackString)) {
                return true;
            }
        }
        return false;
    }




    // 流量重放功能
    public static void replayRequest(IASTServletRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {
        if (haveHackString(request)){
            HttpClient httpClient = HttpClients.createDefault();
            System.out.println("can enter next step!");

            // 根据对应的请求类型创建对应的http请求
            if ("POST".equals(request.getMethod())) {
                HttpPost httpPost = new HttpPost(request.getRequestURL().toString());

                //设置请求体
                if (request.getRequestBody() != null){
                    httpPost.setEntity(new StringEntity(request.getRequestBody()));
                }

                // 发送对应请求并获取响应
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // 打印对应的响应信息
                System.out.println("response code: " + httpResponse.getStatusLine().getStatusCode());
                System.out.println("response body: " + httpResponse.getEntity());
            } else if ("GET".equals(request.getMethod())) {
                String baseURI = request.getRequestURL().toString();
                String queryString = request.getQueryString();
                String fullURI = baseURI;
                if (queryString != null && !queryString.isEmpty()){
                    fullURI += "?" + queryString;
                }


                HttpGet httpGet = new HttpGet(fullURI);


                // 这里httpClient会执行多遍，所以引入execOne来控制
                HttpResponse response = null;
                if (isExecOnce.get()){
                    isExecOnce.set(false);
                    response = httpClient.execute(httpGet);
                    System.out.println("response code: " + response.getStatusLine().getStatusCode());
                    System.out.println("response body: " + response.getEntity());
                }

            }
        }
    }



    // 在HTTP方法进入的时候调用，如果当前上下文为空，就将HttpServletRequest和HttpServletResponse对象存到当前线程的上下文中
    // 方便后续对数据的调取使用。
    public static void enterHttp(Object[] objects) {
        if (!haveEnterHttp()){
            IASTServletRequest request = new IASTServletRequest(objects[0]);
            IASTServletResponse response = new IASTServletResponse(objects[1]);

            RequestContext.setHttpRequestContextThreadLocal(request, response);
        }
    }


}
