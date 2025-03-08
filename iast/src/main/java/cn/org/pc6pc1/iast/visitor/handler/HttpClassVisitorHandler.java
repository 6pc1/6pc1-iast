package cn.org.pc6pc1.iast.visitor.handler;

import cn.org.pc6pc1.iast.visitor.Handler;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import java.lang.reflect.Modifier;

public class HttpClassVisitorHandler implements Handler {

    // 用于匹配service这个方法描述符 这里就是匹配HttpServletRequest、HttpServletResponse为参数，返回值为void的函数
    private static final String METHOD_DESC = "(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;)V";


    @Override
    public MethodVisitor ClassVisitorHandler(MethodVisitor mv, String className, int access,
                                             String name, String desc, String signature, String[] exceptions) {
        // 确保只对HttpServlet的service方法进行修改
        if ("service".equals(name) && METHOD_DESC.equals(desc)) {
            final boolean isStatic = Modifier.isStatic(access);
            //获得Object[]的类型值， 也就是[Ljava/lang/Object;
            final Type argsType = Type.getType(Object[].class);

            // 打印基础信息
            System.out.println(
                    "HTTP Process 类名是: " + className + ",方法名是: " + name + "方法的描述符是:" + desc + ",签名是:"
                            + signature + ",exceptions:" + exceptions);
            try {
                // 使用AdviceAdapter更加简易的在方法入口点以及出库点进行操作
                return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
                    @Override
                    protected void onMethodEnter() {
                        // 这段代码的作用是在方法入口处插入代码，将方法的所有参数加载到一个 Object 数组中，并将这个数组传递给enterHttp 方法。
                        //加载对应参数
                        // 加载方法参数到一个对象数组里
                        loadArgArray();
                        // 创建一个对象数组
                        int argsIndex = newLocal(argsType);
                        // 将操作数栈顶的 Object 数组存储到局部变量表中索引为 argsIndex 的位置。
                        storeLocal(argsIndex, argsType);
                        // 再次将局部变量表中索引为 argsIndex 的 Object 数组加载到操作数栈。
                        loadLocal(argsIndex);

                        if (isStatic) {
                            push((Type) null);
                        } else {
                            loadThis();
                        }

                        loadLocal(argsIndex);

                        // 在调用 visitMethodInsn 之前，必须将所有参数压入操作数栈。
                        // 参数的顺序必须与方法描述符中指定的顺序一致。 如果方法是一个实例方法，则必须首先将 this 指针压入操作数栈。
                        // 这里两个参数是HttpServletRequest和HttpServletResponse
                        // false代表不是接口
                        mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Http", "enterHttp",
                                "([Ljava/lang/Object;)V", false);

                    }

                    @Override
                    protected void onMethodExit(int i) {
                        super.onMethodExit(i);
                        mv.visitMethodInsn(INVOKESTATIC, "cn/org/pc6pc1/iast/core/Http", "leaveHttp", "()V",
                                false);
                    }
                };
            }catch (Exception e) {
                System.err.println("处理HTTP请求时发生异常: " + e.getMessage());
                throw new RuntimeException("处理HTTP请求时发生错误", e);
            }

        }
        return mv;
    }
}
