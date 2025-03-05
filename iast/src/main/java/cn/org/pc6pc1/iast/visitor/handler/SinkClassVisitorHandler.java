package cn.org.pc6pc1.iast.visitor.handler;

import cn.org.pc6pc1.iast.visitor.Handler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.reflect.Modifier;

public class SinkClassVisitorHandler implements Handler {
    private static final String METHOD_DESC = "()Ljava/lang/Process;";


    @Override
    public MethodVisitor ClassVisitorHandler(MethodVisitor mv, String className, int access,
                                             String name, String desc, String signature, String[] exceptions) {
        if (("start".equals(name) && METHOD_DESC.equals(desc))) {
            final boolean isStatic = Modifier.isStatic(access);
            final Type argsType = Type.getType(Object[].class);

            System.out.println("sink process 类名: " + className + ", 方法名: " + name + ", 方法的描述符是: " + desc);
            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    try {
                        loadArgArray();
                        int argsIndex = newLocal(argsType);
                        storeLocal(argsIndex, argsType);
                        loadThis();
                        loadLocal(argsIndex);
                        push(className);
                        push(name);
                        push(desc);
                        push(isStatic);

                        mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Sink", "enterSink",
                                "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V",
                                false);
                        super.onMethodEnter();
                    } catch (Exception e) {
                        throw new RuntimeException("Sink Process Error",e);
                    }
                }
            };
        }
        return mv;
    }
}
