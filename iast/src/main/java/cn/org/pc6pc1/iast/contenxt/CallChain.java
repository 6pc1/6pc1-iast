package cn.org.pc6pc1.iast.contenxt;


// 方法调用链类
public class CallChain {

    // 链路类型
    private String chainType;

    //返回结果对象
    private Object returnObject;

    //参数数组对象
    private Object[] argumentsArray;

    //java类名
    private String javaClassName;

    // java方法名
    private String javaMethodName;

    //java方法描述符
    private String javaMethodDesc;

    //方法是否为静态方法
    private boolean isStatic;

    //调用堆栈 StackTrace
    public StackTraceElement[] StackTraceElement;


    // 设置对应各个变量的get、set方法
    public String getChainType() {
        return chainType;
    }

    public void setChainType(String chainType) {
        this.chainType = chainType;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object[] getArgumentsArray() {
        return argumentsArray;
    }

    public void setArgumentsArray(Object[] argumentsArray) {
        this.argumentsArray = argumentsArray;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getJavaMethodName() {
        return javaMethodName;
    }

    public void setJavaMethodName(String javaMethodName) {
        this.javaMethodName = javaMethodName;
    }

    public String getJavaMethodDesc() {
        return javaMethodDesc;
    }

    public void setJavaMethodDesc(String javaMethodDesc) {
        this.javaMethodDesc = javaMethodDesc;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public StackTraceElement[] getStackTraceElement() {
        return StackTraceElement;
    }

    public void setStackTraceElement(StackTraceElement[] stackTraceElement) {
        StackTraceElement = stackTraceElement;
    }
}
