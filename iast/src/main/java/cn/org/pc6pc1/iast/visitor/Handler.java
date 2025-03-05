package cn.org.pc6pc1.iast.visitor;

import org.objectweb.asm.MethodVisitor;


// 对java方法的访问进行拦截和增强，主要用于进行java字节码修改
public interface Handler {
    MethodVisitor ClassVisitorHandler(MethodVisitor mv, final String className, int access, String name, String desc, String signature, String[] exceptions);
}
