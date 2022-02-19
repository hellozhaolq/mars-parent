package com.zhaolq.mars.demo.annotation.processor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;

import com.zhaolq.mars.demo.annotation.annotation.Factory;

/**
 * 继承AbstractProcessor类实现自定义注解处理器 -- @Factory注解处理器
 *
 * @author zhaolq
 * @date 2020/7/10 10:52
 */
@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {

    /**
     * 用于对类型进行操作的实用方法。
     */
    private Types typeUtils;
    /**
     * 用于对元素进行操作的实用方法。
     */
    private Elements elementUtils;
    /**
     * 用于创建新的源文件、类文件或辅助文件的文件管理器。
     */
    private Filer filer;
    /**
     * 为注释处理器提供了一种报告错误消息、警告和其他通知的方法。可以传递元素、注解和注解值以提供消息的位置提示。但是，此类位置提示可能不可用或仅是近似的。
     */
    private Messager messager;

    /**
     * 将要创建的工厂类的列表
     */
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<String, FactoryGroupedClasses>();

    /**
     * 处理轮次
     */
    private int i = 1;

    /**
     * 这是Processor接口中提供的一个方法，在编译期间注解处理器工具会调用此方法并且提供实现ProcessingEnvironment接口的对象
     * 作为参数，该对象提供一些工具类。
     *
     * @param processingEnv 注解处理工具框架提供给处理器的处理环境
     * @return void
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // 返回实现Types接口的对象
        typeUtils = processingEnv.getTypeUtils();
        // 返回实现Elements接口的对象
        elementUtils = processingEnv.getElementUtils();
        // 返回实现Filer接口的对象
        filer = processingEnv.getFiler();
        // 返回实现Messager接口的对象
        messager = processingEnv.getMessager();
    }

    /**
     * 在此处定义要为其注册注解处理器的注解。
     *
     * @param
     * @return java.util.Set<java.lang.String> 返回类型是String集合，其中包含要使用此注解处理器处理的注解类型的全限定名称。
     *
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    /**
     * 指定支持的Java版本
     *
     * @param
     * @return javax.lang.model.SourceVersion
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 检查带注解的元素是否合法
     */
    private void checkValidClass(FactoryAnnotatedClass item) throws ProcessingException {

        // 转换为TypeElement，具有更多类型特定的方法
        TypeElement classElement = item.getTypeElement();

        // 检查它是否是public
        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new ProcessingException(classElement,
                    "The class %s is not public.",
                    classElement.getQualifiedName().toString());
        }

        // 检查它是否是抽象类
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ProcessingException(classElement,
                    "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(),
                    Factory.class.getSimpleName());
        }

        // 检查继承：此类必须是@Factory.type()中指定类型的子类；
        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedFactoryGroupName());

        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            // 当@Factory.type()指定一个接口类型时，检查此类是否实现指定接口。

            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
            }
        } else {
            // 当@Factory.type()指定一个类类型时，检查此类是否继承指定类。

            TypeElement currentClass = classElement;

            // 循环查找直接超类，直到与@Factory.type()的指定相同。
            while (true) {
                /*
                 返回此类型元素的直接超类。
                 如果此类型元素是一个接口或Object类，则返回类型为{@link TypeKind＃NONE NONE}的{@link NoType}。
                 */
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {
                    // Basis class (java.lang.Object) reached, so exit
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(),
                            Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                }

                if (superClassType.toString().equals(item.getQualifiedFactoryGroupName())) {
                    // 已找到所需超类
                    break;
                }

                /*
                 typeUtils.asElement(TypeMirror t):返回TypeMirror对应的Element。
                                                   类型可以是{@code DeclaredType}或{@code TypeVariable}。如果类型不是带有相应元素的类型，则返回{@code null}。
                 */
                // 继承树向上移动
                currentClass = (TypeElement) typeUtils.asElement(superClassType);
            }
        }

        // 检查是否给定了公有的空的构造函数
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 && constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    // 找到了公有的空的构造函数，用return结束当前方法后续的代码执行。
                    return;
                }
            }
        }

        // 找不到公有的空的构造函数
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());
    }

    /**
     * 在这里，您可以编写代码来扫描、评估和处理注解，以及生成java文件。使用RoundEnvironment参数，可以查询被特定注解标注的元素。
     *
     * @param annotations 要求处理的注解类型
     * @param roundEnv 有关本轮和上一轮的处理环境信息。使用此参数可以查询被特定注解标注的元素
     * @return boolean 如果返回{@code true}，则会声明注解类型，并且不会要求后续处理器对其进行处理；
     *                 如果返回{@code false}，则将取消声明注解类型，并可能要求后续处理器(其他自定义注解处理器)对其进行处理。
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // note(null, "日志开始-----第" + i + "轮");
        // note(null, "第" + i + "轮：" + annotations.toString());
        // note(null, "第" + i + "轮：" + roundEnv.getElementsAnnotatedWith(Factory.class).toString());

        try {

            // 遍历被@Factory注解标注的元素
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class)) {

                /**
                 * 检查使用注解的元素是否是一个类。
                 * 不可使用 annotatedElement instanceof TypeElement，因为interface也是一种TypeElement。
                 * 请注意，enum是一种class，annotation_type是一种interface。
                 */
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                }

                // 我们可以强制转换它，因为我们知道它是 ElementKind.CLASS
                TypeElement typeElement = (TypeElement) annotatedElement;

                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

                checkValidClass(annotatedClass);

                // 一切就绪，接下来尝试添加

                // 从列表中获取被@Factory标注的类指定type的工厂类
                FactoryGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedFactoryGroupName());

                if (factoryClass == null) {
                    // 工厂类不存在，则添加到列表

                    String qualifiedGroupName = annotatedClass.getQualifiedFactoryGroupName();
                    factoryClass = new FactoryGroupedClasses(qualifiedGroupName);
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // 向工厂类中添加FactoryAnnotatedClass，并检查是否存在另一个具有相同id的带注解的类。
                factoryClass.add(annotatedClass);
            }

            // 遍历工厂类列表，生成代码
            for (FactoryGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(elementUtils, filer);
            }
            factoryClasses.clear();
        } catch (ProcessingException e) {
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            error(null, e.getMessage());
        }

        // note(null, "日志结束-----第" + i++ + "轮");

        return false;
    }

    /**
     * Prints an error message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     */
    public void error(Element e, String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    /**
     * Prints an note message
     *
     * @param e Can be null
     * @param msg The note message
     */
    public void note(Element e, String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg, e);
    }

}