package aboutjava.asm;

import org.objectweb.asm.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class MyClassVisitor extends ClassVisitor {
    public MyClassVisitor(ClassVisitor cv) {
        super(ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if(name.equals("getAge")){
            return new MyMethodVisitor(methodVisitor);
        }else {
            return methodVisitor;
        }
    }
}