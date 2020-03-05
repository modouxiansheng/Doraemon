package aboutjava.annotion;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @program: springBootPractice
 * @description:
 * @author: hu_pf
 * @create: 2020-03-04 17:54
 **/
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("aboutjava.annotion.MySetter")
public class MySetterProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;

    /**
     * @Description: 1. Message 主要是用来在编译时期打log用的
     *              2. JavacTrees 提供了待处理的抽象语法树
     *              3. TreeMaker 封装了创建AST节点的一些方法
     *              4. Names 提供了创建标识符的方法
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment)processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(MySetter.class);
        elementsAnnotatedWith.forEach(e->{
            JCTree tree = javacTrees.getTree(e);
            tree.accept(new TreeTranslator(){
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();

                    // 在抽象树中找出所有的变量
                    for (JCTree jcTree : jcClassDecl.defs){
                        if (jcTree.getKind().equals(Tree.Kind.VARIABLE)){
                            JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                            jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
                        }
                    }

                    // 对于变量进行生成方法的操作
                    jcVariableDeclList.forEach(jcVariableDecl -> {
                        messager.printMessage(Diagnostic.Kind.NOTE,jcVariableDecl.getName()+"has been processed");
                        jcClassDecl.defs = jcClassDecl.defs.prepend(makeSetterMethodDecl(jcVariableDecl));
                    });
                    super.visitClassDef(jcClassDecl);
                }
            });
        });

        return true;
    }

    private JCTree.JCMethodDecl makeSetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl){

        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        // 生成表达式 例如 this.a = a;
        JCTree.JCExpressionStatement aThis = makeAssignment(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()), treeMaker.Ident(jcVariableDecl.getName()));
        statements.append(aThis);
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 生成入参
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.getName(), jcVariableDecl.vartype, null);
        List<JCTree.JCVariableDecl> parameters = List.of(param);

        // 生成返回对象
        JCTree.JCExpression methodType = treeMaker.Type(new Type.JCVoidType());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),getNewMethodName(jcVariableDecl.getName()),methodType,List.nil(),parameters,List.nil(),block,null);

    }

    private Name getNewMethodName(Name name){
        String s = name.toString();
        return names.fromString("set"+s.substring(0,1).toUpperCase()+s.substring(1,name.length()));
    }

    private JCTree.JCExpressionStatement makeAssignment(JCTree.JCExpression lhs, JCTree.JCExpression rhs) {
        return treeMaker.Exec(
                treeMaker.Assign(
                        lhs,
                        rhs
                )
        );
    }
}



