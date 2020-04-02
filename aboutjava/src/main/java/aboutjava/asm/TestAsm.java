package aboutjava.asm;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-03-31 18:17
 **/
public class TestAsm {
    public static byte[] createNewClass() {
        //创建ClassWriter ，构造参数的含义是是否自动计算栈帧，操作数栈及局部变量表的大小
        //0：完全手动计算 即手动调用visitFrame和visitMaxs完全生效
        //ClassWriter.COMPUTE_MAXS=1：需要自己计算栈帧大小，但本地变量与操作数已自动计算好，当然也可以调用visitMaxs方法，只不过不起作用，参数会被忽略；
        //ClassWriter.COMPUTE_FRAMES=2：栈帧本地变量和操作数栈都自动计算，不需要调用visitFrame和visitMaxs方法，即使调用也会被忽略。
        //这些选项非常方便，但会有一定的开销，使用COMPUTE_MAXS会慢10%，使用COMPUTE_FRAMES会慢2倍。
        ClassWriter cw = new ClassWriter(0);
        //创建类头部信息：jdk版本，修饰符，类全名，签名信息，父类，接口集
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, "asm/Student", null, "java/lang/Object", null);
        //创建字段arg：修饰符，变量名，类型，签名信息，初始值（不一定会起作用后面会说明）
        cw.visitField(ACC_PUBLIC, "age", "I", null, new Integer(11))
                .visitEnd();
        //创建方法：修饰符，方法名，类型，描述（输入输出类型），签名信息，抛出异常集合
        // 方法的逻辑全部使用jvm指令来书写的比较晦涩，门槛较高，后面会介绍简单的方法
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getAge", "()I", null, null);
        // 创建方法第一步
        mv.visitCode();
        // 将索引为 #0 的本地变量列表加到操作数栈下。#0 索引的本地变量列表永远是 this ，当前类实例的引用。
        mv.visitVarInsn(ALOAD, 0);
        // 获取变量的值，
        mv.visitFieldInsn(GETFIELD, "asm/Student", "age", "I");
        // 返回age
        mv.visitInsn(IRETURN);
        // 设置操作数栈和本地变量表的大小
        mv.visitMaxs(1, 1);
        //结束方法生成
        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitIntInsn(BIPUSH, 10);
        mv.visitFieldInsn(PUTFIELD, "asm/Student", "age", "I");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
        //结束类生成
        cw.visitEnd();
        //返回class的byte[]数组
        return cw.toByteArray();
    }

    //修改测试代码
    public static void main(String[] args) throws Exception {
//        ClassReader classReader = new ClassReader(createNewClass());
//        ClassWriter classWriter = new ClassWriter(classReader, 0);
//        ClassVisitor cv = new MyClassVisitor(classWriter);
//        classReader.accept(cv,0);
//        Student student = new Student();
//        student.getAge();
//        MyClassLoader myClassLoader = new MyClassLoader();
//        Class classByBytes = myClassLoader.getClassByBytes(classWriter.toByteArray());
//        Object o = classByBytes.newInstance();
//        Field field = classByBytes.getField("age");
//        Object o1 = field.get(o);
//        Method method = classByBytes.getMethod("getAge");
//        Object o2 = method.invoke(o);
//        System.out.println("Field age:  " + o1);
//        System.out.println("Method method :  " + o2);

        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.loadClass("aboutjava.asm.SubstitutionImpl", false);
        Substitution substitution = (Substitution)aClass.newInstance();
        substitution.print();
    }

}
