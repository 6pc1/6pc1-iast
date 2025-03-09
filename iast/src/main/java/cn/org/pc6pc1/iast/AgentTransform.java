package cn.org.pc6pc1.iast;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

public class AgentTransform implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        if (className.contains("cn.org.pc6pc1")) {
            System.out.println("Skip class: " + className);
            return classfileBuffer;
        }

        if (className.contains("java.lang.invoke")) {
            System.out.println("Skip class: " + className);
            return classfileBuffer;
        }
        byte[] originalClassfileBuffer = classfileBuffer;

        ClassReader classReader  = new ClassReader(classfileBuffer);
        ClassWriter classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new IASTClassVisitor(className, classWriter);


        // 将 ClassReader 接收 IASTClassVisitor 的访问。 ClassReader.EXPAND_FRAMES 参数告诉 ASM 展开栈图。
        // accept 方法会遍历类的所有结构，并调用 IASTClassVisitor 中相应的方法进行处理。这里也就是调用visitMethod方法
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        classfileBuffer = classWriter.toByteArray();
        className = className.replace("/", ".");


        String regexp = "(Decoder|Servlet|connector|Request|Parameters|Base64|Runtime|ProcessBuilder)";

        if (Pattern.compile(regexp).matcher(className).find()) {
            ClassUtils.dumpClassFile("E:\\AST\\IAST\\6pc1-iast\\iast\\src\\main\\resources\\test\\test", className, classfileBuffer, originalClassfileBuffer);
        }

        return classfileBuffer;
    }
}
