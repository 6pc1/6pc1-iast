package cn.org.pc6pc1.iast;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Base64;

public class Agent {
    //tomcat的agent配置如下
    //-Dfile.encoding=GBK -noverify -Xbootclasspath/p:E:\AST\IAST\6pc1-iast\iast\target\agent.jar -javaagent:E:\AST\IAST\6pc1-iast\iast\target\agent.jar
    public static void premain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        inst.addTransformer(new AgentTransform(), true);
        inst.retransformClasses(Runtime.class);
        inst.retransformClasses(Base64.class);
    }
}
