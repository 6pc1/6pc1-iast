package cn.org.pc6pc1.iast.visitor.handler;

import cn.org.pc6pc1.iast.visitor.Handler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.reflect.Modifier;

public class SourceClassVisitorHandler implements Handler {

    private static final String METHOD_DESC = "(Ljava/lang/String;)Ljava/lang/String;";



    public MethodVisitor ClassVisitorHandler(MethodVisitor mv, String className,
                                             int access, String name, String desc,
                                             String signature, String[] exceptions) {
        if (METHOD_DESC.equals(desc) && "getParameter".equals(name)) {
            final boolean isStatic = Modifier.isStatic(access);

            System.out.println("source process 类名是: " + className + ", 方法名是: " + name + ", 方法的描述符是: " + desc
            + ", 签名是: " + signature + ", exceptions: " + exceptions);
            return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                @Override
                protected void onMethodEnter() {
                    try {
                        loadArgArray();
                        int argsIndex = newLocal(Type.getType(Object[].class));
                        storeLocal(argsIndex, Type.getType(Object[].class));
                        loadLocal(argsIndex);
                        push(className);
                        push(name);
                        push(desc);
                        push(isStatic);

                        mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Source", "enterSource",
                                "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", false);
                        super.onMethodEnter();
                    } catch (Exception e){
                        throw new RuntimeException("source process error", e);
                    }
                }

                @Override
                protected void onMethodExit(int i) {
                   try {
                       Type returnType = Type.getReturnType(desc);
                       if (returnType == null || Type.VOID_TYPE.equals(returnType)) {
                           push((Type) null);
                       } else {
                           mv.visitInsn(Opcodes.DUP);
                       }
                       push(className);
                       push(name);
                       push(desc);
                       push(isStatic);
                       mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Source", "leaveSource",
                               "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", false);
                       super.onMethodExit(i);
                   } catch (Exception e){
                       throw new RuntimeException("source process error", e);
                   }
                }
            };
        }
        return mv;
    }
}
