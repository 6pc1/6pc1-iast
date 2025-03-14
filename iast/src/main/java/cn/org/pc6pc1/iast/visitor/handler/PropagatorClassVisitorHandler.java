package cn.org.pc6pc1.iast.visitor.handler;

import cn.org.pc6pc1.iast.visitor.Handler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;


public class PropagatorClassVisitorHandler implements Handler {

    private static final String METHOD_DESC = "(Ljava/lang/String;)[B";

    private static final String CLASS_NAME = "java.lang.Runtime";


    @Override
    public MethodVisitor ClassVisitorHandler(MethodVisitor mv, String className, int access,
                                             String name, String desc, String signature, String[] exceptions) {
        if ((name.contains("decode") && METHOD_DESC.equals(desc)) || CLASS_NAME.equals(className)){
            final boolean isStatic = Modifier.isStatic(access);
            final Type argsType = Type.getType(Object[].class);

            if (((access & Opcodes.ACC_NATIVE) == Opcodes.ACC_NATIVE) || className.contains("cn.org.pc6pc1.iast")){
                System.out.println("propagator process skip 类名: " + className +  ", 方法名: " + name + "方法的描述符是: " + desc);
            } else {
                System.out.println("propagator process 类名是: " + className + ", 方法名: " + name + ", 方法的描述符是: " + desc);
                return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                    @Override
                    protected void onMethodEnter() {
                        try {
                            loadArgArray();
                            int argsIndex = newLocal(argsType);
                            storeLocal(argsIndex,argsType);
                            loadLocal(argsIndex);
                            push(className);
                            push(name);
                            push(desc);
                            push(isStatic);

                            mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Propagator",
                                    "enterPropagator",
                                    "([Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V",
                                    false);
                            super.onMethodEnter();
                        }catch (Exception e){
                            System.err.println("Propagator Process Error: " + e.getMessage());
                            throw new IllegalStateException("进入方法时错误",e);
                        }
                    }

                    @Override
                    protected void onMethodExit(int i) {
                        try {
                            Type returnType = Type.getReturnType(desc);
                            if (returnType == null || Type.VOID_TYPE.equals(returnType)) {
                                push((Type) null);
                            } else {
                                mv.visitInsn(org.objectweb.asm.Opcodes.DUP);
                            }
                            push(className);
                            push(name);
                            push(desc);
                            push(isStatic);
                            mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Propagator",
                                    "leavePropagator",
                                    "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V",
                                    false);
                            super.onMethodExit(i);
                        } catch (Exception e) {
                            System.err.println("Propagator Process Error: " + e.getMessage());
                            throw new IllegalStateException("退出方法时错误",e);
                        }
                    }
                };
            }
        }
        return mv;
    }
}
