package aboutjava.asm;

import org.objectweb.asm.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;


public class MyMethodVisitor extends MethodVisitor {
    public MyMethodVisitor(MethodVisitor mv) {
        super(ASM5, mv);
    }


    @Override
    public void visitCode() {
        mv.visitCode();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("getAge");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack+1, maxLocals);
    }
}



